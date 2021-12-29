package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class NewExitETCPassData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sid;
    private String id;
    private String obuId;
    private String cardId;
    private String vehicleId;//实际收费车辆车牌号码+颜色
    private String identifyvehicleid;//出口识别车辆车牌号+颜色
    private String vehicleType;
    private String vehicleClass;
    private Integer exWeight;
    private Integer axleCount;//轴数
    private String description;
    private String specialType;
    private String vehiclesignid;//车牌识别流水号,外键
  /*  private String enTollLaneId;
    private String entolllanehex;//入口车道hex*/
  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;
    private String passId;
 /*   private Double transfee;//卡面扣费金额*/
    private Integer enweight;
    private Integer enenaxlecount;//入口轴数
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exTime;
    private Long requestId;
    private String eludemoneyType;//逃费类型
    private String eludemoneytypeitem;//逃费种类
    private Integer mediaType;//通行介质类型
    private String enumName;//车型
    private String enumName1;//车种
    private String eludeMoneyType1;
    private String eludeMoneyTypeItem1;
    private String enstationid;
    private String enstationname;
    private String exstationid;
    private String exstationname;
}
