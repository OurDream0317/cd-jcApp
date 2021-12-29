package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 15:25
 **/
public interface SysRoleService extends BaseMapperService<SysRolesEntity>{
    List<Map<String,Object>> getPrivilege(String funcDataPrivis);


    void saveRoleMenu(Long roleId, Long functionId);

    List<Map<String, Object>> findAllRoles(Long userId);

    List<Map<String, Object>> findAllBaseRole();



    List<Map<String,Object>> getRoleMenuByRoleId(Long roleId);

    List<Map<String,Object>> findRoleUser(Long roleId);

    List<Map<String,Object>> findRoleUserByRoleId(Long roleId,String searchValue);
    
    List<Map<String,Object>> findRoleUserByRoleIdAndDeptIds(Long roleId,String searchValue,List<Long> deptIds);

    @Transactional
    void saveUserRoleByUserIds(String[] userId, Long roleId);

    @Transactional
    void deleteRoleUser(Long userId, Long roleId);

    @Transactional
    void deleteAndSaveRoleMenu(Long roleId, String[] functionIds);

    @Transactional
    void deleteRoleMenuByFunctionId(Long functionId);
    
    Boolean isOrNotChildForCurrRoleIdByRoleId(String roleId,String childRoleId);
}
