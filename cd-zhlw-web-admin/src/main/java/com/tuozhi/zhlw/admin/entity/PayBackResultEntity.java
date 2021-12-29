package com.tuozhi.zhlw.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *
 * @Descriotion TODO 车辆补费结果上传表
 * @author liyuyan
 * @create 2019/10/15
 *
 */
@Entity
@Data
@Table(name = "JC_PAYBACKRESULT")
public class PayBackResultEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	private Long Sid;
	
	private String vehicleId; //车辆车牌号码+颜色
	
	private String passId; //通行标识 ID
	
	private String paybackUser; //补费人姓名
	
	private String paybackUserPhone; //补费人联系方式
	
	private String transactionType; //补费类型
	
	private String paybackType; //补费渠道
	
	private String operator; //经办人姓名
	
	private String operateOrg; //经办单位
	
	private String operateRoad; //经办路段
	
	private String operateStation; //经办站
	
	private Double oweFee; //欠费金额
	
	private Double payBackFee; //补费金额

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date payBackTime; //补费时间

	private String messageId;

	private String payBackDescription; //补费说明new

	private String userCardId; //用户卡编号new

	/*追加字段*/
	private String plateId;//车牌号
	private String plateColor;//车牌颜色
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date endTime;
	private String roadName; //路段民称
	private String tollName; //收费站名称
	private String unitName; //经办单位名称
}
