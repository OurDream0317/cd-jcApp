package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

@Data
public class JcRoadBlackListTempEntity {
    private String vehiclelicense;
    private String vehiclelicensecolor;
    private String stopstatus;
    private String stopstatustype;
    private String cpuid;
    private String obuid;
    private String road;
    private String listversion;
    private String createtime;
    private String evasiontype;
    private String evasionkind;
    private String evadetime;
    private String evadesite;
    private String operateflag;
    private String lostcard;
    private String phonenumber;
    private String type;
    private Double evadetoll;
    private String htmlstr;
}
