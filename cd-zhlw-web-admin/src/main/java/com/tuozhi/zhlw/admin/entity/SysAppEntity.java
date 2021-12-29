package com.tuozhi.zhlw.admin.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author linqi
 * @create 2019/09/03 21:09
 **/
@Data
@Entity
@Table(name = "SYS_APP")
public class SysAppEntity implements Serializable {

    @Id
    @KeySql(sql = "select SEQ_SYS_APP.nextval from dual", order = ORDER.BEFORE)
    private Long id;

    @Size(max = 20)
    private String name;

//    @Size(max = 3) TODO
    private Integer sort;

    @Column(name = "CREATE_TIME")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createTime;

    private Integer validStatus;

    @Size(max = 9)
    private String code;
    private String iconCls ;

}
