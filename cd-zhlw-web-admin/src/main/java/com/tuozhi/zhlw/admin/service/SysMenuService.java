package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesMenuEntity;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/03 17:30
 **/

public interface SysMenuService extends BaseMapperService<SysMenuEntity>{
    /**
     * 根据userId 获得各子系统对应的权限菜单
     * @param userId
     * @return
     */
    List<SysMenu> findAllByUserId(Long userId);


    List<SysMenu> findAllMenu();
    
    List<SysMenu> findAllMenuAppAll();
    
    List<SysMenu> findAllMenuByRoleId(String roleId);
    List<SysMenu> findAllMenuHisByRoleId(String roleId);
    
    List<SysMenu> findAllMenuByAppId(Long appId) ;

    List<Object> createMenuTreeNode(String functionCode, List<SysMenu> sysMenuList,String checkFlag,Boolean expandedFlag);


    List<Object> getSysMenuTreeCrecursion(long parentId, List funcList, String fram);

    @Transactional
    void saveMenuAndRolesMenu(SysMenuEntity menuEntity, String[] roleIds);

    void deleteMenuByFunction(String functionCode);

    List<SysMenu> getSysMenuTreeIsNotLeafNode(Long appId);

    List<Map<String,Object>>  getPredefineMenuTreeByUserId(Map map) throws IOException, SQLException;

    void saveEntity(Object entity);

    List<Map<String,Object>> selectMenuIsvalId(long functionId);

    SysMenuEntity getEntityByFunctionCode(long functionCode);


    Map<String, SysMenu> getGroupMenuMapByFunctionCode();

    SysMenuEntity getEntityByPreDefineMenuId(long preDefineMenuId);

    boolean queryMenuRepeat(String functionCode) ;

}
