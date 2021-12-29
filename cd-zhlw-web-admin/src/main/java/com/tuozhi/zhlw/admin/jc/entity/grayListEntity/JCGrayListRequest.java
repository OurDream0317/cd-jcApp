package com.tuozhi.zhlw.admin.jc.entity.grayListEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCGrayListRequest implements Serializable {
    private static final long serialVersionUID = 1L;
    private  Integer requestId;
    private String carNumber;//车牌号码
    private String carFeature;//车辆特征
    private String carType;//车型
    private String carClass;//车种
    private String exitStationId;//出口站
    private String eludeMoneyNumber;//逃费金额
    private String eludeMoneyType;//逃费类型
    private String eludemoneytypeName;//逃费类型名称
    private String requestDescription;//备注
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;//创建时间
    private Integer loginUserId;//登录用户
    private String createUserName;//创建人
    private Integer requestStatus;//
    private String createDeptId;//创建部门ID
    private String entryStationId;//入口站
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date entryStationTime;//入口时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exitStationTime;//出口时间
    private String totalWeight;//总重
    private String factMoneyNumber;//实交金额
    private String axleType;//轴型
    private Integer currentFlowPathId;//当前环节
    private Integer logicType;//
    private Integer ferretoutStationId;//查获站
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date ferretoutStationTime;//查获时间
    private String disgorgeMentMoneyNumber;//追缴金额
    private Long cardId;//IC卡号
    private String haveDamageDevice;//有无设备损失;
    private String haveVideo;//有无录像备份
    private Integer haveCard;//有无赔卡
    private Integer workFlowDefinationId;//工作流编号
    private  String eludeMoneyTypeItem;//逃费种类
    private Integer cardDamageMoneny;
    private Integer sumTotal;//欠费条数
    private String cpuCardId;//cpu卡内部编号（cpu卡号）
    private String obuId;//obu卡号
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date reCoverTime;//认定追缴时间（根据这个时间来追缴钱）
    private Integer physicalTruthStatus;//实际车与发行不符 1符合0不符合
    private String devcarNumber;//设备车牌号
    private String phone;//联系人电话
    private Integer allRoadStatus;
    private Integer seatNum;//车座
    private Integer licenseColorId;//车牌颜色
    private Integer workFlowNodeId;
    private String  deptid;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")
    private Date fcreatetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd  HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd  HH:mm:ss",timezone = "GMT+8")
    private Date freadtime;
     private  String deptName;
    private String createStationName;
    private String entrystationName;
    private String exitstationName;
    private String redisDeptId;
    private String eludeMoneyTypeItem1;
    private String carTypeName;
    private Integer attachmentId;
    private Integer fileType;
    private Integer issuer;//发行省份编号
    private String issuername;//发行省份名称
    private String carclassname;//车种名称
    private String issuerBank;//发行方
    private String issuerBankName;//发行方名称

}
