package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/06 9:32
 **/

public interface MenuMapper extends MyMapper<SysMenuEntity> {

    @Select(value = "SELECT DISTINCT a.FUNCTION_CODE AS functionCode,a.FUNCTION_NAME as functionName ,a.PARENT_CODE as parentCode,a.DESCRIPTION, " +
            " a.ORDER_INDEX as orderIndex,a.LEAF_NODE_STATUS as leafNodeStatus, a.ICONCLS, a.APP_ID as appId ," +
            "app.name as appName,a.MENU_TYPE,a.URL,a.PRE_DEFINE_MENU_ID" +
            " from SYS_MENU a " +
            " INNER JOIN SYS_ROLES_MENU f on a.ID = f.FUNCTION_ID " +
            " INNER JOIN SYS_USER_ROLES r on r.ROLE_ID = f.ROLE_ID " +
            " LEFT JOIN SYS_APP app on app.id = a.app_id " +
            " where r.USER_ID = #{userId} and a.valid_status = 1 and a.LEAF_NODE_STATUS !=2 order by a.ORDER_INDEX,a.PARENT_CODE")
    List<SysMenu> findAllByUserId(Long userId);


    @Select(" SELECT" +
            " DISTINCT " +
            " a.id," +
            " a.FUNCTION_CODE ," +
            " a.FUNCTION_NAME ," +
            " a.PARENT_CODE," +
            " a.DESCRIPTION," +
            " a.ORDER_INDEX," +
            " a.LEAF_NODE_STATUS ," +
            " aa.FUNCTION_NAME AS PARENT_NAME," +
            " aa.PRE_DEFINE_MENU_ID AS PRE_DEFINE_MENU_ID," +
            " a.ICONCLS ,a.MENU_TYPE ,a.URL,a.APP_ID," +
            " p.FUNCTIONNAME AS SQL" +
            " FROM" +
            " SYS_MENU a " +
            " LEFT OUTER JOIN SYS_MENU aa " +
            " ON a.PARENT_CODE = aa.FUNCTION_CODE " +
            " LEFT join " +
            " PREDEFINE_MENU p on p.FUNCTIONID = a.PRE_DEFINE_MENU_ID " +
            " WHERE" +
            " a.valid_STATUS = 1 and a.DELETE_FLAG = 0" +
            " ORDER BY" +
            " a.PARENT_CODE," +
            " a.ORDER_INDEX")
    List<SysMenu> findAllMenu();
    
    @Select(" SELECT" +
            " DISTINCT " +
            " a.id," +
            " a.FUNCTION_CODE ," +
            " a.FUNCTION_NAME ," +
            " a.PARENT_CODE," +
            " a.DESCRIPTION," +
            " a.ORDER_INDEX," +
            " a.LEAF_NODE_STATUS ," +
            " aa.FUNCTION_NAME AS PARENT_NAME," +
            " aa.PRE_DEFINE_MENU_ID AS PRE_DEFINE_MENU_ID," +
            " a.ICONCLS ,a.MENU_TYPE ,a.URL,a.APP_ID," +
            " p.FUNCTIONNAME AS SQL" +
            " FROM" +
            " SYS_MENU a " +
            " LEFT OUTER JOIN SYS_MENU aa " +
            " ON a.PARENT_CODE = aa.FUNCTION_CODE " +
            " LEFT join " +
            " PREDEFINE_MENU p on p.FUNCTIONID = a.PRE_DEFINE_MENU_ID " +
            " WHERE" +
            " a.valid_STATUS = 1 and a.DELETE_FLAG = 0 and a.LEAF_NODE_STATUS !=2 " +
            " ORDER BY" +
            " a.PARENT_CODE," +
            " a.ORDER_INDEX")
    List<SysMenu> findAllMenuAppAll();
    
    @Select("SELECT DISTINCT\r\n" + 
    		"	a.id,\r\n" + 
    		"	a.FUNCTION_CODE,\r\n" + 
    		"	a.FUNCTION_NAME,\r\n" + 
    		"	a.PARENT_CODE,\r\n" + 
    		"	a.DESCRIPTION,\r\n" + 
    		"	a.ORDER_INDEX,\r\n" + 
    		"	a.LEAF_NODE_STATUS,\r\n" + 
    		"	aa.FUNCTION_NAME AS PARENT_NAME,\r\n" + 
    		"	aa.PRE_DEFINE_MENU_ID AS PRE_DEFINE_MENU_ID,\r\n" + 
    		"	a.ICONCLS,\r\n" + 
    		"	a.MENU_TYPE,\r\n" + 
    		"	a.URL,\r\n" + 
    		"	a.APP_ID,\r\n" + 
    		"	p.FUNCTIONNAME AS SQL \r\n" + 
    		"FROM\r\n" + 
    		"	(SELECT * from SYS_MENU where id in (select FUNCTION_ID from SYS_ROLES_MENU where ROLE_ID = #{roleId})) a\r\n" + 
    		"	LEFT OUTER JOIN SYS_MENU aa ON a.PARENT_CODE = aa.FUNCTION_CODE\r\n" + 
    		"	LEFT JOIN PREDEFINE_MENU p ON p.FUNCTIONID = a.PRE_DEFINE_MENU_ID \r\n" + 
    		"WHERE\r\n" + 
    		"	a.valid_STATUS = 1 \r\n" + 
    		"	AND a.DELETE_FLAG = 0 \r\n" + 
    		"ORDER BY\r\n" + 
    		"	a.PARENT_CODE,\r\n" + 
    		"	a.ORDER_INDEX ")
    List<SysMenu> findAllMenuByRoleId(String roleId);

