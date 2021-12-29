package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.BlackListFlowpath;
import com.tuozhi.zhlw.admin.jc.entity.JcWorkFlowNodeEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BlackListFlowpathMapper extends MyMapper<BlackListFlowpath> {

    int saveBlacklistFlowpath(BlackListFlowpath blackListFlowpath);

    /**
     * 获取 黑名单申请流程 的数据根据黑名单申请编号
     *
     * @param requestId
     * @return
     */
    List<BlackListFlowpath> getDataByRequestId(@Param("requestId") Long requestId);

    /**
     * 获取 黑名单申请流程的处理部门 根据黑名单申请编号和工作流节点编号
     *
     * @param requestId
     * @param workFlowNodeId
     * @return
     */
    Integer getFlowPathByRequestId(@Param("requestId") Integer requestId, @Param("workFlowNodeId") Integer workFlowNodeId);

    /**
     * 新增环节流程
     *
     * @param blackListFlowpath
     * @return
     */
    int insertData(BlackListFlowpath blackListFlowpath);

    /**
     * 更新 黑名单流程表
     *
     * @param blackListFlowpath
     * @return
     */
    int updateDataByFlowpathIdAndRequestId(BlackListFlowpath blackListFlowpath);

    /**
     * 获取上个环节的数据 根据当前环节的编号
     *
     * @param workflowDefinationId
     * @param workflowNodeId
     * @param requestId
     * @return
     */
    BlackListFlowpath getPrevDataByPrevWorkFlow(@Param("workflowDefinationId") Integer workflowDefinationId, @Param("workflowNodeId") Integer workflowNodeId, @Param("requestId") Long requestId);

    /**
     * 获取当前环节的数据 根据当前环节编号和黑名单申请Id
     *
     * @param flowPathId
     * @param requestId
     * @return
     */
    BlackListFlowpath getDataByFlowPathIdAndRequestId(@Param("flowPathId") Integer flowPathId, @Param("requestId") Long requestId);

    /**
     * 黑名单申请流程添加
     * @param blackListFlowpath
     * @return
     */
    int addBlackFlowPath(BlackListFlowpath blackListFlowpath);

    /**
     * 删除
     * @param flowpathId
     * @return
     */
    int delBlackFlowPath(Integer flowpathId);

    /**
     * 获取 流程节点表的数据 根据使用它的环节表中的RequestId和FlowPathId
     *
     * @param flowPathId
     * @param requestId
     * @param status
     * @return
     */
    JcWorkFlowNodeEntity getDataByRequestIdAndFlowPathId(@Param("flowPathId") Integer flowPathId, @Param("requestId") Long requestId, @Param("status") Boolean status);
}
