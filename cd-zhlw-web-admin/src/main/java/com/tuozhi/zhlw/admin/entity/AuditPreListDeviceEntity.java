package com.tuozhi.zhlw.admin.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @Descriotion TODO 预追缴黑名单下载设备表
 * @author liyuyan
 * @create 2019/10/16
 *
 */
@Entity
@Data
@Table(name = "BASIC_AUDIT_PREBLACKLISTDEVICE")
public class AuditPreListDeviceEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long Sid;
	
	private String obuId; //OBU序号编码
	
	private String cardId; //ETC卡编码
	
	private Long auditId; //外部关联id

}
