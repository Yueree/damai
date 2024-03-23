package com.damai.context.filter;


import com.damai.context.impl.GatewayContextHolder;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 damai 来获取项目的完整资料 
 * @description: GatewayContextHolder数据存放
 * @author: 阿宽不是程序员
 **/
public class GatewayWorkRouteFilter implements GlobalFilter, Ordered {
    
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange, final GatewayFilterChain chain) {
        GatewayContextHolder.getCurrentGatewayContext().setExchange(exchange);
        return chain.filter(exchange);
    }
    
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
