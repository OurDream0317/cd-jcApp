package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName BaseIssuerEntity
 * @Descriotion TODO 发行方信息表
 * @Author liyuyan
 * @Date 2019/12/01 11:30
 * @Version 1.0
 */
//@Entity
@Table(name = "BASE_SECTION")
@Data
public class BaseIssuerEntity implements Serializable {
    @Id
    private Long sid;
    private String id;
    private String name;
    private String contact;
    private String tel;
    private String address;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private String cdOldId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdLastUpDateTime;
    private String bank;
    private String bankAddr;
    private String bankacCount;
    private String taxPayerCode;
    private String creditCode;
    private Integer cdDeleteFlag;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdInsertTime;
}
