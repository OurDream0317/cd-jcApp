package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.JcWorkflowDefinationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @ClassName JcWorkflowDefinationMapper
 * @Descriotion TODO 稽查工作流定义Mapper
 * @Author fujiankang
 * @Date 2019/11/12 16:13
 * @Version 1.0
 */
public interface JcWorkflowDefinationMapper {
    /**
     * 获取 稽查工作流定义 数据根据参数
     *
     * @param jcWorkflowDefinationEntity
     * @return
     */
    List<JcWorkflowDefinationEntity> getDataByParam(JcWorkflowDefinationEntity jcWorkflowDefinationEntity);

    /**
     * 新增 稽查工作流定义
     *
     * @param jcWorkflowDefinationEntity
     * @return
     */
    int insertData(JcWorkflowDefinationEntity jcWorkflowDefinationEntity);

    /**
     * 获取 稽查工作流 数据根据DefinationInitDept
     *
     * @param definationInitDept
     * @param definationMenu
     * @return
     */
    int getCountByDefinationInitDept(@Param("definationInitDept") String definationInitDept, @Param("definationMenu") Integer definationMenu);

    /**
     * 修改 稽查工作流 数据 根据definationId
     *
     * @param jcWorkflowDefinationEntity
     * @return
     */
    int updateDataByDefinationId(JcWorkflowDefinationEntity jcWorkflowDefinationEntity);

    /**
     * 批量删除 稽查工作流 数据 根据流程ID
     *
     * @param definationIdList
     * @return
     */
    int deleteDataByDefinationId(List<Integer> definationIdList);

    /**
     * 获取 稽查工作流 数据根据流程ID
     *
     * @param definationId
     * @return
     */
    JcWorkflowDefinationEntity getDataByDefinationId(@Param("definationId") Integer definationId);
}
