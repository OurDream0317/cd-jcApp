package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@Table(name = "JC_OPERATION_ADDLOG")
public class JCOperationAddLog {
    /* 序号 */
    private Long id;
    /* 车牌号 */
    @NotNull(message = "车牌号不能为空")
    private String vehiclelicense ;
    /* 车牌颜色 */
    @NotNull(message = "车牌颜色不能为空")
    private Integer vehiclelicenseColor ;
    /* 车型（按照新表结构，TYPE为车型，CLASS为车种） */
    private Integer vehicleType ;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime ;
    /* 创建USERID */
    private String createDept ;
    //创建部门ID
    private Long deptId;
    /* 创建用户名姓名 */
    private String createname ;
    /* 内容描述 */
    private String detil ;
    public String flag;// add 添加   update 修改
    public String vehicleBrand;//车辆品牌
    public Integer alexNum;//轴数
    public Integer vehicleClass;//车种
    public Integer fileNum;//附件数
    private String obuid;//obu编号
    private String issuerBank;//发行方
    private String issuer;//发行省份
    private String issuerBankName;//发行方名称
    private String issuername;//发行省份名称
    private String eludeMoneyType;
    private String eludeMoneyTypeItem;
    private String eludemoneytypename;
    private String eludemoneytypeitemname;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date eludeTime;
    private String cardid;
}
