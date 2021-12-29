package com.tuozhi.zhlw.admin.jcApp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class JCAppLabelTypeEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private String vehicle;
    private Integer vehicleColor;
    private String labelType;
    private String createUser;
    private String createDept;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;
    private String loginName;
}
