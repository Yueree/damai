package com.damai.service;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baidu.fsg.uid.UidGenerator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.damai.core.RedisKeyManage;
import com.damai.dto.ProgramGetDto;
import com.damai.dto.SeatAddDto;
import com.damai.dto.SeatBatchAddDto;
import com.damai.dto.SeatBatchRelateInfoAddDto;
import com.damai.dto.SeatListDto;
import com.damai.entity.ProgramShowTime;
import com.damai.entity.Seat;
import com.damai.enums.BaseCode;
import com.damai.enums.BusinessStatus;
import com.damai.enums.SeatType;
import com.damai.enums.SellStatus;
import com.damai.exception.DaMaiFrameException;
import com.damai.mapper.SeatMapper;
import com.damai.redis.RedisCache;
import com.damai.redis.RedisKeyBuild;
import com.damai.servicelock.LockType;
import com.damai.servicelock.annotion.ServiceLock;
import com.damai.util.ServiceLockTool;
import com.damai.vo.ProgramVo;
import com.damai.vo.SeatRelateInfoVo;
import com.damai.vo.SeatVo;
import org.redisson.api.RLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.damai.core.DistributedLockConstants.GET_SEAT_LOCK;
import static com.damai.core.DistributedLockConstants.SEAT_LOCK;
import static com.damai.service.cache.ExpireTime.EXPIRE_TIME;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 座位 service
 * @author: 阿宽不是程序员
 **/
@Service
public class SeatService extends ServiceImpl<SeatMapper, Seat> {
    
    @Autowired
    private UidGenerator uidGenerator;
    
    @Autowired
    private RedisCache redisCache;
    
    @Autowired
    private SeatMapper seatMapper;
    
    @Autowired
    private ProgramService programService;
    
    @Autowired
    private ProgramShowTimeService programShowTimeService;
    
    @Autowired
    private ServiceLockTool serviceLockTool;
    
    /**
     * 添加座位
     * */
    public Long add(SeatAddDto seatAddDto) {
        LambdaQueryWrapper<Seat> seatLambdaQueryWrapper = Wrappers.lambdaQuery(Seat.class)
                .eq(Seat::getProgramId, seatAddDto.getProgramId())
                .eq(Seat::getRowCode, seatAddDto.getRowCode())
                .eq(Seat::getColCode, seatAddDto.getColCode());
        Seat seat = seatMapper.selectOne(seatLambdaQueryWrapper);
        if (Objects.nonNull(seat)) {
            throw new DaMaiFrameException(BaseCode.SEAT_IS_EXIST);
        }
        seat = new Seat();
        BeanUtil.copyProperties(seatAddDto,seat);
        seat.setId(uidGenerator.getUid());
        seatMapper.insert(seat);
        return seat.getId();
    }
    
    /**
     * 查询座位
     * */
    @ServiceLock(lockType= LockType.Read,name = SEAT_LOCK,keys = {"#programId"})
    public List<SeatVo> selectSeatByProgramId(Long programId) {
        List<SeatVo> seatVoList = getSeatVoListByCache(programId);
        if (CollectionUtil.isNotEmpty(seatVoList)) {
            return seatVoList;
        }
        RLock lock = serviceLockTool.getLock(LockType.Reentrant, GET_SEAT_LOCK, new String[]{String.valueOf(programId)});
        lock.lock();
        try {
            seatVoList = getSeatVoListByCache(programId);
            if (CollectionUtil.isNotEmpty(seatVoList)) {
                return seatVoList;
            }
            LambdaQueryWrapper<Seat> seatLambdaQueryWrapper =
                    Wrappers.lambdaQuery(Seat.class).eq(Seat::getProgramId, programId);
            List<Seat> seats = seatMapper.selectList(seatLambdaQueryWrapper);
            for (Seat seat : seats) {
                SeatVo seatVo = new SeatVo();
                BeanUtil.copyProperties(seat, seatVo);
                seatVo.setSeatTypeName(SeatType.getMsg(seat.getSeatType()));
                seatVoList.add(seatVo);
            }
            Map<Integer, List<SeatVo>> seatMap = seatVoList.stream().collect(Collectors.groupingBy(SeatVo::getSellStatus));
            List<SeatVo> noSoldSeatVoList = seatMap.get(SellStatus.NO_SOLD.getCode());
            List<SeatVo> lockSeatVoList = seatMap.get(SellStatus.LOCK.getCode());
            List<SeatVo> soldSeatVoList = seatMap.get(SellStatus.SOLD.getCode());
            if (CollectionUtil.isNotEmpty(noSoldSeatVoList)) {
                redisCache.putHash(RedisKeyBuild.createRedisKey(RedisKeyManage.PROGRAM_SEAT_NO_SOLD_HASH, programId)
                        ,noSoldSeatVoList.stream()
                                .collect(Collectors.toMap(s -> String.valueOf(s.getId()),s -> s,(v1,v2) -> v2))
                        ,EXPIRE_TIME, TimeUnit.DAYS);
            }
            if (CollectionUtil.isNotEmpty(lockSeatVoList)) {
                redisCache.putHash(RedisKeyBuild.createRedisKey(RedisKeyManage.PROGRAM_SEAT_LOCK_HASH, programId)
                        ,lockSeatVoList.stream()
                                .collect(Collectors.toMap(s -> String.valueOf(s.getId()),s -> s,(v1,v2) -> v2))
                        ,EXPIRE_TIME, TimeUnit.DAYS);
            }
            if (CollectionUtil.isNotEmpty(soldSeatVoList)) {
                redisCache.putHash(RedisKeyBuild.createRedisKey(RedisKeyManage.PROGRAM_SEAT_SOLD_HASH, programId)
                        ,soldSeatVoList.stream()
                                .collect(Collectors.toMap(s -> String.valueOf(s.getId()),s -> s,(v1,v2) -> v2))
                        ,EXPIRE_TIME, TimeUnit.DAYS);
            }
            seatVoList = seatVoList.stream().sorted(Comparator.comparingInt(SeatVo::getRowCode)
                    .thenComparingInt(SeatVo::getColCode)).collect(Collectors.toList());
            return seatVoList;
        }finally {
            lock.unlock();
        }
    }
    
