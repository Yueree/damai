package com.damai;

import com.damai.constant.RedisStreamConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 大麦 来获取项目的完整资料 
 * @description: redis-stream属性配置
 * @author: 阿宽不是程序员
 **/
@Data
@ConfigurationProperties(prefix = RedisStreamConfigProperties.PREFIX)
public class RedisStreamConfigProperties {
    
    public static final String PREFIX = "spring.redis.stream";
    
    private String streamName;
    
    private String consumerGroup;
    
    private String consumerName;
    
    /**
     * 消费方式 group:消费组/broadcast:广播
     */
    private String consumerType = RedisStreamConstant.GROUP;
}

