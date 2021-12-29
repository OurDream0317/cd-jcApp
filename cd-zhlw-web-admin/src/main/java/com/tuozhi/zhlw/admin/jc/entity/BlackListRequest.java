package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuozhi.zhlw.admin.jc.util.DateFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


@Entity
@Table(name = "CD_JC.JC_BLACKLIST_REQUEST")
@Data
public class BlackListRequest implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@KeySql(sql = "select SEQ_JC_BLACKLIST_REQUEST.nextval from dual", order = ORDER.BEFORE)
	private Long requestId;
	
	private String  carNumber;//车辆  牌号
	
	private String  carFeature;//车辆特征
	private String  carFeature1;//车辆特征(老系统表)
	private Integer  carType;//车型
	
	private String exitStationId;//出口站
	
	private Double  eludeMoneyNumber;//逃费 金额
	private Double  eludeMoneyNumber1;//逃费 金额（老系统表）
	
	private Integer  eludeMoneyType;//逃费 类型,1 闯口, 2 倒卡 , 3 减轻自重 ,4 利用优惠政策 , 5 其他
	
	private String  requestDescription;//备注

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date    createTime;//创建时间
	
	private Long loginUserId;//登陆用户
	
	private String  createUserName;//创建人
	
	private Integer requestStatus;//0不可用, 1 可用, 2 办结
	
	private Long createDeptid;//创建部门
	
	private String entryStationId;//入口站

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date    entryStationTime;//入口时间

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date    exitStationTime;//出口时间
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private String    exitStationTime1;//出口时间（老系统表）
	private Integer  totalWeight;//总重
	
	private Double  factMoneyNumber;//实交金额

	private Double  payMoneyNumber;//应交金额
	private String  axleType;//轴型
	
	private Integer currentFlowpathId;//当前环节
	
	private Integer logicType;
	
	private String ferretOutStationId;//查获站
	private String ferretOutStationName;//查获站名称

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date    ferretOutStationTime;//查获时间
	
	private Double  disgorgementMoneyNumber;//追缴金额
	
	private String cardId;//IC卡号
	
	private Integer  haveDamageDevice;//有无设施损坏, 1有，0无
	
	private Integer  haveVideo;//有无录像备份，1有，0无
	
	private Integer  haveCard;//是否配卡，1是，0否

	private BigDecimal cardLossFee;//设施/损卡费金额
	
	private Integer workflowDefinationId;//工作流编号
	
	private Integer  eludeMoneyTypeItem;//逃费 种类
	
	private Integer sumtotal;//欠费条数(etc_card_blacklist）
	
    private String  cpuCardId;//cpu卡内部编号（cpu卡号）
	
	private String  obuId;//OBU卡号

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date    recoverTime;//认定追缴时间（根据这个时间来追缴钱）
	
	private Integer physicalTruthStatus;//实际车与发行不符 1符合0不符合
	
    private String  devcarNumber;//设备车牌号
	
	private String  phone;//联系人电话
	private String  phone1;//关联查询追缴黑名单
	private String  investigationId;//协查证据编号
	
	private String seatNum;//车座
	private String seatNum1;//车座（老系统表）
	
	private Integer deleteFlag ;//删除标记位

	private Integer carColour;//车辆  颜色

	private Integer mediaType;//通行介质类型（1-OBU,2-CPC卡,3-纸券,9-无通行介质）

	private String enSectionId; //入口路段编码

	private String exSectionId; //出口路段编码
	@JsonProperty(value = "sRequestId")
	private Long sRequestId;//对应的添加黑名单的RequestId

	@JsonProperty(value = "sRequestId1")
	private Long sRequestId1;//根据该值判断是否撤销
	private String issuer;//发行方
	/*
	追加字段
	 */
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date beginTime;//开始时间
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date endDate;//结束时间
	private Integer deptId;//当前送达部门ID
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date flowPathCreateTime;//送达时间
	private String deptName;//当前送达部门名称
	private String createStationName;//创建部门名称
	private String addEvadetollCount;//此申请对应的逃费记录数
	private Integer workflowNodeId;//工作流节点编号
	private Integer flowPathStatus;//当前环节状态
	private String vehicleId; //车牌号+车牌颜色
	private String enSectionName; //入口路段名称
	private String exSectionName; //出口路段名称
	private String entryStationName;//入口站名称
	private String exitStationName;//出口站名称
	private String exitStationName1;//出口站名称(老系统表)
	private String eludeMoneyTypeName;//逃费类型名称
	private String eludeMoneyTypeItemName;//逃费种类名称
	private String carColourName;//车辆颜色名称
	private String carTypeName;//车型名称
	private String haveCardName;//是否配卡名称
	private String haveDamageDeviceName;//有无设施损坏名称
	private String haveVideoName;//有无录像备份名称
	private Integer entrystationcode;//入口站7位编号
	private Integer exitstationcode;//出口站7位编号
    private Long companyDeptid;//所属公司部门ID

	private String RepairFeeUserName;//补费人姓名
	private String RepairFeePhone;//补费人联系电话
	private Integer isApp;
	private String pictureList;
	private String isTicket;
	@Transient
    public String getRECOVERTIMEStr() {
        if (getRecoverTime() != null) {
            return DateFormat.Date2StringFormat(getRecoverTime());
        }
        return "";
    }
	
	
	
	

	
	
	

	
	
}
