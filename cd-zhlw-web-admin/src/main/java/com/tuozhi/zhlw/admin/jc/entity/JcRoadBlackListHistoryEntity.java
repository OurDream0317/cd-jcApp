package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName JcRoadBlackListHistoryEntity
 * @Descriotion TODO 稽查下发黑名单车道历史表实体类
 * @Author liyuyan
 * @Date 2019/11/06 19:58
 * @Version 1.0
 */
@Entity
@Table(name = "JC_ROAD_BLACKLIST_HISTORY")
@Data
public class JcRoadBlackListHistoryEntity implements Serializable {
    @Id
    private Long sid;
    private String listId; //UUID
    private Long stopStatus; //是否拦截（0不拦截，1入口拦截，2出口拦截，3出入口全部拦截）
    private String timeRange; //拦截的时间段
    private String road; //生效路段 默认为0代表拉黑全路段
    private String listVersion; //版本号
    private Long operateFlag; //当前黑名单录入还是移除 1添加2修改3删除
    private String vehicleLicense; //车牌号
    private Long vehicleLicenseColor; //车牌颜色
    private String cpuId;
    private String obuId;
    private Double evadeToll; //逃费金额
    private String htmlStr; //黑名单内容展示html字段
    private Long requestId; //黑名单关联的id(此字段不用下发)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private Long transFertag; //传输标志位
    private Long grayRequestId; //灰名单关联id
    private Long evasionType; //逃费类型
    private String evasionKind; //逃费种类
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date evadeTime; // 逃费时间
    private String evadeSite; //逃费地点
    private Long phoneNumber; //联系电话
    private Long lostCard;//是否赔卡
    private String caseDescription; //情况描述
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date cdInsertTime; //数据入库时间
    private Integer stopStatusType; //拦截类型（0：不拦截 1：ETC拦截 2：MTC拦截 3：ETC和MTC全部拦截）
    private Integer sumTotal; //欠费条数
    /*追加字段*/
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime; //创建开始时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime; //结束时间
    private String createUserName;
    private String evasiontypename;
    private String evasionkindname;
}
