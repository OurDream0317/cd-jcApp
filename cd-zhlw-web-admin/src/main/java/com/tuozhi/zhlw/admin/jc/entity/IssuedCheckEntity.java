package com.tuozhi.zhlw.admin.jc.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Transient;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class IssuedCheckEntity implements Serializable{/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private String vehicleid;
	private String obuid;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date obuenabletime;
	private String cardid;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date cardenabletime;
	private String drivinglicenseisver;
	private String  verdatatype;
	private String verdataid;
	private String trafficdatadisno;
	private String vehicletype;
	private Integer weight;
	private Integer axlecount;
	private String vehtypeisagreement;
	private String vehnoisagreement;
	private String pointtype;
	private Long messageid;
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date inserttime;
	
	@SuppressWarnings("unused")
	private String vehicleidnum;
	private String vehicleidcolor;
	
	@Transient
    public String getVehicleidnum() {
        if (StringUtils.isNotEmpty(getVehicleid()) && getVehicleid().length() >= 9) {
            return getVehicleid().substring(0, 7);
        }
        return "";
    }

}