    private List<SeatVo> getSeatVoListByCache(Long programId){
        List<SeatVo> seatVoList = redisCache.getAllForHash(RedisKeyBuild.createRedisKey(
                RedisKeyManage.PROGRAM_SEAT_NO_SOLD_HASH, programId),SeatVo.class);
        seatVoList.addAll(redisCache.getAllForHash(RedisKeyBuild.createRedisKey(
                RedisKeyManage.PROGRAM_SEAT_LOCK_HASH, programId),SeatVo.class));
        seatVoList.addAll(redisCache.getAllForHash(RedisKeyBuild.createRedisKey(
                RedisKeyManage.PROGRAM_SEAT_SOLD_HASH, programId),SeatVo.class));
        return seatVoList;
    }
    
    public SeatRelateInfoVo relateInfo(SeatListDto seatListDto) {
        SeatRelateInfoVo seatRelateInfoVo = new SeatRelateInfoVo();
        ProgramVo programVo = 
                redisCache.get(RedisKeyBuild.createRedisKey(RedisKeyManage.PROGRAM,seatListDto.getProgramId()),ProgramVo.class);
        if (Objects.isNull(programVo)){
            ProgramGetDto programGetDto = new ProgramGetDto();
            programGetDto.setId(seatListDto.getProgramId());
            programVo = programService.getDetail(programGetDto);
        }
        if (programVo.getPermitChooseSeat().equals(BusinessStatus.NO.getCode())) {
            throw new DaMaiFrameException(BaseCode.PROGRAM_NOT_ALLOW_CHOOSE_SEAT);
        }
        List<SeatVo> seatVos = selectSeatByProgramId(seatListDto.getProgramId());
        Map<String, List<SeatVo>> seatVoMap = 
                seatVos.stream().collect(Collectors.groupingBy(seatVo -> seatVo.getPrice().toString()));
        ProgramShowTime programShowTime = programShowTimeService.selectProgramShowTimeByProgramId(seatListDto.getProgramId());
        
        seatRelateInfoVo.setProgramId(programVo.getId());
        seatRelateInfoVo.setPlace(programVo.getPlace());
        seatRelateInfoVo.setShowTime(programShowTime.getShowTime());
        seatRelateInfoVo.setShowWeekTime(programShowTime.getShowWeekTime());
        seatRelateInfoVo.setPriceList(seatVoMap.keySet().stream().sorted().collect(Collectors.toList()));
        seatRelateInfoVo.setSeatVoMap(seatVoMap);
        return seatRelateInfoVo;
    }
    
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchAdd(SeatBatchAddDto seatBatchAddDto) {
        Long programId = seatBatchAddDto.getProgramId();
        List<SeatBatchRelateInfoAddDto> seatBatchRelateInfoAddDtoList = seatBatchAddDto.getSeatBatchRelateInfoAddDtoList();
        
        
        int rowIndex = 0;
        for (SeatBatchRelateInfoAddDto seatBatchRelateInfoAddDto : seatBatchRelateInfoAddDtoList) {
            Long ticketCategoryId = seatBatchRelateInfoAddDto.getTicketCategoryId();
            BigDecimal price = seatBatchRelateInfoAddDto.getPrice();
            Integer count = seatBatchRelateInfoAddDto.getCount();
            
            int colCount = 10;
            int rowCount = count / colCount;
            
            for (int i = 1;i<= rowCount;i++) {
                rowIndex++;
                for (int j = 1;j<=colCount;j++) {
                    Seat seat = new Seat();
                    seat.setProgramId(programId);
                    seat.setTicketCategoryId(ticketCategoryId);
                    seat.setRowCode(rowIndex);
                    seat.setColCode(j);
                    seat.setSeatType(1);
                    seat.setPrice(price);
                    seat.setSellStatus(SellStatus.NO_SOLD.getCode());
                    seatMapper.insert(seat);
                }
            }
        }
        
        return true;
    }
}
