package com.tuozhi.zhlw.admin.pojo;

/**
 * @author linqi
 * @create 2019/09/05 0:47
 **/
public class SessionUser {
    private static final long serialVersionUID = 1764365572138947234L;

    // 登录用户访问Token
    private String token;
    // 登录名
    private String loginName;

    public SessionUser() {
        super();
    }

    public SessionUser(String token, String account) {
        super();
        this.token = token;
        this.loginName = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAccount() {
        return loginName;
    }

    public void setAccount(String account) {
        this.loginName = account;
    }
}
