package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.ProgramAddDto;
import com.damai.dto.ProgramGetDto;
import com.damai.dto.ProgramInvalidDto;
import com.damai.dto.ProgramListDto;
import com.damai.dto.ProgramPageListDto;
import com.damai.dto.ProgramRecommendListDto;
import com.damai.dto.ProgramSearchDto;
import com.damai.page.PageVo;
import com.damai.service.ProgramService;
import com.damai.vo.ProgramHomeVo;
import com.damai.vo.ProgramListVo;
import com.damai.vo.ProgramVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿星不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 节目 控制层
 * @author: 阿星不是程序员
 **/
@RestController
@RequestMapping("/program")
@Api(tags = "program", value = "节目")
public class ProgramController {
    
    @Autowired
    private ProgramService programService;
    
    
    @ApiOperation(value = "添加")
    @PostMapping(value = "/add")
    public ApiResponse<Long> add(@Valid @RequestBody ProgramAddDto programAddDto) {
        return ApiResponse.ok(programService.add(programAddDto));
    }
    
    @ApiOperation(value = "搜索")
    @PostMapping(value = "/search")
    public ApiResponse<PageVo<ProgramListVo>> search(@Valid @RequestBody ProgramSearchDto programSearchDto) {
        return ApiResponse.ok(programService.search(programSearchDto));
    }
    
    @ApiOperation(value = "查询主页列表")
    @PostMapping(value = "/home/list")
    public ApiResponse<List<ProgramHomeVo>> selectHomeList(@Valid @RequestBody ProgramListDto programListDto) {
        return ApiResponse.ok(programService.selectHomeList(programListDto));
    }
    
    @ApiOperation(value = "查询分页列表")
    @PostMapping(value = "/page")
    public ApiResponse<PageVo<ProgramListVo>> selectPage(@Valid @RequestBody ProgramPageListDto programPageListDto) {
        return ApiResponse.ok(programService.selectPage(programPageListDto));
    }
    
    @ApiOperation(value = "查询推荐列表")
    @PostMapping(value = "/recommend/list")
    public ApiResponse<List<ProgramListVo>> recommendList(@Valid @RequestBody ProgramRecommendListDto programRecommendListDto) {
        return ApiResponse.ok(programService.recommendList(programRecommendListDto));
    }
    
    @ApiOperation(value = "查询详情(根据id)")
    @PostMapping(value = "/detail")
    public ApiResponse<ProgramVo> getDetail(@Valid @RequestBody ProgramGetDto programGetDto) {
        return ApiResponse.ok(programService.detail(programGetDto));
    }
    
    @ApiOperation(value = "节目失效(根据id)")
    @PostMapping(value = "/invalid")
    public ApiResponse<Boolean> invalid(@Valid @RequestBody ProgramInvalidDto programInvalidDto) {
        return ApiResponse.ok(programService.invalid(programInvalidDto));
    }
}
