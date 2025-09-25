package com.damai.pay;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @description: 退款结果
 **/
@Data
@AllArgsConstructor
public class RefundResult {
    
    private final boolean success;
    
    private final String body;
    
    private final String message;
}
