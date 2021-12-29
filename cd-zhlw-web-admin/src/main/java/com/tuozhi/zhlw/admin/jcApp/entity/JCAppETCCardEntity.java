package com.tuozhi.zhlw.admin.jcApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCAppETCCardEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String  plateno;
    private String cardVehicleColor;
    private String cardVehicleType;
    private String cardState;
    private String codeName;
    private String officeName;
    private String approvedCount;
    private String oicarUserType;
    private String oiid;
    private String oiplateNo;
    private String oiplateColor;
    private String oicarcategory;
    private String oistate;
    private String ownerName;
    private String oicarAxies;
    private String oicarTonnage;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String saleDate;
    private String createDept;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String createUserName;
    private String cpuCardid;
}
