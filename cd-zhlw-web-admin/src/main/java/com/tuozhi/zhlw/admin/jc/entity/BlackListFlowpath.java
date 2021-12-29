package com.tuozhi.zhlw.admin.jc.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@Entity
@Table(name = "CD_JC.JC_BLACKLIST_FLOWPATH")
@Data
public class BlackListFlowpath implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private Integer flowpathId;//环节编号
    private Long requestId;//黑名单申请编号
    private String flowpathName;//环节名称
    private Integer loginUserId;//TODO 创建人 该字段已经删除
    private String handleSuggestion;//处理意见
    private Date createTime;//环节创建时间
    private Date readTime;//TODO 第一次查看时间 该字段已经删除
    private Date operateTime;//送出时间
    private String operateUserName;//处理人
    private Long operateDeptId;//处理部门
    private Integer sendDirection;//2向上发送，1向下发送,
    private Integer flowpathStatus;//1是退回操作，2是被退回，3是通过办结，4是拒绝办结
    private Integer workflowDefinationId;//工作流编号
    private Integer workflowNodeId;//工作流节点编号

    /*
    追加字段
     */
    private String deptName;//处理部门名称
    private Integer deptId;//TODO 这个字段已经删除 使用新的operateDeptId字段
}
