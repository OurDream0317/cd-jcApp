package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCExitETCData implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private Integer mediaType;
    private String mediaNo;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;
    private String enVehicleId;
    private String exVehicleId;
    private Integer enVehicleType;
    private Integer exVehicleType;
    private Integer enVehicleClass;
    private Integer exVehicleClass;
    private String enTollStationName;
    private String exTollStationName;
    private Double fee;
    private Double disCountFee;
    private Integer payType;
    private String description;
    private String specialType;
    private String vehicleSignId;
    private  String passId;
    private Integer enaxlecount;
    private Integer enweight;
    private Integer multiprovince;
    private Integer payfee;
    private Integer sid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exTime;
    private String identifyvehicleid;
    private Long requestId;
    private String eludeMoneyType;
    private String eludeMoneyTypeItem;
    private String enumName;//入口车型
    private String enumName1;//入口车种
    private String enumName2;//出口车型
    private String enumName3;//出口车种
    private String eludeMoneyType1;
    private String eludeMoneyTypeItem1;
}
