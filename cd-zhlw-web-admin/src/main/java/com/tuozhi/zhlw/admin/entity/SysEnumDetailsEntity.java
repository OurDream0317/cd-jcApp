package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/07 16:46
 **/

@Table(name = "SYS_ENUM_DETAILS")
@Data
public class SysEnumDetailsEntity implements Serializable {
    @Id
    @KeySql(sql = "select SEQ_SYS_ENUM_D.nextval from dual", order = ORDER.BEFORE)
    private Long id;
    private Long enumId;
    private String enumValue;
    private String enumName;
    private String enumType;
    private int enumLogout;//注销标记

    @Transient
    private Long enumDetailsId;


}

