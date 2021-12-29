package com.tuozhi.zhlw.admin.dao;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tuozhi.zhlw.admin.entity.SysAppEntity;

/**
 * @author linqi
 * @create 2019/09/03 21:08
 **/

@Repository
public interface SysAppDao extends BaseDao<SysAppEntity,Long> {


    @Query(value = "SELECT APP.ID AS id,APP.NAME AS name,APP.SORT AS sort," +
            "APP.CREATE_TIME AS createTime ," +
            "APP.CODE AS code ,APP.ICON_CLS  FROM SYS_APP APP WHERE APP.ID IN (" +
            " SELECT DISTINCT APP_ID FROM SYS_USERS U INNER JOIN SYS_USER_ROLES UR ON U.ID = UR.USER_ID " +
            " INNER JOIN SYS_ROLES_MENU RM ON UR.ROLE_ID = RM.ROLE_ID" +
            " INNER JOIN SYS_MENU M ON RM.FUNCTION_ID = M.ID WHERE U.ID = ?1) AND APP.VALID_STATUS = 1",nativeQuery = true)
    List<Map<String,Object>> findAllConditionByCondition(Long userId);
    
    @Query(value = "SELECT\r\n" + 
    		"	APP.ID AS id,\r\n" + 
    		"	APP.NAME AS name,\r\n" + 
    		"	APP.SORT AS sort,\r\n" + 
    		"	APP.CREATE_TIME AS createTime,\r\n" + 
    		"	APP.CODE AS code,\r\n" + 
    		"	APP.ICON_CLS \r\n" + 
    		"FROM\r\n" + 
    		"	SYS_APP APP \r\n" + 
    		"WHERE\r\n" + 
    		"	APP.ID IN (\r\n" + 
    		"SELECT DISTINCT\r\n" + 
    		"	APP_ID \r\n" + 
    		"FROM\r\n" + 
    		"	SYS_ROLES_MENU RM \r\n" + 
    		"	INNER JOIN SYS_MENU M ON RM.FUNCTION_ID = M.ID \r\n" + 
    		"WHERE\r\n" + 
    		"	RM.ROLE_ID = ?1 \r\n" + 
    		"	) \r\n" + 
    		"	AND APP.VALID_STATUS = 1",nativeQuery = true)
    List<Map<String,Object>> findAllConditionByCondition(String roleId);
    @Query(value = "SELECT APP.ID AS id,APP.NAME AS name,APP.SORT AS sort," +
            "APP.CREATE_TIME AS createTime ," +
            "APP.CODE AS code ,APP.ICON_CLS  FROM SYS_APP APP WHERE  APP.VALID_STATUS = 1",nativeQuery = true)
    List<Map<String,Object>> findAllCondition();

/*    @Query(value = "select " +
            "sya.id as id, " +
            "sya.name as name, " +
            "sya.sort as sort, " +
            "sya.create_time as createTime, " +
            "sya.valid_status as validStatus, " +
            "sya.code as code " +
            "from sys_app sya" +
            "where 1=1",nativeQuery = true)
    List<SysAppEntity> findGridAll();*/
    @Query(value = "SELECT\r\n" + 
    		"	APP.ID AS id,\r\n" + 
    		"	APP.NAME AS name,\r\n" + 
    		"	APP.SORT AS sort,\r\n" + 
    		"	APP.CREATE_TIME AS createTime,\r\n" + 
    		"	APP.CODE AS code,\r\n" + 
    		"	APP.ICON_CLS \r\n" + 
    		"FROM\r\n" + 
    		"	SYS_APP APP \r\n" + 
    		"WHERE\r\n" + 
    		"	APP.ID IN (\r\n" + 
    		"SELECT DISTINCT\r\n" + 
    		"	APP_ID \r\n" + 
    		"FROM\r\n" + 
    		"	SYS_ROLES_MENUS_HISTORY RM \r\n" + 
    		"	INNER JOIN SYS_MENU M ON RM.FUNCTION_ID = M.ID \r\n" + 
    		"WHERE\r\n" + 
    		"	RM.ROLE_ID = ?1 \r\n" + 
    		"	) \r\n" + 
    		"	AND APP.VALID_STATUS = 1",nativeQuery = true)
    List<Map<String,Object>> findAllConditionHisByCondition(String roleId);
}
