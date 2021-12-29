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
 * @ClassName JcSLaneAudresultEntity
 * @Descriotion TODO 协查门架的收费单元逃费证据表
 * @Author fujiankang
 * @Date 2019/12/24 21:17
 * @Version 1.0
 */
@Entity
@Table(name = "CD_JC.JC_S_LANE_AUDRESULT")
@Data
public class JcSLaneAudresultEntity implements Serializable {
    @Id
    private Long sId;//
    private String listno;//交易编号计费交易编号（id）=OBU 序号编码/CPC 卡编码+交易发生时间+流水号
    private String tollintervalId;//补费收费单元
    private String vehiclePlate;//计费车辆车牌号码+颜色
    private String exVehicleType;//出口车型
    private String exVehicleClass;//出口车种
    private String tollgantryId;//门架编号
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transTime;//门架交易时间
    private String passId;//通行标识ID
    private Integer mediaType;//通行介质类型
    private Double orginalFee;//实收金额 单位：分
    private Double realFee;//应收金额 单位：分
    private Double oweFee;//欠费金额 单位：分
    private Integer oweFeeType;//欠费金额类型 1：发行方欠费2：路段单位欠费 3：客户欠费
    private Long operatorId;//最后操作人ID
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date operatorTime;//最后操作时间
    private Integer realVehicleType;//实际车型
    private Long requestId;

}