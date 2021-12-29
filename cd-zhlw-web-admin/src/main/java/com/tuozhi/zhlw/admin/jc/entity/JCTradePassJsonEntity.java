package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JCTradePassJsonEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String enPlateHex;
    private Integer enPlateColor;
    private Integer enVehicleType;
    private Integer enVehicleClass;
    private String exPlateHex;
    private Integer exPlateColor;
    private Integer exVehicleType;
    private Integer exVehicleClass;
    private Integer totalWeight;
    private String requestID;
    private String passId;
    private String enLaneType;
    private String enTollStationHex;
    private String enTime;
    private String enTollLaneId;
    private String enTollLaneHex;
    private String exTollLaneId;
    private String exTollLaneHex;
    private String exLaneType;
    private String exTollStationId;
    private String exTollStationHex;
    private String exTime;
    private String lastGantryHex;
    private String lastPasstime;
    private Integer axleCount;
    private Integer passStatus;
    private Integer tagType;
    private Integer cardType;
    private Integer cardVer;
    private String obuSn;
    private String cpuCardId;
    private Integer vehicleLength;
    private Integer vehicleWidth;
    private Integer vehicleHight;
    private Integer vehicleWeightLimits;
    private String cpcCardGantryFees;
    private String cpcCardProvinceFees;
    private String proxyGantryHex;
    private String proxyGantryId;
    private String proxyGantryTransaction;



}
