package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCEntryPassData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sid;
    private String id;
    private Integer mediaType;
    private String obuId;
    private String cardId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;
    private String vehicleId;
    private Integer vehicleType;
    private Integer vehicleClass;
    private String enWeight;
    private String specialType;
    private String enTollLaneHex;
    private String passId;
    private Long requestId;
    private Integer enAxlecount;
    private String  rstationid; //入口收费站编号
    private String rstationname;//入口收费站名称
    private String vehiclesignid;//入口识别流水号
    private String identifyVehicleId;
    private String eludeMoneyType;
    private String eludeMoneyTypeItem;
    private String enumName;//车型
    private String enumName1;//车种
    private String eludeMoneyType1;
    private String eludeMoneyTypeItem1;
    private String enstationid;
    private String enstationname;
    private String exstationid;
    private String exstationname;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//创建时间
}
