package com.damai.service;

import com.alibaba.fastjson.JSONObject;
import com.damai.core.RedisKeyManage;
import com.damai.core.StringUtil;
import com.damai.enums.BaseCode;
import com.damai.exception.DaMaiFrameException;
import com.damai.jwt.TokenUtil;
import com.damai.redis.RedisCache;
import com.damai.redis.RedisKeyBuild;
import com.damai.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @program: 极度真实还原大麦网高并发实战项目。 添加 阿宽不是程序员 微信，添加时备注 damai 来获取项目的完整资料 
 * @description: token service
 * @author: 阿宽不是程序员
 **/

@Component
public class TokenService {
    
    private static final String TOKEN_SECRET = "CSYZWECHAT";
    
    @Autowired
    private RedisCache redisCache;
    
    public String parseToken(String token){
        String userStr = TokenUtil.parseToken(token,TOKEN_SECRET);
        if (StringUtil.isNotEmpty(userStr)) {
            return JSONObject.parseObject(userStr).getString("userId");
        }
        return null;
    }
    
    public UserVo getUser(String token){
        UserVo userVo = null;
        String userId = parseToken(token);
        if (StringUtil.isNotEmpty(userId)) {
            userVo = redisCache.get(RedisKeyBuild.createRedisKey(RedisKeyManage.USER_LOGIN, userId), UserVo.class);
        }
        return Optional.ofNullable(userVo).orElseThrow(() -> new DaMaiFrameException(BaseCode.USER_EMPTY));
    }
}
