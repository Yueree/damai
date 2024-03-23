package com.damai.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 订单支付后状态检查 dto
 * @author: 阿宽不是程序员
 **/
@Data
@ApiModel(value="OrderPayCheckDto", description ="订单支付后状态检查")
public class OrderPayCheckDto {
    
    @ApiModelProperty(name ="orderNumber", dataType ="String", value ="订单编号", required =true)
    @NotNull
    private Long orderNumber;
    
    @ApiModelProperty(name ="payChannelType", dataType ="Integer", value ="支付方式1.支付宝 2.微信", required =true)
    @NotNull
    private Integer payChannelType;
}
