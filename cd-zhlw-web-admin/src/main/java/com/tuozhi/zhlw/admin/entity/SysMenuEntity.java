package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
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
 * @create 2019/09/03 16:58
 **/

@Data
@Entity
@Table(name="SYS_MENU")
public class SysMenuEntity implements Serializable {

    @Id
    @KeySql(sql = "select SEQ_SYS_MENU.nextval from dual", order = ORDER.BEFORE)
    private Long id;

    @Size(max = 20)
    private String functionName;

    @Column(name = "FUNCTION_CODE")
    private String functionCode;

    private String parentCode;

    @Column(name = "LEAF_NODE_STATUS")
    private Integer leafNodeStatus;

    @Column(name = "VALID_STATUS")
    private Integer validStatus;

    @Column(name = "ORDER_INDEX")
    private Integer orderIndex;

    private String description;
   // private String sqlFunctionStatus;
    private String iconcls;
    private String appId;
    private String url;
    private Integer menuType;
    // TODO
//    @KeySql(sql = "select SEQ_SYS_MENU.curval from dual", order = ORDER.AFTER)
    private Long preDefineMenuId;//预定义组件表外键

}
