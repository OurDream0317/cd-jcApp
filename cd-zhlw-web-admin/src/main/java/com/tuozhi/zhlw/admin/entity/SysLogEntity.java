package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.*;
import java.util.Date;

/**
 * @author linqi
 * @create 2019-08-30 18:09
 **/


@Entity
@Table(name="SYS_LOG")
@Data
public class SysLogEntity {

   public enum OperationType{
        登入,登出,授权角色与菜单关系,新增角色,删除角色,新增人员,删除人员;
    }


    @Id
    @KeySql(sql = "select SEQ_SYS_LOG.nextval from dual", order = ORDER.BEFORE)
    private String logId;//Controller层的ID 日志ID

    @Column(name = "LOG_MODEL")
    private String logModel;    //操作的模块 模块名称

    @Column(name = "LOGIN_USER_ID")
    private Long loginUserId ; // 当前登录的用户ID

    @Column(name = "LOGIN_NAME")
    private String loginName;    //当前登录的用户名

    @Column(name = "OPERATE_DESCRIPTION")
    private String operateDescription;  //当前 操作描述

    @Column(name = "REQUEST_URI")
    private String requestUri;  //当前用户的请求uri

    @Column(name = "OPERATE_TIME")
    private Date operateTime;//当前操作时间

    @Column(name = "DETAILS")
    private String details;//操作详情 控制器接收到的参数的值

    @Column(name = "MODEL")
    private String model ; // 模块

    @Column(name = "LOGIN_IP")
    private String loginIp ; // 登录ip

}
