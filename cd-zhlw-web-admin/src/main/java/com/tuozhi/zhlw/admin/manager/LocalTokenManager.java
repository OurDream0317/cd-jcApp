package com.tuozhi.zhlw.admin.manager;

import com.tuozhi.zhlw.admin.pojo.LoginUser;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author linqi
 * @create 2019/09/05 0:18
 **/

@Slf4j
//@Component
public class LocalTokenManager extends TokenManager {

    // 令牌存储结构
    private final ConcurrentHashMap<String, DummyUser> tokenMap = new ConcurrentHashMap<String, DummyUser>();

    @Override
    public void verifyExpired() {
        Date now = new Date();
        for (Map.Entry<String, DummyUser> entry : tokenMap.entrySet()) {
            String token = entry.getKey();
            DummyUser dummyUser = entry.getValue();
            // 当前时间大于过期时间
            if (now.compareTo(dummyUser.expired) > 0) {
                // 已过期，清除对应token
                if (now.compareTo(dummyUser.expired) > 0) {
                    tokenMap.remove(token);
//                    log.debug("token : " + token + "已失效");
                }
            }
        }
    }

    public void addToken(String token, LoginUser loginUser) {
        DummyUser dummyUser = new DummyUser();
        dummyUser.loginUser = loginUser;
        extendExpiredTime(dummyUser);
        tokenMap.putIfAbsent(token, dummyUser);
    }

    public LoginUser validate(String token) {
        DummyUser dummyUser = tokenMap.get(token);
        if (dummyUser == null) {
            return null;
        }
        extendExpiredTime(dummyUser);
        return dummyUser.loginUser;
    }

    public void remove(String token) {
        tokenMap.remove(token);
    }

    /**
     * 扩展过期时间
     *
     * @param dummyUser
     */
    private void extendExpiredTime(DummyUser dummyUser) {
        dummyUser.expired = new Date(new Date().getTime() + tokenTimeout * 1000);
    }

    // 复合结构体，含loginUser与过期时间expried两个成员
    private class DummyUser {
        private LoginUser loginUser;
        private Date expired; // 过期时间
    }
}
