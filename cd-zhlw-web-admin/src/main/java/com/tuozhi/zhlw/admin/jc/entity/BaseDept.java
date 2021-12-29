package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class BaseDept implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String deptName;
    private Integer parentId;
    private String deptWork;
    private Integer workFlowdeptRole;
}
