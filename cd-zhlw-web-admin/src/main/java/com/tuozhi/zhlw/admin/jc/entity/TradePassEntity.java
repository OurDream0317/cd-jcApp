package com.tuozhi.zhlw.admin.jc.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Data
public class TradePassEntity implements Serializable {

    private static final long serialVersionUID = -689635701629762141L;
    /**
     *
     */

    private String passid;
    private String mediatype;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entime;
    private Integer cdpathfittingclass;
    /*@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")*/
    private Timestamp cdinserttime;
    private String entolllaneid;
    private String enprovinceid;
    private String entollstationid;
    private String cdpathfittingid;
    private String exprovinceid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date extime;
    private String extolllaneid;
    private String extollstationid;
    private String ensectionid;
    private String exsectionid;
    private String envehicleid;
    private String exvehicleid;
    private String mediano;
    private String obuid;
    private String exvehicletype;
    private String exvehicleclass;
    private String specialtype;
    private String enStationname;
    private String exStationName;
    private String enSectionName;
    private String exSectionName;
    private String envehicletype;
    private String envehicleclass;
    private String sectionidgroup;//途经路段
    private String cdetcdiscounttype;//优惠车类型:0普通车,1京津六五折,2预约绿通,3预约联合收割机,4天津港集装箱
    private String cdetcdiscountstate;//优惠状态: -1不满足优惠条件,0待确定,1满足优惠条件
    private String cdetcdiscountid;//优惠编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cdetcdiscouttime;//优惠状态修改时间
    private String cdetcfreeid;//免费编号
    private String cdetcfreestate;//免费状态: -1不满足免费条件,0待确定,1满足免费条件
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cdetcfreetime;//免费状态修改时间
    private String cdrefitting;//重新拟合：0不需要，1需要
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cdrefittingbytradetime;//重新拟合需要依据的交易时间
    private String exid;//出口交易编号
    private String enprovincename;
    private String exprovincename;
    private String issuerName;
    /*
        追加字段
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date startDate;//开始日期
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date endDate;//结束日期
    @SuppressWarnings("unused")
	private String envehicleNo;//入口车辆车牌号码
    private String envehicleColor;//入口车辆车牌颜色

	private String exvehicleNo;//实际收费车辆车牌号码
    private String exvehicleColor;//实际收费车辆车牌颜色
    private String mediatypename;//交易类型名称
    private String envehicletypename;//入口车型名称
    private String exvehicletypename;//出口车型名称
    private String envehicleclassname;//入口车种名称
    private String exvehicleclassname;//出口车种名称
    private String cdetcdiscounttypename;//优惠车类型名称
    private String cdetcdiscountstatename;//优惠状态名称
    private String cdetcfreestatename;//免费状态名称
    private String cdpathfittingclassname;//拟合依据类型名称
    private String cdrefittingname;//拟合状态名称
    private Integer isDispose;//特情是否处理
    private String entollstationname;//入口收费站
    private String extollstationname;//出口收费站
    private String issuerProcesser;//发行服务机构稽核人
    private String audroadProcesser;//稽核路段稽核人
    private String audroadProgressBar;//稽核路段进度
    private String issuer;//发行方
    private Double fee;
    private Double discountfee;
    private String paytype;
    private Double payfee;
    private Double otherfee;//cpc 金额 （STA_LANE_EXITOTHERTRANSAD）
    private Double countfee;//通过passid去门架通行交易表（TRADE_GANTRY）查询的交易金额
    private Double shortfee;
    private Double realfee;//大车小标计算后应收金额
    private Double owefee;//大车小标计算后逃费
    private String newvehicletype;
    private String nid;
    private String xid;
    private String vehicleSignId;
    private Integer exaxlecount;//轴数
    private Double vehiclelength;
    private Double vehiclewidth;
    private Double vehiclehight;
    private String exweight;
    private Integer exitfeetype;
    
    private String ENVEHICLENAME;//入口车牌（颜色已转为汉字）
    private String EXVEHICLENAME;//出口车牌（颜色已转为汉字）
    private String feevehicletype;//计费车型
    private String feemileage;//计费总里程

    private Double owerfee;
    private Double allfee;
    private String exitFeeTypeName;//计费方式名称
    private String envehicleSignid;//入口站车牌流水号
    private Double provincediscountfee;//省中心优惠金额

    private Integer exitMediatype;//出口通行介质类型
}
