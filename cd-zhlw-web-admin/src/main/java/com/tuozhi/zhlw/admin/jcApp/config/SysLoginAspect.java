package com.tuozhi.zhlw.admin.jcApp.config;

import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tk.mybatis.mapper.annotation.Order;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author wwx
 * 拦截器  判断当前用户是否存在
 */
@Slf4j
@Component
@Aspect
@RestController
public class SysLoginAspect extends BaseController {
    @Autowired
    private LoginService loginService;

    @Pointcut("execution(public * com.tuozhi.zhlw.admin.jcApp.controller.*.*(..))")
    @Order("1")  //值越小越先被执行
    public void pointCut() {
    }

    //前置通知
    @Before(value = "pointCut()")
    public void before(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest req = attributes.getRequest();
        log.info("方法执行前执行。。。。。before");
        log.info("<============================================");
        log.info("请求来源： =》" + req.getRemoteAddr());
        log.info("请求URL：=》" + req.getRequestURI());
        log.info("请求方式：=》" + req.getMethod());
        log.info("响应方法：=》" + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("请求参数：=》" + Arrays.toString(joinPoint.getArgs()));
    }

    @Around("pointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        //2.最关键的一步:通过这获取到方法的所有参数名称的字符串数组
        String[] parameterNames = methodSignature.getParameterNames();
            //3.通过你需要获取的参数名称的下标获取到对应的值
        int loginNameIndex = ArrayUtils.indexOf(parameterNames, "loginName");
        if (loginNameIndex != -1) {
            String loginName = (String) args[loginNameIndex];
            // 调用工具类
            LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
            if (ObjectUtils.isEmpty(loginUser)) {
                return ResultMsgUtil.isError(ResultMsgEnum.RETURN_LOGIN);
            }
        }
        return  joinPoint.proceed(); //执行目标方法
    }
}
