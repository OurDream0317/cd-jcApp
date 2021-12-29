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
import java.util.Date;
import java.util.List;

/**
 * @author ma_zy
 * @Date: 2019/9/24 16:55
 * @Description: 用户自定义菜单
 */
@Data
@Table(name = "SYS_CUSTOM_MENU")
public class SysCustomMenuEntity implements Serializable {

    private static final long serialVersionUID = 5057861853644153343L;

    @Id
    @KeySql(sql = "select SEQ_SYS_CUSTMENU.nextval from dual", order = ORDER.BEFORE)
    private Long id ;

    @Column(name = "user_id")
    private Long userId;

    @Size(max = 20)
    private String customMenuName ;
    private Date createTime ;
    private Date updateTime ;
    private List<SysCustomMenuDetailsEntity> detailList;

}
