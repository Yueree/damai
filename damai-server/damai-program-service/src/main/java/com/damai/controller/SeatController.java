package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.SeatAddDto;
import com.damai.dto.SeatBatchAddDto;
import com.damai.dto.SeatListDto;
import com.damai.service.SeatService;
import com.damai.vo.SeatRelateInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 座位 控制层
 * @author: 阿宽不是程序员
 **/
@RestController
@RequestMapping("/seat")
@Api(tags = "seat", value = "座位")
public class SeatController {
    
    @Autowired
    private SeatService seatService;
    
    
    @ApiOperation(value = "单个座位添加")
    @PostMapping(value = "/add")
    public ApiResponse<Long> add(@Valid @RequestBody SeatAddDto seatAddDto) {
        return ApiResponse.ok(seatService.add(seatAddDto));
    }
    
    @ApiOperation(value = "批量座位添加")
    @PostMapping(value = "/batch/add")
    public ApiResponse<Boolean> batchAdd(@Valid @RequestBody SeatBatchAddDto seatBatchAddDto) {
        return ApiResponse.ok(seatService.batchAdd(seatBatchAddDto));
    }
    
    @ApiOperation(value = "查询座位相关信息")
    @PostMapping(value = "/relate/info")
    public ApiResponse<SeatRelateInfoVo> relateInfo(@Valid @RequestBody SeatListDto seatListDto) {
        return ApiResponse.ok(seatService.relateInfo(seatListDto));
    }
}
