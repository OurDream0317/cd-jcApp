package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * @author ma_zy
 * @Date: 2019/12/2 10:48
 * @Description:
 */
@Data
@Table(name = "JC_OPERATION_LOG")
public class JcOperationLogEntity implements Serializable {
    /* 车牌号 */
    @NotNull(message = "车牌号不能为空")
    private String vehiclelicense ;
    /* 车牌颜色 */
    @NotNull(message = "车牌颜色不能为空")
    private Integer vehiclelicenseColor ;
    /* 车型（按照新表结构，TYPE为车型，CLASS为车种） */
    private Integer vehicleType ;
    /* 逃费类型 */
    private String misstollType ;
    /* 逃费种类 */
    private String misstollClass ;
    /* 创建时间 */
    @Column(name = "CREATETIME")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime ;
    /* 创建USERID */
    private Long createUserId ;
    /* 来源 -- 1.协查 2.黑名单 3.稽查经费 4.灰名单 5.特情 */
    @NotNull(message = "来源不能为空")
    private Integer transferSource ;

    public JcOperationLogEntity (String vehiclelicense,Integer vehiclelicenseColor,Integer vehicleType,String misstollType,String misstollClass,Integer transferSource){
        this.vehiclelicense=vehiclelicense;
        this.vehiclelicenseColor=vehiclelicenseColor;
        this.vehicleType=vehicleType;
        this.misstollType=misstollType;
        this.misstollClass=misstollClass;
        this.transferSource=transferSource;
    }
    public JcOperationLogEntity (){};


    /*
        补充
         */
     private Integer assistantNum;//协查数量
    private Integer auditFoundsNum;//稽核数量
    private Integer greyNum;//灰名单数量
    private Integer blackNum;//黑名单新增数量
    private Integer revokeBlackNum;//黑名单撤销数量
    private Integer specialNum;//特情数量
    private Integer operationLogNum;//稽查日志数量（手动添加）
}
