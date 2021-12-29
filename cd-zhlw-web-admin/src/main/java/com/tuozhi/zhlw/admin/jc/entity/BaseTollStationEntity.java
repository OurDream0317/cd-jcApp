package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseTollStationEntity
 * @Descriotion TODO 收费站信息表
 * @Author liyuyan
 * @Date 2019/11/15 14:42
 * @Version 1.0
 */
//@Entity
@Table(name = "BASE_TOLLSTATION")
@Data
public class BaseTollStationEntity implements Serializable {
    @javax.persistence.Id
    private Long sid;
    private String Id;
    private String name;
    private Long type;
    private Long tollPlazaCount;
    private String StationHex;
    private Long shortId;
    private Long jsPcount;
    private String cdOldId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdLastUpdateTime;
    private Long cdDeleteFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdInsertTime;
    private Long lineType;
    private Long operators;
    private String dataMergePoint;
    private String imei;
    private String ip;
    private String snmpVersion;
    private String snmpPort;
    private String community;
    private String securityName;
    private String securityLevel;
    private String authEntication;
    private String authKey;
    private String enCryption;
    private String secretKey;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    private String reMark;
    private String sectionId;
    private Long tollPlazaCountOut;
    private Long tollPlazaCountIn;
}
