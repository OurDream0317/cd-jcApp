package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class GtyBillingTransationEntity {
    private String tradeid; // 计费交易编号(唯一索引)
    private String gantryid;//门架编号
    private String gantryName;//门架名称
    private Integer gantryordernum;//门架顺序号，方向（1-上行，2下行）+序号（2位）
    private String gantryType;//门架类型: 1.路段;2.省界入口;3.省界出口
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transtime;
    private Double payfee;//应收金额
    private Double fee;//交易金额
    private Double discountfee;//优惠金额
    private Double transfee;//卡面扣费金额
    private Integer mediatype;//通行介质类型，1-OBU（单片式，双片式）2-CPC卡
    private Integer obusign;//OBU单/双片标识。1-单片式OBU 2-双片式OBU
    private Integer vehicletype;//入口车型
    private Integer vehicleclass;//车种
    private String obusn;
    private Integer passstate;//通行状态，1-有入口，2-无入口
    private String entolllaneid;//入口车道编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entime;//入口交易发生的时间 YYYY-MM-DDTHH:mm:ss
    private String enlanetype;//入口车道类型，1-混合（ETC+MTC），3-ETC专用
    private String passid;//通行标识ID
    private String vehicleplate;//计费车辆车牌号码+颜色,0-蓝色,1-黄色,2-黑色,3-白色,4-渐变绿色,5-黄绿双拼色,6-蓝白渐变色,7-临时牌照,9-未确定,11-绿色,12-红色
    private String obuvehicleplate;
    private Integer obuvehicletype;
    private Double vehicleseat;
    private Integer axlecount;
    private Double totalweight;
    private Double vehiclelength;
    private Double vehiclewidth;
    private Double vehiclehight;
    private String cpunetid;
    private String cpuvehicleplate;
    private Integer cpuvehicletype;
    private String cpucardid;
    private Integer gantrypasscount;
    private String gantrypassinfo;
    private Double obupayfeesumafter;
    private Double obudiscountfeesumafter;
    private Double obufeesumafter;
    private Double obuprovfeesumafter;
    private Integer holidaystate;
    private Integer traderesult;
    private Integer provminfee;
    private String specialtype;
    private String specialTypeName;//特情类型名称
    private Integer interruptsignal;
    private String vehiclepicid;
    private String vehicletailpicid;
    private Integer ratecompute;
    private Integer fitprovflag;
    private Double provminfeecalcmode;
    private Integer exitfeetype;
    private Integer obutraderesult;
    private String sectionName;
    private String feevehicletype;//计费车型
    private Double realfee;
    private Double owefee;
    private Integer newvehicletype;
    private Integer oldvehicletype;
    private Integer rn;//序号
}
