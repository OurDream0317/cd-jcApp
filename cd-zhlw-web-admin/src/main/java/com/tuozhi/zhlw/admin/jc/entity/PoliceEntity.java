package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PoliceEntity implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	 private Integer policyId;
	 private String policyTitle;
	 private String policyFilepath;
	 @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	 private Date  beginDate;
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	 private Date  endDate;
	 @DateTimeFormat(pattern = "yyyy-MM-dd")
	 @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
	 private Date  createDate;
	 private Integer createDeptId;
	 private String createUserName;
	 private Integer loginUserId;
     private Integer flag;
}
