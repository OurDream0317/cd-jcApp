package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.BaseDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public interface BaseDeptMapper {
    List findAllDept();

    List findDeptByDeptId(@Param("deptId") String deptId, @Param("workflowdeptroles") String[] workflowdeptroles);

    List findDeptByParentId(@Param("parentIds") String[] parentIds, @Param("workflowdeptroles") String[] workflowdeptroles);

    List findDeptByWorkflowdeptrole(@Param("workflowdeptroles") String[] workflowdeptroles);

    BaseDept findDeptByDeptId1(@Param("deptId") String deptId);

    /**
     * 获取 收费路段表 的数据
     *
     * @param idList
     * @return
     */
    List<Map<String, Object>> getAudroadByIdList(@Param("idList") List<String> idList);

    /**
     * 获取 客户合作机构信息表 的数据
     *
     * @param idList
     * @return
     */
    List<Map<String, Object>> getIssuerByIdList(@Param("idList") List<String> idList);

    /**
     * 获取 对应部门角色的所有部门 根据部门角色
     *
     * @param workFlowDeptRole
     * @param highSpeedGroup
     * @return
     */
    String getDataByWorkFlowDeptRole(@Param("workFlowDeptRole") Integer workFlowDeptRole, @Param("highSpeedGroup") Boolean highSpeedGroup);

    /**
     * 获取 部门数据 根据deptID
     *
     * @param deptId
     * @return
     */
    BaseDept getDataByDeptId(@Param("deptId") Long deptId);

    /**
     * 获取当前用户的所有下级路段的真实长编号
     *
     * @param deptId
     * @return
     */
    String getAllNextDeptDeptLongId(@Param("deptId") Long deptId);


    /**
     * 获取 稽查部的部门ID
     *
     * @return
     */
    @Select("SELECT ID FROM CD_SYSTEM.SYS_DEPT WHERE DEPT_WORK = '1000002'")
    Long getJCBDeptId();

    /**
     * 获取 发行方的部门ID
     *
     * @return
     */
    @Select("SELECT ID FROM CD_SYSTEM.SYS_DEPT WHERE DEPT_WORK = 'FX'")
    Long getIssuerDeptId();

    /**
     * 获取 部门ID 根据部门ID和部门角色ID
     *
     * @param deptId
     * @param workFlowDeptRole
     * @return
     */
    BaseDept getPrevDeptByDeptAndWorkFlowDeptRole(@Param("deptId") Long deptId, @Param("workFlowDeptRole") Long workFlowDeptRole);

    /**
     * 获取 部门ID 根据部门Work和部门角色ID
     *
     * @param deptWrok
     * @param workFlowDeptRole
     * @return
     */
    BaseDept getPrevDeptByDeptWrokAndWorkFlowDeptRole(@Param("deptWrok") String deptWrok, @Param("workFlowDeptRole") Long workFlowDeptRole);

    /**
     * 获取当前用户所属的公司部门ID
     * @param deptid
     * @return
     */
    Long getDeptId(@Param("deptid") Long deptid);
    /**
     * 判断当前用户的最高部门是不是稽查部
     * @param deptid
     * @return
     */
    Integer getDeptId1(@Param("deptid") Long deptid);
    List<String> getDeptIdByRoles(@Param("rangeRoleIds") String rangeRoleIds);
}
