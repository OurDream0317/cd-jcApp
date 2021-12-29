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
 * @ClassName JcEvadetollRecordEntity
 * @Descriotion TODO 逃费记录表
 * @Author fujiankang
 * @Date 2019/11/6 11:54
 * @Version 1.0
 */
@Entity
@Table(name = "JC_EVADETOLL_RECORD")
@Data
public class JcEvadetollRecordEntity implements Serializable {
    @Id
    private Long ids;
    private Integer recordid;//逃费记录ID
    private String vehiclelicense;//车牌
    private String rstationid;//入口站号
    private String rstationflag;//入口广场号
    private String exstationid;//出口站号
    private String exstationflag;//出口广场号
    private Integer exvehicleclass;//车型
    private String listno;//原始记录的唯一标识流水号
    private Double finetoll;//收费金额
    private Double recalculatetoll;//重新计算后的应收金额
    private Double evadetoll;//逃费金额
    private Integer disgorgementstatus;//追缴状态，0未追缴，1已追缴
    private String tolllistver;//拆账费率标识
    private Integer isallsplittoroad;//拆账类型：0按路径精确拆分，1全拨付路段,2按照路段长度比例拆分
    private String allsplittoroadid;//全拨付路段编号（依据拆账类型）
    private String remark;//备注
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date splitdate;//拆账日期
    private Integer splitstatus;//拆分状态（100001 已拆分， 100002 未拆分，100003 非精确拆分交易已拆分）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date passtime;//通过时间
    private Integer rroadid;//入口ID
    private Integer exroadid;//出口ID
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date checkdate;//对账时间
    private Integer checkblacklistcancelid;//对账的撤销申请ID
    private Integer checkuserid;//对账人的ID
    private Long blacklistaddid;//对应的黑名单添加申请编号ID
    private Long createuserid;//创建用户
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createdate;//创建时间
    private Integer jcfundid;//稽查经费申请编号ID
    private Integer datasource;//数据源添加出处黑名单1 稽查经费2 手动排查3
    private Integer investigationid;//协查请求Funid
    private Integer cordid;//与协查表互相关联的ID
    private Integer recoveryid;//合作追缴ID
    private Integer carColour;//车辆颜色
    private Integer compensateType;//赔偿类型（1：逃费数据 2：设施赔偿 3：CPC卡费）
}