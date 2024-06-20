package com.damai.service.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿星不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 订单支付 回调地址配置
 * @author: 阿星不是程序员
 **/
@Data
@Component
public class OrderProperties {

    @Value("${orderPayNotifyUrl:localhost:8081}")
    private String orderPayNotifyUrl;
    
    @Value("${orderPayReturnUrl:localhost:8081}")
    private String orderPayReturnUrl;
}
