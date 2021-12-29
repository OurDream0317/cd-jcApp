package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @author liyuyan
 * @create 2019/10/08
 *
 */

@Entity
@Data
@Table(name="JC_S_LANE_EXITETCTRANSAD")
public class TesEtcDataEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Long Sid; //主键ID
	
	private String Id;

	private String envehicleId;

	private String exvehicleId;
	
	private Long Fee;
	
	private Long discountFee;
	
	private Long payType;

	private String descRiption;
	
	private String specialType;
	
	private Long multiProvince;

	private Long enWeight;

	private Long enAxleCount;

	private String passId; //通行标识ID

	private String vehicleSignId;
	
	private Long discountType;
	
	private Long mediaType; //通行介质类型

	private String mediaNo;
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date enTime; //入口处理时间
	
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date exTime; //出口处理时间

	private Long enVehicleType;
	
	private Long enVehicleClass;
	
	private String enTollstationName;

	private Long exVehicleType;
	
	private Long exVehicleClass;
	
	private String exTollstationName;

	private Long payFee;
	
	private Long requestId; //关联外部协查请求表的id

	private String identifyVehicleId;
	
	private String eludemoneyType; //逃费类型
	
	private String eludemoneytypeTime;
}
