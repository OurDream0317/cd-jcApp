package com.tuozhi.zhlw.admin.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/09 15:06
 **/

@Table(name = "SYS_ROLES_MENU")
@Data
@Entity
public class SysRolesMenuEntity implements Serializable {

    private static final long serialVersionUID = -8376822669968126554L;

    @Id
    private SysRolesMenuPK sysRolesMenuPK;


}
