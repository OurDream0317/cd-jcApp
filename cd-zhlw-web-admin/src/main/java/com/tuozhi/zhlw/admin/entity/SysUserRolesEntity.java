package com.tuozhi.zhlw.admin.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/26 16:15
 **/
@Table(name="SYS_USER_ROLES")
@Data
public class SysUserRolesEntity implements Serializable {
    private static final long serialVersionUID = 1488475906346438119L;
    private String userID;
    private String roleId;
}
