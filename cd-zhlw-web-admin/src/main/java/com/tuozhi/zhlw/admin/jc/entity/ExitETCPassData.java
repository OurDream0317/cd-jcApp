package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class ExitETCPassData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sid;
    private String id;
    private Integer obuSign;
    private String terminaltransno;//PSAM卡脱机交易序列号
    private String obuId;
    private String cardId;
    private String vehicleId;//实际收费车辆车牌号码+颜色
    private String identifyvehicleid;//出口识别车辆车牌号+颜色
    private String vehicleType;
    private String vehicleClass;
    private String tac;
    private String transtype;
    private String terminalno;//终端机编号
    private Integer exWeight;
    private Integer axleCount;//轴数
    private Integer electricalpercentage;
    private String signStatus;
    private String description;
    private String specialType;
    private String vehiclesignid;//车牌识别流水号,外键
    private String enTollLaneId;
    private String entolllanehex;//入口车道hex
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date enTime;
    private String passId;
    private Double transfee;//卡面扣费金额
    private Integer enweight;
    private Integer enenaxlecount;//入口轴数
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date exTime;
    private Integer status;
    private String enumName;//入口车型
    private String enumName1;//入口车种
    private String enstationid;
    private String enstationname;
    private String exstationid;
    private String exstationname;
    private Long rs;
}
