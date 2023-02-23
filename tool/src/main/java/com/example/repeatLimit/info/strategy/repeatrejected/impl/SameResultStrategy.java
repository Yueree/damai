package com.example.repeatLimit.info.strategy.repeatrejected.impl;

import com.example.operate.Operate;
import com.example.repeatLimit.info.RepeatRejectedStrategy;
import com.example.repeatLimit.info.strategy.repeatrejected.RepeatLimitHandler;
import com.example.repeatLimit.info.strategy.repeatrejected.RepeatLimitStrategyContext;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @program: distribute-cache
 * @description: 防重复提交触发时策略(方法执行期间返回相同结果)
 * @author: lk
 * @create: 2022-05-28
 **/
public class SameResultStrategy implements RepeatLimitHandler {

    private final String NO_RETURN_VALUE = "void";
    
    private Operate operate;
    
    public SameResultStrategy(Operate operate){
        this.operate = operate;
    }

    @PostConstruct
    private void init(){
        RepeatLimitStrategyContext.put(RepeatRejectedStrategy.SAME_RESULT.getMsg(),this);
    }


    @Override
    public Object execute(String resultKeyName, long timeOut, TimeUnit timeUnit, ProceedingJoinPoint joinPoint) throws Throwable {
        Object o = operate.get(resultKeyName);
        if (o != null) {
            if (o instanceof String && NO_RETURN_VALUE.equals(o)) {
                o = null;
            }
            return o;
        }else{
            Object proceed,resultObject = null;
            try{
                proceed = joinPoint.proceed();
                resultObject = proceed;
                if (resultObject == null) {
                    resultObject = NO_RETURN_VALUE;
                }
                return proceed;
            }finally {
                operate.set(resultKeyName,resultObject,timeOut,timeUnit);
            }
        }
    }

    @Override
    public Object repeatRejected(String resultKeyName) {
        Object o = operate.get(resultKeyName);
        if (o instanceof String && NO_RETURN_VALUE.equals(o)) {
            o = null;
        }
        return o;
    }
}
