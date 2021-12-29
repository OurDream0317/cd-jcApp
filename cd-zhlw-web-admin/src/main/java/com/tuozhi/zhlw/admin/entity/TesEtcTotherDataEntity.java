package com.tuozhi.zhlw.admin.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Data
@Table(name="JC_S_LANE_EXITOTHERTRANSAD")
public class TesEtcTotherDataEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long Sid; //主键ID
	
	private String Id;

	private String enVehicleId;

	private String exVehicleId;

	private String identifyVehicleId;

	private Long enVehicleType;

	private Long exVehicleType;

	private String enVehicleClass;

	private String exVehicleClass;

	private String enTollstationName;

	private String exTollstationName;

	private Long requestId; //关联外部协查请求表的id

	private String eludemoneyType; //逃费类型

	private String eludemoneytypeTime;
	
	private Long Fee;
	
	private Long discountFee;
	
	private Long payType;
	
	private String specialType;

	private String vehicleSignId;//车牌识别流水号
	
	private String passId; //通行标识ID

	private Long mediaType; //通行介质类型

	private String mediaNo;

	private Long multiProvince;

	private Long enAxleCount;

	private Long enWeight;

	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date exTime; //出口处理时间
}
