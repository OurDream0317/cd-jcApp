package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCOperationlogFiles implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer attachmentId;
    private String attachmentPath;
    private Integer type;
    private String typeName;
    private String upFileUserName;//上传人
    private String upFileDept;//部门
    private String vehicle;
    private Integer vehicleColor;
    private String vehicleColorName;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date startTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date endTime;
    private String startheadoftruckpicpath;
    private String startbodyoftruckpic1path;
    private String startbodyoftruckpic2path;
    private String starttailoftruckpicpath;
    private String waybillpicpath;
    private String endheadoftruckpicpath;
    private String endbodyoftruckpic1path;
    private String endbodyoftruckpic2path;
    private String endtailoftruckpicpath;
    private String truckpicpath;
    private String drivinglicensepicpath;
    private String transportationtrippicpath;
    private String businesslicensepicpath;

}
