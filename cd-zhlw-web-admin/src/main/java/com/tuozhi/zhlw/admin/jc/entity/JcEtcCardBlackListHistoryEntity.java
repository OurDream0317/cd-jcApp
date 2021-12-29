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
 * @ClassName JcEtcCardBlackListHistoryEntity
 * @Descriotion TODO ETC发行黑名单历史表实体类
 * @Author liyuyan
 * @Date 2019/11/28 18:02
 * @Version 1.0
 */
@Entity
@Table(name = "JC_ETC_BLACKLIST_HISTORY")
@Data
public class JcEtcCardBlackListHistoryEntity implements Serializable {
    @Id
    private Long id;
    private Long avtId;
    private Integer rcardType;
    private String iccIssuer;
    private String cpuCardId;
    private String vehicleLicense;
    private Integer blackListType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Integer flag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date time;
    private String eludeMoneyType;
    private String eludeMoneyTypeItem;
    private String vehicleColor;
    private String carType;
    private String blackType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date recoverTime;
    private Double sumMoneny;
    private Integer sumTotal;
    private String obuId;
    private String entryStationId;
    private String exitStationId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entryStationTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exitStationTime;
    private Integer physicalTruthStatus;
    private String requestDescription;
    private String createName;
    private String createDept;
    private String createDeptName;
    private Long requestId;
    private Integer deleteFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deleteTime;
    private String devVehicleLicense;

    /*追加字段*/
    private String entryStationName; //入口路段名称
    private String exitStationName; //出口路段名称
}
