package com.tuozhi.zhlw.admin.pojo;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author linqi
 * @create 2019/09/05 0:46
 **/
public class SessionUtils {
    /**
     * 用户信息
     */
    public static final String SESSION_USER = "_sessionUser";

    /**
     * 用户权限
     */
    public static final String SESSION_USER_PERMISSION = "_sessionUserPermission";

    public static SessionUser getSessionUser(HttpServletRequest request) {
        return (SessionUser) WebUtils.getSessionAttribute(request, SESSION_USER);
    }

    public static void setSessionUser(HttpServletRequest request, SessionUser sessionUser) {
        WebUtils.setSessionAttribute(request, SESSION_USER, sessionUser);
    }

    public static SessionPermission getSessionPermission(HttpServletRequest request) {
        return (SessionPermission) WebUtils.getSessionAttribute(request, SESSION_USER_PERMISSION);
    }

    public static void setSessionPermission(HttpServletRequest request, SessionPermission sessionPermission) {
        WebUtils.setSessionAttribute(request, SESSION_USER_PERMISSION, sessionPermission);
    }

    public static void invalidate(HttpServletRequest request){
        setSessionUser(request, null);
        setSessionPermission(request, null);
    }
}
