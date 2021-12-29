package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
@Entity
public class BlacklistDownAll implements Serializable {
	@Id
	private String id;
    private static final long serialVersionUID = 1L;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date creationTime;//黑名单生成时间
    
    private Integer evasionCount;//欠费行为次数
    
	private String vehicleId;//车辆车牌号码+颜色
    
    private String type;//黑名单类型
    
    private String status;//状态
    
    private String version;//版本号

    private BigDecimal oweFee;//欠费金额
    
    private String reason;//进入黑名单原因
        
    private Long downId;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date putinTime;
    
    private String plateId;
    
    private String plateColor;
    
    
    
}
