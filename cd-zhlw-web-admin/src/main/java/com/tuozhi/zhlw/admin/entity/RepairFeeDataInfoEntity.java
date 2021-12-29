package com.tuozhi.zhlw.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @ClassName AuditDissentAttachmentEntity
 * @Descriotion TODO 补费数据信息表
 * @Author liyuyan
 * @Date 2019/10/30 19:29
 * @Version 1.0
 */
@Entity
@Table(name = "BASIC_JC_REPAIRFEEDATAINFO")
@Data
public class RepairFeeDataInfoEntity {

    @Id
    private Long Id;

    private String passId;

    private String transactionId;

    private String mediaType;

    private String obuId;

    private String cardId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime;

    private String enLaneId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exTime;

    private String exLaneId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date realEnTime;

    private String realEnLaneId;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date realExTime;

    private String realExLaneId;

    private Long realVehicleType;

    private Long realVehicleClass;

    private String confirmedEscapeType;

    private String oweFee;

    private String payBackStatus;

    private Long Sid; //关联车辆补费结果的ID

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdInsertTime;
}
