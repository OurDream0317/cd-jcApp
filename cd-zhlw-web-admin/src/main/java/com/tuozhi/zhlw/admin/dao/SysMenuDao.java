package com.tuozhi.zhlw.admin.dao;

import com.tuozhi.zhlw.admin.entity.SysMenuEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/03 17:14
 **/

@Repository
public interface SysMenuDao extends BaseDao<SysMenuEntity, Long> {

    @Query(value = "SELECT DISTINCT a.ID ,a.FUNCTION_NAME ,a.PARENT_ID,a.DESCRIPTION, " +
            " a.ORDER_INDEX,a.LEAF_NODE_STATUS , a.ICONCLS,a.APP_ID" +
            " from SYS_MENU a " +
            " INNER JOIN SYS_ROLES_MENU f on a.ID = f.FUNCTION_ID " +
            " INNER JOIN SYS_USER_ROLES r on r.ROLE_ID = f.ROLE_ID " +
            " where r.USER_ID = ?1 and a.valid_status = 1 and a.LEAF_NODE_STATUS !=2 order by a.ORDER_INDEX,a.PARENT_ID",
            nativeQuery = true)
    List<Map<String,Object>> findAllByUserId(Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE SYS_MENU SET DELETE_FLAG = 1 WHERE FUNCTION_CODE = ?1", nativeQuery = true)
    void deleteByFunctionCode(String functionCode);

    @Query(value = "DELETE FROM " +
            "(" +
            "  SELECT RM.* " +
            "  FROM SYS_ROLES_MENU RM ,SYS_MENU M" +
            "  WHERE RM.FUNCTION_ID = M.ID AND M.FUNCTION_CODE = ?1 " +
            ")", nativeQuery = true)
    void deleteRolesMenuByFunctionCode(String functionCode);
}
