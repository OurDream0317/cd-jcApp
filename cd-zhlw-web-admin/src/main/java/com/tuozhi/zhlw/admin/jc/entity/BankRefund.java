package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
@Data
public class BankRefund implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(generator="system-uuid")
    @SequenceGenerator(name = "system-uuid", sequenceName = "uuid")
    private String id;
    private String iccissuer;
    private String bankId;
    private String orderId;
    private String passId;
    private String plateNumber;
    private Integer plateColor;
    private String cardId;
    private Integer cardType;
    private String obuId;
    private Integer obuSign;
    private Integer vehicleType;
    private Integer vehicleTypeactual;
    private String enstationName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date enpassTime;
    private String exstationName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date expassTime;
    private Integer transClass;
    private Integer paycostsFee;
    private Integer orderStatus;
    private String title;
    private String messageId;
    private Integer transFlag;
    private String description;
    private String remark;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date insertTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date dealTime;
}
