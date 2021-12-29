package com.tuozhi.zhlw.admin.manager;

import com.alibaba.fastjson.JSON;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.cache.RedisCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author linqi
 * @create 2019/09/05 0:11
 **/

@Component
public class RedisTokenManager extends TokenManager {

    /**
     * 是否需要扩展token过期时间
     */
    private Set<String> tokenSet = new CopyOnWriteArraySet<String>();

    @Resource
    private RedisCache<String> redisCache;

    @Override
    public void addToken(String token, LoginUser loginUser) {
        String jsonString = JSON.toJSONString(loginUser);
        redisCache.set(TOKEN_PREFIX + token, jsonString, tokenTimeout);
    }


    @Override
    public LoginUser validate(String token) {
        String loginUserString = redisCache.get(TOKEN_PREFIX + token);
         if (StringUtils.isEmpty(loginUserString)) {
            return null;
        }
        LoginUser loginUser = JSON.parseObject(loginUserString, LoginUser.class);
        addToken(token, loginUser);

        return loginUser;
    }

    @Override
    public void remove(String token) {
        redisCache.delete(TOKEN_PREFIX + token);
    }

    @Override
    public void verifyExpired() {
        tokenSet.clear();
    }
}
