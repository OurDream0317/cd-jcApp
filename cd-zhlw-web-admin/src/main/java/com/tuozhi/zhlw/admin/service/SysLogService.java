package com.tuozhi.zhlw.admin.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description 日志处理
 * @Author ma_zy
 * @Date Created in 17:57 2019/10/8
 * @Modified by
 */
public interface SysLogService {

    PageInfo<SysLogEntity> findAllByPageHelper(QueryParams queryParams, String startTime, String endTime,String loginName) ;

    void insertLogIn(LoginUser loginUser, HttpServletRequest request) ;
    void insertLogOut(LoginUser loginUser, HttpServletRequest request) ;
    void inertByOperation(LoginUser loginUser,HttpServletRequest request,String desc);

    void insertMenuLog(LoginUser loginUser, String remark, HttpServletRequest request);
}