package com.damai.controller;

import com.damai.common.ApiResponse;
import com.damai.dto.NotifyDto;
import com.damai.dto.PayBillDto;
import com.damai.dto.PayDto;
import com.damai.dto.RefundDto;
import com.damai.dto.TradeCheckDto;
import com.damai.service.PayService;
import com.damai.vo.NotifyVo;
import com.damai.vo.PayBillVo;
import com.damai.vo.TradeCheckVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿星不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: 支付 控制层
 * @author: 阿星不是程序员
 **/
@RestController
@RequestMapping("/pay")
@Api(tags = "pay", value = "支付")
public class PayController {
    
    @Autowired
    private PayService payService;
    
    @ApiOperation(value = "支付")
    @PostMapping(value = "/common/pay")
    public ApiResponse<String> commonPay(@Valid @RequestBody PayDto payDto) {
        return ApiResponse.ok(payService.commonPay(payDto));
    }
    
    @ApiOperation(value = "支付后回到通知")
    @PostMapping(value = "/notify")
    public ApiResponse<NotifyVo> notify(@Valid @RequestBody NotifyDto notifyDto) {
        return ApiResponse.ok(payService.notify(notifyDto));
    }
    
    @ApiOperation(value = "支付状态查询")
    @PostMapping(value = "/trade/check")
    public ApiResponse<TradeCheckVo> tradeCheck(@Valid @RequestBody TradeCheckDto tradeCheckDto) {
        return ApiResponse.ok(payService.tradeCheck(tradeCheckDto));
    }
    
    @ApiOperation(value = "退款")
    @PostMapping(value = "/refund")
    public ApiResponse<String> refund(@Valid @RequestBody RefundDto refundDto) {
        return ApiResponse.ok(payService.refund(refundDto));
    }
    
    @ApiOperation(value = "账单详情查询")
    @PostMapping(value = "/detail")
    public ApiResponse<PayBillVo> detail(@Valid @RequestBody PayBillDto payBillDto) {
        return ApiResponse.ok(payService.detail(payBillDto));
    }
}
