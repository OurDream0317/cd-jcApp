package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author linqi
 * @create 2019/09/02 14:01
 **/
@Entity
@Data
@Table(name="SYS_USERS")
public class SysUserEntity implements Serializable {
    private static final long serialVersionUID = -6104890074268139996L;

    @Id
    @KeySql(sql = "select SEQ_SYS_USERS.nextval from dual", order = ORDER.BEFORE)
    private Long id;

    @Size(min=4,max=20,message="110006")
    @Pattern(regexp = "^[a-zA-Z0-9]+$",message="110007")
    private String loginName;

    @Size(min=1,max=20,message="110006")
    private String userName;

    @Size(min=4,max=100,message="110006")
    //@Pattern(regexp = "^[a-zA-Z0-9@]+$",message="110007")
    private String password;
    private String deptId;
    //private String deptWork;
    @Transient
    private String token;
    private Integer validStatus;

    @Transient
    private String deptName;

    private Date lastPasswordModifyTime;
    private String passwordHistory;

}
