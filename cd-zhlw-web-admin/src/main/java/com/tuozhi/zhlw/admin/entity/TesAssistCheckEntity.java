package com.tuozhi.zhlw.admin.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @Descriotion TODO 外部协查申请添加
 * @author liyuyan
 * @create 2019/10/08
 *
 */
@Entity
@Data
@Table(name="JC_INVESTIGATION_EXTERNAL")
public class TesAssistCheckEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
    private Long sid; //主键id
	
	private String vehicleid;//车辆车牌号码+颜色格式为：车牌号码+间隔符+车牌颜色编码
	
	private String initiatortype;//发起方类型 1：省中心2：发行方3：收费公路运营单位
	
	private String issuerid;//发行服务机构编号
	
	private String tollroadid;//收费路段编号
	
	private String messageid;//处理方省中心生成的文件 Id (上传标志位0未上传)

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createtime;//创建时间
	
	private String createdept;//创建部门

	private String evidence; //证据来源
	
	/*追加字段*/
	private String plateId;//车牌号
	private String plateColor;//车牌颜色
	private Long requestId;//外部（省部）协查编号
	private String roadName; //路段名称
	private String unitName; //发行方名称
}