    List<SysMenu> findAppAllMenus(@Param("appId") Long appId);



    List<SysMenu> getSysMenuTreeIsNotLeafNode(@Param("appId") Long appId);

    @Select("DELETE FROM SYS_ROLES_MENU WHERE FUNCTION_ID = " +
            " ( SELECT M.ID\n" +
            "              FROM SYS_MENU M\n" +
            "              WHERE FUNCTION_CODE = #{functionCode})")
    void deleteRolesMenuByFunctionCode(String functionCode);


    /*@Select(" SELECT DISTINCT a.FUNCTIONID ,a.FUNCTIONNAME ,a.PARENTID,a.DESCRIPTION, a.ORDERINDEX," +
            "             a.ISLEAFNODE ,a.ICONCLS , aa.FUNCTIONNAME as PARENTNAME ,a.ISSQLFUNCTION, a.SQLSCRIPT, " +
            "            a.AUTOLOADDATA ,a.DATASOURCE,a.PRIMARYKEY " +
            "            from PREDEFINE_MENU a " +
            "            LEFT JOIN PREDEFINE_MENU aa ON a.PARENTID=aa.FUNCTIONID " +
            "            where  a.isvalid = 1 and a.ISLEAFNODE !=2 order by a.PARENTID,a.ORDERINDEX")
    List<Map<String,Object>> getPredefineMenuTreeByUserId(Long userId);*/
    List<Map<String,Object>> getPredefineMenuTreeByUserId(Map map);

  /*  @Select(" SELECT DISTINCT FUNCTIONID,FUNCTIONNAME,ISVALID from PREDEFINE_MENU pm " +
            " inner join sys_menu m on pm.PARENTID = m.PRE_DEFINE_MENU_ID where  m.FUNCTION_CODE = #{functionId} and ISVALID = 1")*/
  @Select("     SELECT  DISTINCT FUNCTIONID,\n" +
          "          FUNCTIONNAME," +
          "          ISVALID  from  PREDEFINE_MENU p where p.PARENTID in  (        \n" +
          "      SELECT\n" +
          "          FUNCTIONID\n" +
          "          FROM\n" +
          "          PREDEFINE_MENU pm inner join sys_menu m on m.PRE_DEFINE_MENU_ID = pm.FUNCTIONID\n" +
          "          WHERE\n" +
          "          ISVALID = 1 and  m.FUNCTION_CODE = #{functionId})")
    List<Map<String, Object>> selectMenuIsvalId(long functionId);

    @Select("select * from sys_menu where function_code = #{functionCode}")
    SysMenuEntity getEntityByFunctionCode(Long functionCode);


    @Select("select * from sys_menu where pre_define_menu_id = #{prdDefineMenuId}")
    SysMenuEntity getEntityByPreDefineMenuId(Long prdDefineMenuId);

    @Select("select id from sys_menu where function_code = #{functionCode,jdbcType=VARCHAR}")
    Long getIdByFunctionCode(String functionCode) ;

    @Select("select count(1) from sys_menu where function_code = #{functionCode,jdbcType=VARCHAR}")
    int menuCountByCode(String functionCode) ;

    List<String> queryMenusCodeByParentCode(@Param("functionCode") String functionCode) ;
    
    @Select("SELECT DISTINCT\r\n" + 
    		"	a.id,\r\n" + 
    		"	a.FUNCTION_CODE,\r\n" + 
    		"	a.FUNCTION_NAME,\r\n" + 
    		"	a.PARENT_CODE,\r\n" + 
    		"	a.DESCRIPTION,\r\n" + 
    		"	a.ORDER_INDEX,\r\n" + 
    		"	a.LEAF_NODE_STATUS,\r\n" + 
    		"	aa.FUNCTION_NAME AS PARENT_NAME,\r\n" + 
    		"	aa.PRE_DEFINE_MENU_ID AS PRE_DEFINE_MENU_ID,\r\n" + 
    		"	a.ICONCLS,\r\n" + 
    		"	a.MENU_TYPE,\r\n" + 
    		"	a.URL,\r\n" + 
    		"	a.APP_ID,\r\n" + 
    		"	p.FUNCTIONNAME AS SQL \r\n" + 
    		"FROM\r\n" + 
    		"	(SELECT * from SYS_MENU where id in (select FUNCTION_ID from SYS_ROLES_MENUS_HISTORY where ROLE_ID = #{roleId})) a\r\n" + 
    		"	LEFT OUTER JOIN SYS_MENU aa ON a.PARENT_CODE = aa.FUNCTION_CODE\r\n" + 
    		"	LEFT JOIN PREDEFINE_MENU p ON p.FUNCTIONID = a.PRE_DEFINE_MENU_ID \r\n" + 
    		"WHERE\r\n" + 
    		"	a.valid_STATUS = 1 \r\n" + 
    		"	AND a.DELETE_FLAG = 0 \r\n" + 
    		"ORDER BY\r\n" + 
    		"	a.PARENT_CODE,\r\n" + 
    		"	a.ORDER_INDEX ")
    List<SysMenu> findAllMenuHisByRoleId(String roleId);
}