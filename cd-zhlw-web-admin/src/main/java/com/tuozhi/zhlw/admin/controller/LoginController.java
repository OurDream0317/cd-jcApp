package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wwx
 * @create 2019-09-23 19:10
 **/

@RestController
@RequestMapping("/JC/APP")
@Slf4j
public class LoginController extends BaseController {

    final int IS_STOP = 0;


    @Autowired
    private LoginService loginService;


    /**
     * 根据用户名和密码验证登录
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/userCheckLogin", method = RequestMethod.POST)
    public ResultMsg checkLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String loginName = request.getParameter("loginName");
        String password = request.getParameter("password");
        Date curTime = new Date();
        Map<String, Object> map = new HashMap<String, Object>();
        ResultMsg err = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        if (null == loginUser) {
            return ResultMsgUtil.isError(ResultMsgEnum.NO_USER);
        }
        if (loginUser.getValidStatus().equals(IS_STOP)) {
            return ResultMsgUtil.isError(ResultMsgEnum.USER_STOP);
        }
        if (!loginUser.getPassword().equals(password)) {
            err.setSuccess(false);
            err.setMessage("您输入的密码错误，请重新输入！");

            return err;
        }
        err.setSuccess(true);
        err.setMessage("登录成功");
        err.setData(loginUser.getUserId());
        err.setObj(loginUser);
        return err;
    }
}
