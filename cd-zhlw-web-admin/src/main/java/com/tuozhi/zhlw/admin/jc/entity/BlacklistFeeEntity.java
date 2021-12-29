package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

//拆分欠费实体类
@Data
public class BlacklistFeeEntity implements Serializable {
    private String vehicle; //车牌号+颜色
    private String cpuId; //cpu卡号
    private String obuId; //obu卡号
    private String enstationid;//入口站编号
    private String enstationname;
    private String exstationid;
    private String exstationname;
    private Date enTime;
    private Date exTime;
    private String exvehicletypename;//出口车型名称
    private String newvehicletype;//判定车型
    private Double payfee;
    private Double realfee;//大车小标计算后应收金额
    private Double owefee;//大车小标计算后逃费
    private String passid;
    private String exid;
    private String multiprovince;
}
