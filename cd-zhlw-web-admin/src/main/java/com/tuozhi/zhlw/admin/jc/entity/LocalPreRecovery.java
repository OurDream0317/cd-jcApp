package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/** 
  * @author  作者: jxc
  * @date 创建时间：2020年7月13日 下午4:17:20 
  * @version 1.0 
*/
@Data
public class LocalPreRecovery  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String vehicleId;
	private String reason;
	private Integer oweFeeAll;
	private Integer evasionCount;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date creationTime;
	private String version;
	private Integer downId;
	private Integer id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date putinTime;
	private String plant;
	private String color;
	private List<TransactionData> transactions;
	private String colorText;

}
