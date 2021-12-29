package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author ma_zy
 * @Date: 2019/9/11 18:31
 * @Description:
 */
public interface DeptMapper extends MyMapper<SysDeptEntity> {

    @Select("  SELECT d.*,w.DEPT_NAME as PARENT_DEPT_NAME,p.PRIVILEGE_NAME,s.WORKFLOWDEPTROLE,s.DEPT_LONG_ID deptLongId FROM sys_dept_all d " +
            "  LEFT JOIN sys_dept_all w on d.PARENT_ID = w.id" +
            "  LEFT JOIN SYS_PRIVILEGE p on p.ID =d.PRIVILEGE_ID LEFT JOIN SYS_DEPT s ON s.ID = d.ID")
    List<SysDeptEntity> findAllDept();

    /**
     * 获取 部门数据 根据deptWork
     *
     * @param deptWork
     * @return
     */
    @Select("SELECT " +
            "ID, DEPT_NAME, PARENT_ID, TELEPHONE, DEPT_WORK, COMPANY_ID, DEPT_CODE, PRIVILEGE_ID, WORKFLOWDEPTROLE, DEPT_LONG_ID " +
            "FROM CD_SYSTEM.SYS_DEPT WHERE DEPT_WORK = #{deptWork}")
    SysDeptEntity getDataByDeptWork(String deptWork);
    
    /**
     * 获取 本部门子部门数据 根据ID
     *
     * @param deptWork
     * @return
     */
    @Select("SELECT\r\n" + 
    		"	d.*,\r\n" + 
    		"	w.DEPT_NAME AS PARENT_DEPT_NAME,\r\n" + 
    		"	p.PRIVILEGE_NAME,\r\n" + 
    		"	s.WORKFLOWDEPTROLE,\r\n" + 
    		"	s.DEPT_LONG_ID deptLongId \r\n" + 
    		"FROM\r\n" + 
    		"	(SELECT * \r\n" + 
    		"FROM sys_dept_all \r\n" + 
    		"START WITH ID = #{id} \r\n" + 
    		"CONNECT BY PRIOR ID=PARENT_ID) d \r\n" + 
    		"	LEFT JOIN sys_dept_all w ON d.PARENT_ID = w.id \r\n" + 
    		"	LEFT JOIN SYS_PRIVILEGE p ON p.ID = d.PRIVILEGE_ID LEFT JOIN SYS_DEPT s ON s.ID = d.ID")
    List<SysDeptEntity> getAllChildIdsById(Long id);

}
