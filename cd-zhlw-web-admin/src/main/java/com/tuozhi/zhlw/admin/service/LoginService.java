package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.pojo.LoginUser;

/**
 * @author linqi
 * @create 2019/09/05 10:54
 **/

public interface LoginService {
    LoginUser findBaseUserByLoginName(String loginName);
}
