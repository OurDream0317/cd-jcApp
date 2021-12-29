package com.tuozhi.zhlw.admin.jc.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName JcWorkflowDefinationEntity
 * @Descriotion TODO 稽查工作流定义表
 * @Author fujiankang
 * @Date 2019/11/12 16:24
 * @Version 1.0
 */
@Entity
@Table(name = "JC_WORKFLOW_DEFINATION")
@Data
public class JcWorkflowDefinationEntity implements Serializable {
    @Id
    private Integer definationId;//流程id
    private String definationName;//流程名称
    private Integer definationStatus;//流程状态（1：存草稿，2：停止运行，3：未运行，4：运行中
    private String definationComment;//流程描述
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间
    private Integer definationMenu;//流程所属（1：黑名单,2:稽查经费，3：省协查请求，4：灰名单）
    private String definationInitDept;//流程初始环节部门（逗号分隔）

    /*
    追加字段
     */
    private String workFlowDeptRole;
}