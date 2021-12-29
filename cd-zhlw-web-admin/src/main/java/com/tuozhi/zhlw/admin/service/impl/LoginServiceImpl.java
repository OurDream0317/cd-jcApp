package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.admin.mapper.LoginMapper;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author linqi
 * @create 2019/09/05 10:49
 **/

@Service
public class LoginServiceImpl implements LoginService {
   @Resource
    LoginMapper loginMapper;

    @Override
    public LoginUser findBaseUserByLoginName(String loginName) {
       return loginMapper.findBaseUserByLoginName(loginName);
    }

}
