package com.damai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 节目类型 dto
 * @author: 阿宽不是程序员
 **/
@Data
@ApiModel(value="ProgramCategoryDto", description ="节目类型")
public class ProgramCategoryDto {
    
    @ApiModelProperty(name ="type", dataType ="Integer", value ="1:一级种类 2:二级种类", required =true)
    @NotNull
    private Integer type;
}
