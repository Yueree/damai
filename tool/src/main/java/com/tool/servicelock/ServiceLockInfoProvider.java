package com.tool.servicelock;

import com.tool.core.BaseInfoProvider;
import com.example.core.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: distribute-cache
 * @description: 锁业务名和标识进行组装并获取类
 * @author: k
 * @create: 2022-05-28
 **/
public class ServiceLockInfoProvider extends BaseInfoProvider {

    private final Logger logger = LoggerFactory.getLogger(ServiceLockInfoProvider.class);

    private static final String LOCK_NAME_PREFIX = "LOCK";

    private static final String LOCK_DISTRIBUTE_ID_NAME_PREFIX = "LOCK_DISTRIBUTE_ID";

    public String getLockName(JoinPoint joinPoint,String name,String[] keys){
        return LOCK_NAME_PREFIX + ":" + name + getDefinitionKey(joinPoint, keys);
    }

    public String simpleGetLockName(String name,String[] keys){
        List<String> definitionKeyList = new ArrayList<>();
        for (String key : keys) {
            if (StringUtil.isNotEmpty(key)) {
                definitionKeyList.add(key);
            }
        }
        String definitionKeys = StringUtils.collectionToDelimitedString(definitionKeyList, "", ":", "");
        return LOCK_DISTRIBUTE_ID_NAME_PREFIX + ":" + name + definitionKeys;
    }
}
