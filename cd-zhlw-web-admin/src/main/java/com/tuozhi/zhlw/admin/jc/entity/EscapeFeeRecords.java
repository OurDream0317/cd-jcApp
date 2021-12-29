package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "CD_JC.JC_TRAFFIC_EVASION_RECORD")
@Data
public class  EscapeFeeRecords implements Serializable {
    @Id
    private Long sid;//序号
    private String id; //交易编号
    private double consumpotionFee;  //应收金额
    private double payFee;//实收金额
    private String entolllaneid;  //入口车道编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String entime;  //入口交易时间1
    private String etccardid;  //ETC卡编号
    private Integer mediatype;  //通行介质类型
    private String vehicleid;  //收费车辆车牌号码和颜色
    private Integer vehicleusertype;  //车辆用户类型
    private String specialtype;  //特殊类型1
    private Integer vehicleclass;  //车种1
    private Integer obusign;  //OBU单/双片标识1
    private String obuid;
    private Integer etccardtype;  //ETC卡类型
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String transtime;  //门架交易时间1
    private String tollintervalid; //收费单元编号1
    private String transtype;  //交易类型标识1
    private String tollgantryid;  //门架编号1
    private String passid;//通行ID
    private String issuerid;//发行方
    private String tollprovinceid;//服务方
    private Integer enweight;//入口车货总重
    private Integer enaxlecount;//入口车轴数
    private String tollintervalpayfee;//收费单元应收收费金额组合
    private String tollintervalfee;//收费单元交易金额组合
    private Integer enlanetype;//入口车道类型
    private String entollstationhex;//入口站车道类型
    private String sectionid;//收费路段编号
    private String entollstationname;//入口收费站名称
    private String tollintervalname;//收费单元名称
    private String identifyvehicleid;//门架识别的车牌号码+颜色
    private String description;//对交易文字解释
    private String extolllaneid;//出口车道编号
    private String sectionname;//收费路段名称
    private Long requestId;//协查请求编号
    private String eludemoneyType;//逃费类型
    private String eludemoneytypeitem;//逃费种类
    private Double oweFee;//欠费金额
    private String operationer;//操作人
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date operationTime;//操作时间
    private String vehicletype;//计费车型
    @Transient
    private String enumName;
    @Transient
    private String enumName1;
    @Transient
    private String eludemoneyType1;
    @Transient
    private String eludemoneytypeitem1;
    private Integer flag;
}