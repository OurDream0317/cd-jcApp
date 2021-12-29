package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCBlacklistPayment implements Serializable {
    private static final long serialVersionUID = 1L;
    private long sid;//主键
    private String vehicle;//车辆车牌号码
    private Integer vehicleColor;//车牌颜色
    private String vehicleid;//车牌_颜色
    private String paybackuser;//补费人姓名
    private String paybackuserphone;//补费人联系方式
    private Integer transactiontype;//补费类型 (1：ETC交易 2：其他交易)
    private Integer paybacktype;//补费渠道 (1：部中心 2：省中心 3：发行方 4：路段)
    private String operator;//经办人姓名
    private String operateorg;//经办单位
    private String operateroad;//经办路段
    private String operatestation;//经办站
    private Double owefee;//欠费总金额，单位：分
    private Double paybackfee;//补费的金额，单位：分
    private Date paybacktime;//补费时间 (YYYY-MM-DDTHH:mm:ss)
    private String paybackdescription;//补费说明
    private String etccardid;//用户卡编号，当需要电子发票时必填
    private Integer paybackway;//1：主动补交 2：行业劝缴
    private Integer cartype;//车辆类别 1客车 2 货车 3专项作业车
    private String ownername;//车辆所有人
    private String owneridnum;//身份证
    private String enginenum;//发动机号
    private Long createuserid;//操作的账号用户id
    private String userName;
}
