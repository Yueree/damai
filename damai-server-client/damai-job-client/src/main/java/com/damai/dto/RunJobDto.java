package com.damai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: job任务执行 dto
 * @author: 阿宽不是程序员
 **/
@Data
@ApiModel(value="RunJobDto", description ="RunJobDto")
public class RunJobDto {
    
    @ApiModelProperty(name ="id", dataType ="Long", value ="任务id", required =true)
    @NotNull
    private Long id;
}
