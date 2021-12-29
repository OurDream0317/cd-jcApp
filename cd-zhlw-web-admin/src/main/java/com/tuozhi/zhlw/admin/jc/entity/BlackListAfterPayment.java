package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @ClassName BlackListAfterPayment
 * @Descriotion TODO 稽查下发黑名单车道表实体类
 * @Author jxc
 * @Date 2019/11/06 19:45
 * @Version 1.0
 */
@Entity
@Table(name = "JC_BLACKLIST_EVIDENCE_DETAILS")
@Data
public class BlackListAfterPayment implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
    private Long sId;
    private String listId; //
    private String passId; //
    private String vehicleLicense; //
    private Integer vehicleLicensecolor; //


    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date enTime; //
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date exTime; //
    private String enstationName; //
    private String exstationName; //
    private String enstationId;
    private String exststionId;
    private String cardId; //
    private String obuId; //
    private String vehicleType; //
    private Integer correctVehicleType;
    private BigDecimal fee; //
    private BigDecimal correctFee; //
    private BigDecimal oweFee; //
    private BigDecimal paybackFee; //
    private Integer payFlag; //

    private String issure;//发行省份（不填写认可为MTC）
    private String issure1;//发行省份（不填写认可为MTC）（导入）
    private String inAxle;//入口轴数
    private String outAxle;//出口轴数
    private String realAxle;//正确轴数
    private String vehicleClass;//车种
    private String transprovincial;//是否跨省
    private String transprovincial1;//是否跨省(手动)
    private Long requestId;//关联黑名单申请编号
    private Integer flag;
}
