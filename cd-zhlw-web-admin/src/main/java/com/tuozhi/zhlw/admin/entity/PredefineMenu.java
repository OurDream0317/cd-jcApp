package com.tuozhi.zhlw.admin.entity;


import com.tuozhi.zhlw.common.valid.AddValid;
import com.tuozhi.zhlw.common.valid.DeleteValid;
import com.tuozhi.zhlw.common.valid.UpdateValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name="PREDEFINE_MENU")
@Data
public class PredefineMenu implements Serializable {
    private static final long serialVersionUID = -6104890074268139996L;

    @Column(name="FUNCTIONNAME")
    @Length(message="400005",min=0,max=250,groups={AddValid.class, UpdateValid.class})
    @NotBlank(message="400005",groups={AddValid.class,UpdateValid.class})
    private String functionName;

    @Column(name="PARENTID")
   // @NotBlank(message="400006",groups={AddValid.class,UpdateValid.class})
    private String parentId;

    @Column(name="ICONCLS")
   // @NotNull(message="400006",groups={AddValid.class,UpdateValid.class})
    private String iconCls;

    public String getIconCls() {
        return iconCls;
    }
    public void setIconCls(String iconCls) {
        this.iconCls = iconCls;
    }
    @Column(name="ISLEAFNODE")
    @NotNull(message="400006",groups={AddValid.class,UpdateValid.class})
    private Integer isLeafNode;

    @Column(name="ISVALID")
    private String isvalId="1";

    @Id
    @Column(name="FUNCTIONID")
    @NotNull(message="400006",groups={AddValid.class, DeleteValid.class,UpdateValid.class})
    private Long functionId;

    @Column(name="ORDERINDEX")
    @Min(value=0, message="400007")
    private String orderIndex;

    @Column(name="DESCRIPTION")
    private String description;

    //是否是预定义菜单
    @Column(name="ISSQLFUNCTION")
    private String isSqlFunction;

    //预定义SQL语句
    @Column(name="SQLSCRIPT")
    private String sqlScript;

    //预定义SQL语句
    @Column(name="AUTOLOADDATA")
    private String autoLoadData;

    @Column(name="DATASOURCE")
    private String dataSource;


    @Column(name="PRIMARYKEY")
    private String primaryKey;

    @Column(name="ISPREEXE")
    private String ispreexe;

}
