package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.RuleDto;
import com.damai.dto.RuleGetDto;
import com.damai.dto.RuleStatusDto;
import com.damai.dto.RuleUpdateDto;
import com.damai.service.RuleService;
import com.damai.vo.RuleVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 damai 来获取项目的完整资料 
 * @description: 普通规则 控制层
 * @author: 阿宽不是程序员
 **/
@RestController
@RequestMapping("/rule")
@Api(tags = "rule", description = "规则")
public class RuleController {

    @Autowired
    private RuleService ruleService;
    
    @ApiOperation(value = "添加普通规则")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse add(@Valid @RequestBody RuleDto ruleDto) {
        ruleService.ruleAdd(ruleDto);
        return ApiResponse.ok();
    }
    
    @ApiOperation(value = "修改普通规则")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ApiResponse update(@Valid @RequestBody RuleUpdateDto ruleUpdateDto) {
        ruleService.ruleUpdate(ruleUpdateDto);
        return ApiResponse.ok();
    }
    
    @ApiOperation(value = "修改普通规则状态")
    @RequestMapping(value = "/updateStatus", method = RequestMethod.POST)
    public ApiResponse updateStatus(@Valid @RequestBody RuleStatusDto ruleStatusDto){
        ruleService.ruleUpdateStatus(ruleStatusDto);
        return ApiResponse.ok();
    }
    
    @ApiOperation(value = "查询普通规则")
    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ApiResponse<RuleVo> get(@Valid @RequestBody RuleGetDto ruleGetDto){
        return ApiResponse.ok(ruleService.get(ruleGetDto));
    }
}
