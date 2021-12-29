package com.tuozhi.zhlw.admin.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import tk.mybatis.mapper.annotation.KeySql;
import tk.mybatis.mapper.code.ORDER;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 系统消息
 * @author linqi
 * @create 2019/10/22 17:27
 **/

@Data
@Table(name = "SYS_NEWS")
public class SysNewsEntity {

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
   public enum NewsLevel {
        NORMAL(1,"普通"),
        URGENCY(2,"紧急"),
        HIGH(3,"超紧急");
        private int code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum NewsType {
        SYSTEM(1,"系统消息"),
        INSIDE(2,"内部");
        private int code;
        private String msg;
    }

    @Getter
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    public enum NewsStatus {
        NO_READ(0,"未读"),
        READ(1,"已读"),
        PROCESSING(2,"处理中"),
        PROCESSED(3,"完成");
        private int code;
        private String msg;
    }

    @Id
    @KeySql(sql = "select SEQ_SYS_NEWS.nextval from dual", order = ORDER.BEFORE)
    private String id;
    private String title;
    private String contents;
    private Integer status;//0.未读 1.已读
    private Date readTime;//读取时间
    private Integer newsType;//1.系统消息 2.内部消息
    private Integer newsLevel;//消息级别 1.普通 2.紧急 3.超紧急
    private Date createDate;//创建时间
    private Long createBy;//创建人

}
