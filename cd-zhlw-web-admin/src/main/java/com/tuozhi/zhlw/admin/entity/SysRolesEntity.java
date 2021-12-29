package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/24 13:22
 **/
@Entity
@Table(name="SYS_ROLES")
@Data
public class SysRolesEntity implements Serializable {

    private static final long serialVersionUID = 6310251272745758387L;

    @Id
    @KeySql(sql = "select SEQ_SYS_ROLES.nextval from dual", order = ORDER.BEFORE)
    private Long id;

    @Size(max=20,message="100010")
    private String roleName;

    @Column(name = "ROLE_PARENT_ID")
    private Long parentRoleId;

    private Long dataPrivilegeId;

}
