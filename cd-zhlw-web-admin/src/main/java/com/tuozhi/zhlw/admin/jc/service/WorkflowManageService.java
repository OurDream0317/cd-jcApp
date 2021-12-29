package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.admin.jc.entity.JcWorkFlowNodeEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcWorkflowDefinationEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;

/**
 * @ClassName WorkflowManageService
 * @Descriotion TODO 流程步骤配置Service接口
 * @Author fujiankang
 * @Date 2019/11/12 16:11
 * @Version 1.0
 */
public interface WorkflowManageService {
    /**
     * 获取 稽查工作流定义列表 根据参数
     *
     * @param jcWorkflowDefinationEntity
     * @param queryParams
     * @return
     */
    ResultExtGrid getWorkflowDefinationListByParam(JcWorkflowDefinationEntity jcWorkflowDefinationEntity, QueryParams queryParams);

    /**
     * 保存 稽查工作流定义表
     *
     * @param jcWorkflowDefinationEntity
     * @return
     */
    ResultMsg insertWorkflow(JcWorkflowDefinationEntity jcWorkflowDefinationEntity);

    /**
     * 获取 流程节点表的数据 根据流程id
     *
     * @param definationId
     * @return
     */
    ResultMsg getWorkflowNodeByDefinationId(Integer definationId);

    /**
     * 更新结点名称
     *
     * @param jcWorkFlowNode
     * @return
     */
    ResultMsg updateWorkFlowNodeByNodeId(JcWorkFlowNodeEntity jcWorkFlowNode);

    /**
     * 修改 稽查工作流 数据 根据definationId
     *
     * @param jcWorkflowDefinationEntity
     * @return
     */
    ResultMsg updateWorkFlowDefinationByDefinationId(JcWorkflowDefinationEntity jcWorkflowDefinationEntity);

    /**
     * 删除 稽查工作流和对应的结点数据 根据流程id
     *
     * @param definationIdList
     * @return
     */
    ResultMsg deleteWorkFlowDefination(List<Integer> definationIdList);

    /**
     * 更新 稽查工作流程 中的环节初始部门ID
     *
     * @param definationIdList
     * @return
     */
    ResultMsg updateWorkFlowDefinationInitDept(List<Integer> definationIdList);
}
