package com.tuozhi.zhlw.admin.aspect;


import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysLogService;
import com.tuozhi.zhlw.common.annotation.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.StringJoiner;

/**
 * @author linqi
 * @create 2019-08-30 18:48
 **/
@Slf4j
@Aspect
@Component
public class SysLogAspect extends BaseController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;


    @Pointcut("@annotation(com.tuozhi.zhlw.common.annotation.SysLog)")
    public void logPointCut() {
    }

    /**
     * @param point
     * @return
     * @throws Throwable
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        try {
            saveLog(point);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * 保存日志
     *
     * @param joinPoint
     */
    private void saveLog(ProceedingJoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();

        Object[] paramValues = joinPoint.getArgs();
        String[] paramNames = ((CodeSignature) joinPoint.getSignature()).getParameterNames();


        SysLogEntity sysLogEntity = new SysLogEntity();
        sysLogEntity.setOperateTime(new Date());
        LoginUser loginUser = getLoginUser(request);

        SysLog sysLog = method.getAnnotation(SysLog.class);
        String sysLogType = sysLog.value();
        if (sysLogType.equals(SysLogEntity.OperationType.授权角色与菜单关系.toString())) {
            StringJoiner stringJoiner = new StringJoiner(",");
            for (int i = 0; i < paramNames.length; i++) {
                stringJoiner.add(paramNames[i] + ":" + paramValues[i]);
            }
            sysLogType = sysLogType +";" + stringJoiner.toString();
        }
        if (loginUser != null) {
            //注解上的描述
            sysLogService.inertByOperation(loginUser, request, sysLogType);
        }


    }


}
