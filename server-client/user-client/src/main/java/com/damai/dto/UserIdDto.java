package com.damai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel(value="UserIdDto", description ="用户id入参")
public class UserIdDto {
    
    @ApiModelProperty(name ="id", dataType ="Long", value ="用户id", required =true)
    @NotNull
    private Long id;
}