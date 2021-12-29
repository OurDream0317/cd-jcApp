package com.tuozhi.zhlw.admin.entity;

import com.tuozhi.zhlw.common.valid.AddValid;
import com.tuozhi.zhlw.common.valid.UpdateValid;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author linqi
 * @create 2019/09/19 6:32
 **/

@Entity
@Table(name="BASE_FUNC_SQL_PARAM")
@Data
public class SysSqlManager {

    @Id
    @Column(name="SID")
    private String sid;

    /*@NotNull(message="400006",groups={AddValid.class,DeleteValid.class,UpdateValid.class})*/
    @Column(name="FUNCTIONID")
    private Long functionId;

    @Column(name="PARAMID")
    private Integer paramId;

    @Column(name="PARAMNAME")
    @Length(message="400005",min=0,max=250,groups={AddValid.class, UpdateValid.class})
    @NotBlank(message="400005",groups={AddValid.class,UpdateValid.class})
    private String paramName;

    @Column(name="PARAMEXAMPLE")
    private String paramExample;


    @Column(name="PARAMDATATYPE")
    private String paramType;

    @Column(name="PARAMID_FORLINKCHILD")
    private String paramForLinkChild;

    @Column(name="PARAMSCRIPT")
    private String paramScript;

    @Column(name="IGNOREWHENNULL")
    private Integer ignoreWhenNull;

    @Column(name="PARAMSCRIPTSOURCE")
    private String paramScriptSource;

    @Column(name="REQUIRED")
    private Integer required;
}
