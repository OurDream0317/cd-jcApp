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
 * @ClassName JcWorkFlowNodeEntity
 * @Descriotion TODO 流程步骤表
 * @Author fujiankang
 * @Date 2019/11/10 14:33
 * @Version 1.0
 */
@Entity
@Table(name = "JC_WORKFLOW_NODE")
@Data
public class JcWorkFlowNodeEntity implements Serializable {
    @Id
    private Integer nodeId;//结点id
    private Integer definationId;//流程id
    private String nodeName;//结点名称
    private Integer prevNodeId;//前结点序号，例如1。开始结点没。执行前，通过此来校验一下流程
    private Integer nextNodeId;//后结点序号，例如[同意]3,[不同意]4。尾结点或要结束的结点没有，若没有，直接调用end
    private Integer workFlowDeptRole;//当前结点处理权限的部门角色
    private Integer nodeType;//环节过程标识（1：起始环节，2：过程中环节，3：结束环节）
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间
    /*
    追加字段
     */
    private Long workFlowDeptRoleNext;//本流程的下个节点的权限部门角色
    private Long workFlowDeptRolePrev;//本流程的上个节点的权限部门角色
    private Integer definationStatus;//流程状态（1：存草稿，2：停止使用，3：未运行，4运行中
    private String definationName;//流程名称
}