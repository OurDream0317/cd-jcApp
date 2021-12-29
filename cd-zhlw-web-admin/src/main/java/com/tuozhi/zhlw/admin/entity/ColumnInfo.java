package com.tuozhi.zhlw.admin.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author linqi
 * @create 2019/09/19 6:29
 **/

@Entity
@Table(name="SYS_SQL_COLUMNS")
@Data
public class ColumnInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name="FUNCTIONID",length=20)
    private Long functionId;

    @Column(name="COLUMN_TEXT",length=20)
    private String columnText;

    @Column(name="COLUMN_DATAINDEX",length=10)
    private String columnDataIndex;

    @Column(name="COLUMN_WIDTH_TYPE",length=20)
    private Integer widthType;

    @Column(name="COLUMN_WIDTH_VALUE",length=20)
    private Integer widthValue;

    @Column(name="ORDERINDEX",length=20)
    private Integer orderIndex;

    @Column(name="COLUMN_ISHIDDEN",length=5)
    private Integer columnIsHidden;

    @Column(name="ISFROZEN",length=5)
    private Integer isFrozen;

    @Column(name="IS_HEADER_GROUP",length=5)
    private Integer IsHeaderGroup;

    @Column(name="HEADER_GROUP_TEXT",length=50)
    private String headerGroupText;

    @Column(name="NOTE")
    private String note;
}
