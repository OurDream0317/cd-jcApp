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
 * @Descriotion TODO 预追缴黑名单表
 * @author liyuyan
 * @create 2019/10/16
 *
 */
@Entity
@Data
@Table(name = "BASIC_AUDIT_PREBLACKLIST")
public class AuditPreblackListEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long Sid;

	/**
	 * 车辆车牌号码+颜色格式为：
	 * 车牌号码+间隔符+车牌颜色编码，间隔符：“_”；
	 * 例：京 A12345_1 。
	 */
	private String vehicleId;

	/**
	 * 状态
	 * 1：进入黑名单
	 * 2：解除黑名单
	 */
	private Long status;

	/**
	 * 进入黑名单原因
	 * 由两级构成，两级之间用“-”分割，
	 * 多个原因以“|”分割，具体编码参考：
	 * 表十五，例如：1-2|3-6
	 */
	private String reason;

	/**
	 * 欠费金额
	 * 单位（分）
	 */
	private Long oweFee;

	/**
	 * 欠费行为次数
	 * 已补费次数不计
	 */
	private Long evasionCount;

	/**
	 * 黑名单生成时间
	 * YYYY-MM-DDTHH:mm:ss
	 */
	private String creationTime;

	/**
	 * 版本号
	 */
	private Long version;

	/**
	 * 涉及OBU
	 * 涉及OBU及涉及ETC卡不能同时为空
	 */
	private String relateObu;

	/**
	 * 涉及ETC卡
	 * 涉及OBU及涉及ETC卡不能同时为空
	 */
	private String relateCardId;

	/*追加字段*/
	private String plateId;//车牌号
	private String plateColor;//车牌颜色
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createTime;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date endTime;
}
