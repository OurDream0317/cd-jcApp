package com.tuozhi.zhlw.admin.jc.entity.grayListEntity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCRoadBlackListFeedBack implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer sid;
    private String stopStatus;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date alertTime;
    private String operator;
    private String operatorName;
    private String stationId;
    private String lane;
    private Integer status;
    private String listVersion;
    private String listId;
    private String vehicleLicense;
    private Integer vehicleLicenseColor;
    private String cpuId;
    private String obuId;
    private Integer evadeToll;
    private String dealId;
    private String toll;
    private String recordId;

}
