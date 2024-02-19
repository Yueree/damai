package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.ProgramCategoryAddDto;
import com.damai.dto.ProgramCategoryDto;
import com.damai.service.ProgramCategoryService;
import com.damai.vo.ProgramCategoryVo;
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
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 damai 来获取项目的完整资料 
 * @description: 节目类型 控制层
 * @author: 阿宽不是程序员
 **/
@RestController
@RequestMapping("/program/category")
@Api(tags = "program-category", value = "节目类型")
public class ProgramCategoryController {
    
    @Autowired
    private ProgramCategoryService programCategoryService;
    
    @ApiOperation(value = "查询所有节目类型")
    @PostMapping(value = "/select/all")
    public ApiResponse<List<ProgramCategoryVo>> selectAll() {
        return ApiResponse.ok(programCategoryService.selectAll());
    }
    
    @ApiOperation(value = "通过类型查询节目类型")
    @PostMapping(value = "/selectByType")
    public ApiResponse<List<ProgramCategoryVo>> selectByType(@Valid @RequestBody ProgramCategoryDto programCategoryDto) {
        return ApiResponse.ok(programCategoryService.selectByType(programCategoryDto));
    }
    
    @ApiOperation(value = "批量添加节目类型")
    @PostMapping(value = "/save/batch")
    public ApiResponse<Void> saveBatch(@Valid @RequestBody List<ProgramCategoryAddDto> programCategoryAddDtoList) {
        programCategoryService.saveBatch(programCategoryAddDtoList);
        return ApiResponse.ok();
    }
}
