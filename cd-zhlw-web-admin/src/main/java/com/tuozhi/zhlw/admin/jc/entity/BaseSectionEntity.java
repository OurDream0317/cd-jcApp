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
 * @ClassName BaseSectionEntity
 * @Descriotion TODO 收费路段表
 * @Author liyuyan
 * @Date 2019/11/15 11:30
 * @Version 1.0
 */
//@Entity
@Table(name = "BASE_SECTION")
@Data
public class BaseSectionEntity implements Serializable {
    @Id
    private Long sid;
    private String Id;
    private String name;
    private Long type;
    private Long length;
    private String startStakEnum;
    private String endStakEnum;
    private Long tax;
    private String taxRate;
    private String sectionOwnerId;
    private Long chargeType;
    private String tollRoads;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date builtTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private String startLat;
    private String startLng;
    private String endLat;
    private String endLng;
    private Long nextTaxRate;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date nextRateDate;
    private Long shortId;
    private String tollRoadId;
    private Long sectionDirection;
    private Long branchId;
    private String cdOldId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdLastupDateTime;
    private Long cdDeleteFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdInsertTime;
    private String openFlag;
    private String reMark;
}
