package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCGantryFitting implements Serializable {
    private String id;//门架编号
    private String name;//门架名称
    private Integer fee;//交易金额
    private Integer discountFee;//优惠金额
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;//入口时间
    private String etcCardId;//ETC卡编号
    private String vehicleId;//车牌号_颜色
    private Integer vehicleType;//车型
    private Integer vehicleClass;//车种
    private String obuId;//obuID
    private Integer etcCardType;//ETC卡类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date transTime;//门架交易时间
    private String tollIntervalId;//收费单元编号
    private Integer transFee;//门架扣费金额
    private String passId;//通行ID
    private String issuerId;//发行方
    private Integer payFee;//应收金额=交易金额+优惠金额
    private Integer enWeight;//重量
    private Integer enAxleCount;//轴数
    private String tollIntervalPayFee;//收费单元应收收费金额组合
    private String tollIntervalDiscountFee;//收费单元优惠金额组合
    private String tollIntervalFee;//收费单元交易金额组合
    private Integer plateColor;//车牌颜色
    private String feeLogMsg;
    private String sectionId;//计费单元路段编号
    private String enTollStationName;//入口收费站
    private String tollIntervalName;//收费单元名称
    private String sectionName;//收费路段名称
    private String tollProvinceId;//发行方
}
