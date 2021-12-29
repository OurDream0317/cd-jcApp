package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.manager.RedisTokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import com.tuozhi.zhlw.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author linqi
 * @create 2020/05/07 13:45
 **/

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    RedisTokenManager redisTokenManager;

    @RequestMapping("/validate/token")
    public ResultMsg validateToken(String token, HttpServletResponse response) throws IOException {
        if ( StringUtils.isEmpty(token)) {
            return ResultMsgUtil.isError(ResultMsgEnum.INPUT_IS_NULL);
        }


        LoginUser loginUser = redisTokenManager.validate(token);
        if (loginUser == null) {
            return ResultMsgUtil.isError(ResultMsgEnum.TOKE_IS_TIMEOUT);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS,loginUser);
    }

    @RequestMapping("/test1")
    public String test1() {
        return "ok";
    }

}
