package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseVerticalsection implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private Double length;
    private String startnodename;
    private String endnodename;
    private String sectionid;
}
