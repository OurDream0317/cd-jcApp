package com.tuozhi.zhlw.admin.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysLogService;
import com.tuozhi.zhlw.admin.service.impl.SysLogServiceImpl;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.CookieUtils;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author linqi
 * @create 2019-08-30 17:58
 **/

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {


    @Autowired
    TokenManager tokenManager;

    private static final String[] EXCLUDE_PATH = {"/favicon.ico","/classic.json","/app.js",
            "bootstrap.js","bootstrap.css","html2canvas.js","echarts.js","/css/","/classic/","/app/",
            "/ext/","/build/","/overrides/","/packages/"};


    /**
     * 拦截请求时首先执行
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        //静态文件跳过
        if (checkStaticResource(request)) {
            return true;
        }

        String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
        if (StringUtils.isEmpty(token)) {
            //授权失败，返回登录页面
            returnResponseMessage(response, ResultMsgUtil.isError(ResultMsgEnum.RETURN_LOGIN));
            return false;
        }

        LoginUser loginUser = tokenManager.validate(token);
        if (null == loginUser) {
            //授权失败，返回登录页面
            returnResponseMessage(response, ResultMsgUtil.isError(ResultMsgEnum.RETURN_LOGIN));
            return false;
        }

        //TODO LOG日志代添加


        return true;
    }

    private void returnResponseMessage(HttpServletResponse response, ResultMsg resultMsg) throws Exception {
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.print(JSONObject.toJSONString(resultMsg, SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteDateUseDateFormat));
        writer.close();
        response.flushBuffer();
    //response.sendRedirect("index");
    }

    /**
     * 是静态文件跳过，应该配置在拦截器定义里，但是
     * springboot 对外部静态文件拦截不到
     * 所有只能先这样了
     *
     * @return
     */
    private boolean checkStaticResource(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String uri : EXCLUDE_PATH) {
            if (requestURI.contains(uri)) {
                return true;
            }
        }
        return false;
    }


}
