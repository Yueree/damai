package com.damai.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿星不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 账户下订单数量 vo
 * @author: 阿星不是程序员
 **/
@Data
@ApiModel(value="AccountOrderCountVo", description ="账户下订单数量")
public class AccountOrderCountVo {
    
    @ApiModelProperty(name ="count", dataType ="Integer", value ="账户下的订单数量")
    private Integer count;
}
