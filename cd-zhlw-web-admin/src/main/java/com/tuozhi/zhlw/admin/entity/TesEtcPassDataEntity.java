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
@Table(name="JC_S_LANE_EXITETCPASSDATA")
public class TesEtcPassDataEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private Long Sid; //主键ID

	private String Id;

	private String obuId; //OBU序号编码

	private String cardId; //CPC卡或者ETC卡编号

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date exTime; //出口处理时间

	private String vehicleId;

	private String identifyVehicleId;

	private String vehicleType;

	private String vehicleClass;

	private Long exWeight;

	private Long axleCount;

	private String descRiption;

	private String specialType;

	private String entolllaneId; //入口车道

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date enTime; //入口处理时间

	private String exsectionId;

	private Long requestId; //关联外部协查请求表的id

	private String eludemoneyType; //逃费类型

	private String eludemoneytypeTime;

	private String passId; //通行标识ID

	private Long mediaType; //通行介质类型

	private Long enWeight;

	private Long enEnaxleCount;
}
