package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.FlowPathInform;
import com.tuozhi.zhlw.admin.jc.entity.JcWorkFlowNodeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public interface FlowPathMapper {
    void saveFlowPathInform(Map map);

    void saveFlowPathInformEntiy(FlowPathInform flowPathInform);

    List finaAllFlowPath(@Param("requestId") Long requestId);

    Integer findFlowPathByRequestId(@Param("requestId") Long requestId, @Param("workFlowNodeId") Integer workFlowNodeId);

    void editFlowPathInform(FlowPathInform flowPathInform);

    List findHistoryFlowPath(@Param("requestId") Long requestId, @Param("flowPathId") Integer flowPathId);

    void delAllByRequestId(@Param("requestIds") Long[] requestIds);

    List selectReadTime(@Param("requestId") Long requestId, @Param("deptId") Integer deptId);



    /**
     * 获取当前环节 根据FlowPathId和RequestId
     * @param flowPathId
     * @param requestId
     * @return
     */
    FlowPathInform getDataByFlowPathIdAndRequestId(@Param("flowPathId") Integer flowPathId, @Param("requestId") Long requestId);

    /**
     * 获取 流程节点表的数据 根据使用它的环节表中的RequestId和FlowPathId
     *
     * @param flowPathId
     * @param requestId
     * @param status
     * @return
     */
    JcWorkFlowNodeEntity getDataByRequestIdAndFlowPathId(@Param("flowPathId") Integer flowPathId, @Param("requestId") Long requestId, @Param("status") Boolean status);

    /**
     * 获取上个环节的数据 根据当前环节的编号
     *
     * @param workflowDefinationId
     * @param workflowNodeId
     * @param requestId
     * @return
     */
    FlowPathInform getPrevDataByPrevWorkFlow(@Param("workflowDefinationId") Integer workflowDefinationId, @Param("workflowNodeId") Integer workflowNodeId, @Param("requestId") Long requestId);
}
