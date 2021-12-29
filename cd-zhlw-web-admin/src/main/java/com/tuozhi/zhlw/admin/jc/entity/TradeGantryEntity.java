package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tuozhi.zhlw.common.utils.DateFormat;
import com.tuozhi.zhlw.common.utils.StringUtils;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @ClassName TradeGantryEntity
 * @Descriotion TODO 门架可扣费交易表
 * @Author fujiankang
 * @Date 2019/10/28 11:32
 * @Version 1.0
 */
@Entity
@Table(name = "TRADE_GANTRY")
@Data
public class TradeGantryEntity {
    @Id
    private String id;
    private String sectionname;
    private Double fee;
    private Double discountfee;
    private String entolllaneid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date entime;
    private String etccardid;
    private String vehicleid;
    private String specialtype;
    private String vehicleclass;
    private String rateversion;
    private Integer obusign;
    private String obuid;
    private String etccardtype;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date transtime;
    private String tollintervalid;
    private String vehicletype;
    private String tac;
    private Double transfee;
    private Integer signstatus;
    private String terminaltransno;
    private String terminalno;
    private String servicetype;
    private String enpassrecordid;
    private String forwardetcrecordid;
    private String backwardetcrecordid;
    private String tollgantryid;
    private Integer manualsign;
    private String vehiclesignid;
    private String passid;
    @JsonProperty(value = "cdtransactiontype")
    private String cdtransactiontype;
    private String issuerid;
    private String tollprovinceid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "cdinserttime")
    private Timestamp cdinserttime;
    private Double payfee;
    private Integer enweight;
    private Integer enaxlecount;
    private String tollintervalpayfee;
    private String tollintervaldiscountfee;
    private String tollintervalfee;
    private String enlanetype;
    private String entollstationhex;
    private String cpunetid;
    private Integer vehicleusertype;
    private Integer obuversion;
    private Integer electricalpercentage;
    private String obumac;
    private String obuissueid;
    private String obusn;
    private String cpuissueid;
    private Integer cpuversion;
    private Long reqmessageid;//TODO 该字段库中已删除
    private String sectionid;
    private String entollstationname;
    private String tollintervalname;
    private String identifyvehicleid;
    private String identifyvehicletype;
    private String transtype;
    private String description;
    private String keyversion;
    private String direction;
    private String link;
    private String vehiclesignids;
    private String lastgantryhex;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date lastgantrytime;
    private Integer vehiclelength;
    private Integer vehiclewidth;
    private Integer vehiclehight;
    private Integer vehicleweightlimits;
    private String platecolor;
    private String feeinfo1;
    private String feeinfo2;
    private String feeinfo3;
    private String feelogmsg;
    private String mediatype;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cleandate;
    private String cleanstatus;
    private Integer cdreqmessageid;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date cdpackagetime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date disputecleandate;
    private String disputestatus;
    private Integer disputetype;
    private Integer obuvehicletype;
    private Integer cdEtcdiscounttype;
    /*
    追加字段
     */
    private String vehicleNo;
    private String vehicleColor;
    private String identifyvehicleNo;
    private String identifyvehicleColor;
    private Double realfee;
    private Double owefee;
    private Integer newvehicletype;
    private Integer oldvehicletype;
    @SuppressWarnings("unused")
	private String identifyvehicleidnum;
	private String identifyvehicleidcolor;
	
	private String  tollgantryname;
	@Transient
    public String getIdentifyvehicleidnum() {
        if (StringUtils.isNotEmpty(getVehicleid()) && getVehicleid().length() >= 9) {
            return getVehicleid().substring(0, 7);
        }
        return "";
    }

    @Transient
    public String getPlateNum() {
        if (StringUtils.isNotEmpty(getIdentifyvehicleid()) && getIdentifyvehicleid().length() >= 7) {
            return getIdentifyvehicleid().substring(0, 7);
        }
        return "";
    }


    @Transient
    public String getStarttimeStr() {
        if (getTranstime() != null) {
            return DateFormat.Date2StringFormat(getTranstime());
        }
        return "";
    }

    @Transient
    public String getEndtimeStr() {
        if (getTranstime() != null) {
            return DateFormat.Date2StringFormat(getTranstime());
        }
        return "";
    }


}