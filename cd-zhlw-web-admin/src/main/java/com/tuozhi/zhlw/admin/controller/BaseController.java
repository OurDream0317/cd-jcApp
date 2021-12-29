package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.utils.CookieUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 15:15
 **/
public class BaseController {
    @Autowired
    TokenManager tokenManager;

    public String getFunction_DATA_PRIVILEGE_ID(HttpServletRequest request,String functionid) {

        LoginUser loginUser = getLoginUser(request);
        List<Map<String, Object>> temp = loginUser.getRoleDataFunctions();
        for (Map<String, Object> o : temp) {
            if (functionid.equals(String.valueOf(o.get("FUNCTIONID")))) {
                return o.get("DATA_PRIVILEGE_ID").toString();
            }
        }
        return null;
    }

    public LoginUser getLoginUser(HttpServletRequest request) {
        String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        return tokenManager.validate(token);
    }

}
