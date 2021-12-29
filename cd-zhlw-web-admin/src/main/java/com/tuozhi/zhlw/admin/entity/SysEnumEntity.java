package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * @author linqi
 * @create 2019/09/07 15:57
 **/
@Data
@Table(name = "SYS_ENUM")
public class SysEnumEntity {

    @Id
    @KeySql(sql = "select SEQ_SYS_ENUM.nextval from dual", order = ORDER.BEFORE)
    private Long id;
    private String enumCode;

    @Size(max = 20)
    private String enumName;

    @Size(max = 20)
    private String enumComment;

    @Transient
    private String details;

    private List<SysEnumDetailsEntity> enumDetailsEntityList;


}
