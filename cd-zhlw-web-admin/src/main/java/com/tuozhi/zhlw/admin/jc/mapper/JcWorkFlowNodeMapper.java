package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.JcWorkFlowNodeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @ClassName JcWorkFlowNodeMapper
 * @Descriotion TODO
 * @Author fujiankang
 * @Date 2019/11/10 14:39
 * @Version 1.0
 */
@Repository
public interface JcWorkFlowNodeMapper {
    /**
     * 获取 流程节点表的数据 根据流程id
     *
     * @param definationId
     * @return
     */
    List<JcWorkFlowNodeEntity> getDataByDefinationId(@Param("definationId") Integer definationId);

    /**
     * 获取 流程节点表的数据 根据 流程的初始环节部门编号
     *
     * @param definationMenu
     * @param definationInitDept
     * @return
     */
    JcWorkFlowNodeEntity getDataByDefinationInitDept(@Param("definationMenu") Integer definationMenu, @Param("definationInitDept") String definationInitDept);

    /**
     * 新增 流程节点 数据
     *
     * @param jcWorkFlowNode
     * @return
     */
    int insertData(JcWorkFlowNodeEntity jcWorkFlowNode);

    /**
     * 更新 流程节点 数据
     *
     * @param jcWorkFlowNode
     * @return
     */
    int updateData(JcWorkFlowNodeEntity jcWorkFlowNode);

    /**
     * 删除 流程结点 数据更具流程ID
     *
     * @param definationIdList
     * @return
     */
    int deleteDataByDefinationId(List<Integer> definationIdList);
}
