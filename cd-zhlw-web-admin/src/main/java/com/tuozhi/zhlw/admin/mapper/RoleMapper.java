package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysRolesEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 16:42
 **/

public interface RoleMapper extends MyMapper<SysRolesEntity> {

    public List queryPrivilegeListById(Long pId);

    @Insert("INSERT INTO SYS_ROLES_MENU(ROLE_ID, FUNCTION_ID)" +
            "VALUES(#{roleId}, #{functionId})")
    void insertRoleMenu(@Param("roleId") Long roleId, @Param("functionId") Long functionId);

    @Select("select r.id as ROLEID" +
            ",r.ROLE_NAME as ROLENAME" +
            ",r.role_parent_id as PARENTID " +
            ",rp.ROLE_NAME as PARENTNAME "+
            ",r.data_privilege_id as PRIVILEGEID ,p.PRIVILEGE_NAME as PRIVILEGE_NAME" +
            " from sys_users u" +
            " INNER JOIN sys_user_roles ur" +
            " on u.ID=ur.USER_ID" +
            " INNER JOIN sys_roles r" +
            " on ur.ROLE_ID=r.id" +
			" left JOIN sys_roles rp "+
			" on r.role_parent_id=rp.id "+
            " Left JOIN SYS_PRIVILEGE p on p.id = r.DATA_PRIVILEGE_ID" +
            " WHERE u.id= #{userId}")
    List<Map<String, Object>> findAllRoles(Long userId);


    @Select(" select r.id as ROLE_ID,ROLE_NAME,ROLE_PARENT_ID,DATA_PRIVILEGE_ID ,p.PRIVILEGE_NAME as PRIVILEGENAME" +
            " from SYS_ROLES  r" +
            " Left JOin SYS_PRIVILEGE p on p.id = r.DATA_PRIVILEGE_ID ")
    List<Map<String, Object>> findAllBaseRole();

    @Select(" select a.ROLE_ID,m.FUNCTION_CODE as FUNCTIONID" +
            " from sys_roles_menu a inner join sys_menu m on a.FUNCTION_ID = m.id " +
            " WHERE a.ROLE_ID = #{roleId}")
    List<Map<String, Object>> getRoleMenuByRoleId(Long roleId);


    @Select(" select * from (select  a.USER_ID,a.ROLE_ID,b.LOGIN_NAME,b.USER_NAME,b.DEPT_ID AS DEPTID," +
            " D.DEPT_NAME as DEPTNAME from sys_user_roles a " +
            " INNER JOIN sys_users b   on a.USER_ID = b.id " +
            " LEFT JOIN sys_dept_all d on b.DEPT_ID = d.id ) q where q.role_id = #{roleId}")
    List<Map<String, Object>> findRoleUser(@Param("roleId") Long roleId);


    @Select("SELECT U.ID AS USER_ID,U.LOGIN_NAME,U.USER_NAME,U.DEPT_ID,D.DEPT_NAME AS DEPTNAME " +
            "FROM SYS_USERS U INNER JOIN SYS_DEPT_ALL D ON U.DEPT_ID = D.ID " +
            "WHERE U.ID NOT IN (" +
            "       SELECT  SUR.USER_ID FROM SYS_USER_ROLES SUR " +
            "   WHERE SUR.ROLE_ID = 1 )  "+
            "    AND D.PRIVILEGE_ID >=(SELECT DATA_PRIVILEGE_ID FROM SYS_ROLES  WHERE ID = #{roleId})" +
            " and USER_NAME like #{searchValue} or LOGIN_NAME like #{searchValue} " )
    List<Map<String, Object>> findRoleUserByRoleIdByCondition(@Param("roleId") Long roleId,@Param("searchValue") String searchValue);
    
    @Select("SELECT U.ID AS USER_ID,U.LOGIN_NAME,U.USER_NAME,U.DEPT_ID,D.DEPT_NAME AS DEPTNAME " +
            "FROM SYS_USERS U INNER JOIN SYS_DEPT_ALL D ON U.DEPT_ID = D.ID " +
            "WHERE U.ID NOT IN (" +
            "       SELECT  SUR.USER_ID FROM SYS_USER_ROLES SUR " +
            "   WHERE SUR.ROLE_ID = 1 )  " +
            "    AND D.PRIVILEGE_ID >=(SELECT DATA_PRIVILEGE_ID FROM SYS_ROLES  WHERE ID = #{roleId})")
    List<Map<String, Object>> findRoleUserByRoleId(@Param("roleId")Long roleId);

