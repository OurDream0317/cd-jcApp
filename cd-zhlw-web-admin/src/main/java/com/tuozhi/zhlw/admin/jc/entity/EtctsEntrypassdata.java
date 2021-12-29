package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class EtctsEntrypassdata {
    private static final long serialVersionUID = 1L;
    private String id;
    private String mediaType;
    private String obuId;
    private String cardId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;
    private String vehicleId;
    private String vehicleType;
    private String vehicleClass;
    private String enVehicleClass;
    private Double transFee;
    private String enWeight;
    private Integer enAxlecount;
    private String specialType;
    private String vehiclesignId;
    private String passId;
    private Integer laneType;
    private String enStationName;
    private String enStationId;
    private String axleType;
    private String enTollLaneId;
    private String monitorType;
    private String enTollPlazaid;
    private Integer signStatus;
}
