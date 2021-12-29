package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Data
public class LoginUserForRedis implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer deptId;
    private String deptName;
    private Integer deptParentId;
    private String loginName;
    private String deptWork;
    private Integer privilegeId;
    private List<Map> roleDataFunctions;
    private String roleIds;
    private Integer userId;
    private String userName;
    private Integer validStatus;
}
