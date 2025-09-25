package com.damai.pay;

import com.damai.initialize.base.AbstractApplicationInitializingBeanHandler;
import lombok.AllArgsConstructor;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;
import java.util.Map.Entry;

/**
 * @description: 支付策略初始化
 **/
@AllArgsConstructor
public class PayStrategyInitHandler extends AbstractApplicationInitializingBeanHandler {
    
    private final PayStrategyContext payStrategyContext;
    
    @Override
    public Integer executeOrder() {
        return 1;
    }
    
    @Override
    public void executeInit(ConfigurableApplicationContext context) {
        Map<String, PayStrategyHandler> payStrategyHandlerMap = context.getBeansOfType(PayStrategyHandler.class);
        for (Entry<String, PayStrategyHandler> entry : payStrategyHandlerMap.entrySet()) {
            PayStrategyHandler payStrategyHandler = entry.getValue();
            payStrategyContext.put(payStrategyHandler.getChannel(),payStrategyHandler);
        }
    }
}
