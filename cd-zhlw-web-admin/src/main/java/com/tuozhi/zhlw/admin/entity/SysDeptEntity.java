package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author ma-zy
 * @Date: 2019/9/11 18:03
 * @Description:
 */
@Entity
@Data
@Table(name="SYS_DEPT")
public class SysDeptEntity implements Serializable {

    /* 部门id */
    @Id
    @KeySql(sql = "select SEQ_SYS_DEPT.nextval from dual", order = ORDER.BEFORE)
    private Long id ;

    /* 部门名称 */
    @Size(max=20,message="100012")
    private String deptName ;

    /* 上级部门id */
    private Long parentId ;


    @Size(max=20,message="100025")
    private String telephone;//联系电话
    private String deptCode;


    @Size(max=20,message="100014")
    private String deptWork;//部门职责

    private Integer workflowdeptrole;

    private String privilegeId;//权限id

    @Transient
    private Boolean enableEdit; //
    @Transient
    private String parentDeptName; //

    @Transient
    private String privilegeName; //

    @Transient
    private Long deptId;

    private String deptLongId;//部门长编号

}
