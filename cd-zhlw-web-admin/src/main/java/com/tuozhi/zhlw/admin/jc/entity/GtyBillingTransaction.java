package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ma_zy
 * @Date: 2019/12/2 10:48
 * @Description:
 */
@Data
@Table(name = "gty_billing_transaction")
public class GtyBillingTransaction implements Serializable {

    private String tradeid;	//计费交易编号(唯一索引)
    private String gantryid;//门架编号
    private String gantryordernum;	//门架顺序号，方向（1-上行，2下行）+序号（2位）
    private String gantryhex;	//门架Hex值
    private String gantryType;//门架类型
    /* 创建时间 */
    @Column(name = "TRANSTIME")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date transtime;	//计费交易时间
    private Double payfee;	//应收金额
    private Double discountfee;	//优惠金额
    private Double transfee;	//前端扣费金额
    private Integer mediatype;	//通行介质类型，1-OBU（单片式，双片式）2-CPC卡
    private String vehicleplate;	//计费车辆车牌号码+颜色，0-蓝色，1-黄色，2-黑色，3-白色，4-渐变绿色，5-黄绿双拼色，6-蓝白渐变色，7-临时牌照，9-未确定，11-绿色，12-红色
    private String vehicletype;	//计费车型，默认0，1-一型客车，2-二型客车，3-三型客车，4-四型客车，11-一型货车，12-二型货车，13-三型货车，14-四型货车，15-五型货车，16-六型货车，21-一型专项作业车，22-二型专项作业车，23-三型专项作业车，24-四型专项作业车，25-五型专项作业车，26-六型专项作业车
    private String vehicleclass;	//车种
    private String passstate;	//通行状态，1-有入口，2-无入口
    private String entolllaneid;	//入口车道编号
    private String  entollstationhex;	//入口站hex字符串
    @Column(name = "ENTIME")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entime;	//入口交易发生的时间 YYYY-MM-DDTHH:mm:ss
    private String enlanetype;	//入口车道类型，1-混合（ETC+MTC），3-ETC专用
    private String passid;	//通行标识ID
    private String lastgantryhex;	//上一个门架的 hex编号
    private String lastgantrytime;	//通过上一个门架的时间

    private String obusn;	//OBU/CPC序号编码
    private String obuvehicleplate;	//OBU内车牌号码
    private String obuvehicletype;	//OBU内车型
    private String vehicleusertype;	//车辆用户类型
    private String vehicleseat;	//车辆座位数/载重（kg）
    private String axlecount;	//车轴数。大于等于2，货车必填。
    private String totalweight;	//车货总重（kg）
    private String vehiclelength;	//车辆长（dm）
    private String vehiclewidth;	//车辆宽（dm）
    private String vehiclehight;	//车辆高（dm）
    private String cpunetid;	//CPU卡片网络编号
    private String cpuissueid;	//CPU卡片发行方标识
    private String cpuvehicleplate;	//CPU内车牌号码
    private String cpuvehicletype;	//CPU内车型
    private String cpucardtype;	//CPU卡类型，0-无卡，1-储值卡，2-记账卡
    private String cpucardid;	//CPU卡编号
    private String holidaystate;	//节假日状态，0-非节假日，1-节假日，交易成功必填
    private String traderesult;	//交易结果，0-交易成功，1-交易失败
    private String specialtype;	//门架特情类型
    private String specialtypename;//门架特情类型（中文）
    private String vehiclepicid;	//车牌识别流水号
    private String vehicletailpicid;	//车牌识别流水号(车尾)
    private Double fee;	//交易金额
    private String vehiclesign;	//车辆状态标识，0x00-大件运输 0x01-非优惠车 0x02-绿通车 0x03-联合收割机 0x04-集装箱车 0x05-0xfe预留 0xff为默认值
    private String feecalcspecial;	//计费接口特情值。默认值-1
    private String chargesspecialtype;	//收费特情类型
    private String gantrysectionid;	//门架所属路段编号
    private String feevehicletype;//计费车型

    private String tradeName;//门架名字
    private String sectionName;//路段名字
    private String tollintervalid;//收费单元组合
    private Double realfee;
    private Double owefee;
    private Integer newvehicletype;
    private Integer oldvehicletype;
    private Integer rownum;
}