    @Select("INSERT INTO SYS_USER_ROLES VALUES(#{uId},#{roleId})")
    void insertUserRoles(@Param("uId") Long uId, @Param("roleId") Long roleId);

    @Select("DELETE FROM SYS_USER_ROLES WHERE USER_ID = #{userId} AND ROLE_ID = ${roleId}")
    void deleteRoleUser(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("DELETE FROM SYS_ROLES_MENU WHERE ROLE_ID = #{roleId}")
    void deleteRoleMenuByRoleId(@Param("roleId")Long roleId);

    @Select("DELETE FROM SYS_ROLES_MENU WHERE FUNCTION_ID = #{functionId}")
    void deleteRoleMenuByFunctionId(@Param("functionId") Long functionId);
    
    @Select("DELETE FROM SYS_ROLES_MENUS_HISTORY WHERE ROLE_ID = #{roleId}")
    void deleteRoleMenuHisByRoleId(@Param("roleId") Long roleId);
    
    @Insert("INSERT INTO SYS_ROLES_MENUS_HISTORY(ROLE_ID, FUNCTION_ID)" +
            "VALUES(#{roleId}, #{functionId})")
    void insertRoleMenuHis(@Param("roleId") Long roleId, @Param("functionId") Long functionId);
    
    @Select(" select a.ROLE_ID,m.ID as FUNCTIONID \r\n" + 
    		"             from sys_roles_menu a inner join sys_menu m on a.FUNCTION_ID = m.id  \r\n" + 
    		"             WHERE a.ROLE_ID = #{roleId}")
    List<Map<String, Object>> getFunctionIdsByRoleId(Long roleId);
    
    @Select("SELECT count(1)  from dual where #{childRoleId} in (SELECT ID\r\n" + 
    		"FROM SYS_ROLES\r\n" + 
    		"START WITH ID = #{roleId}\r\n" + 
    		"CONNECT BY PRIOR ID=ROLE_PARENT_ID)")
    Integer hasChildCountByChildId(@Param("roleId") String roleId,@Param("childRoleId") String childRoleId);
    
   
    @Select("<script> "+
    		" SELECT U.ID AS USER_ID,U.LOGIN_NAME,U.USER_NAME,U.DEPT_ID,D.DEPT_NAME AS DEPTNAME " +
            " FROM SYS_USERS U INNER JOIN SYS_DEPT_ALL D ON U.DEPT_ID = D.ID " +
            " WHERE U.ID NOT IN (" +
            "       SELECT  SUR.USER_ID FROM SYS_USER_ROLES SUR " +
            "   WHERE SUR.ROLE_ID = 1 )  " +
            "    AND D.PRIVILEGE_ID >=(SELECT DATA_PRIVILEGE_ID FROM SYS_ROLES  WHERE ID = #{roleId}) "+
            " AND U.DEPT_ID IN "+
            " <foreach item='item' index='index' collection='deptIds' separator=',' open='(' close=')'>" +
            "    #{item}" +
            " </foreach>" +
            "</script>" )
    List<Map<String, Object>> findRoleUserByRoleIdAndDeptIds(@Param("roleId")Long roleId,@Param("deptIds")List<Long> deptIds);
    
    @Select("<script> "+
    		" SELECT U.ID AS USER_ID,U.LOGIN_NAME,U.USER_NAME,U.DEPT_ID,D.DEPT_NAME AS DEPTNAME " +
            " FROM SYS_USERS U INNER JOIN SYS_DEPT_ALL D ON U.DEPT_ID = D.ID " +
            " WHERE U.ID NOT IN (" +
            "       SELECT  SUR.USER_ID FROM SYS_USER_ROLES SUR " +
            "   WHERE SUR.ROLE_ID = 1 )  "+
            "    AND D.PRIVILEGE_ID >=(SELECT DATA_PRIVILEGE_ID FROM SYS_ROLES  WHERE ID = #{roleId})" +
            " and USER_NAME like #{searchValue} or LOGIN_NAME like #{searchValue} "+
            "   AND U.DEPT_ID IN  " +
            " <foreach item='item' index='index' collection='deptIds' separator=',' open='('  close=')'>" +
            "    #{item}" +
            " </foreach>" +
            "</script>" )
    List<Map<String, Object>> findRoleUserByRoleIdByConditionAndDeptIds(@Param("roleId") Long roleId,@Param("searchValue") String searchValue,@Param("deptIds")List<Long> deptIds);
    
    
}
