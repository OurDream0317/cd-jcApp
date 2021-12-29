package com.tuozhi.zhlw.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.admin.mapper.LogMapper;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysLogService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


@Service
public class SysLogServiceImpl implements SysLogService {

    @Autowired
    LogMapper logMapper ;

    @Override
    public PageInfo<SysLogEntity> findAllByPageHelper(QueryParams queryParams, String startTime, String endTime,String loginName) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<SysLogEntity> sysLogEntityList = this.logMapper.findLogsByCondition(startTime,endTime,loginName) ;
        return new PageInfo<>(sysLogEntityList);
    }

    @Override
    public void insertLogIn(LoginUser loginUser,HttpServletRequest request) {
        this.logMapper.insertSelective(this.getLogEntity(loginUser,SysLogEntity.OperationType.登入.toString(),request)) ;
    }

    @Override
    public void insertLogOut(LoginUser loginUser,HttpServletRequest request) {
        this.logMapper.insertSelective(this.getLogEntity(loginUser,SysLogEntity.OperationType.登出.toString(),request)) ;
    }

    @Override
     public void inertByOperation(LoginUser loginUser,HttpServletRequest request,String desc){
        this.logMapper.insertSelective(this.getLogEntity(loginUser,desc,request)) ;
    }

    private SysLogEntity getLogEntity(LoginUser loginUser,String description,HttpServletRequest request){
        SysLogEntity sysLogEntity = new SysLogEntity() ;
        sysLogEntity.setOperateDescription(description);
        sysLogEntity.setLoginName(loginUser.getLoginName());
        sysLogEntity.setLoginUserId(loginUser.getUserId());
        sysLogEntity.setOperateTime(new Date());
        sysLogEntity.setLoginIp(this.getIpAddr(request));
        return sysLogEntity ;
    }

    /**
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址,
     *
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。
     *
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130,
     * 192.168.1.100
     *
     * 用户真实IP为： 192.168.1.110
     *
     * @param request
     * @return
     */
    private String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public void  dataTrans() {
        QueryParams queryParams = new QueryParams();
        queryParams.setLimit(10000);
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());

    }

    @Override
    public void insertMenuLog(LoginUser loginUser, String remark, HttpServletRequest request) {
        this.logMapper.insertSelective(this.getLogEntity(loginUser,remark,request)) ;
    }
}

