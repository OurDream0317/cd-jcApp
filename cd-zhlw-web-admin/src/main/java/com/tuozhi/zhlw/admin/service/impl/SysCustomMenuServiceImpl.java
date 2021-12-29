package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuDetailsEntity;
import com.tuozhi.zhlw.admin.entity.SysCustomMenuEntity;
import com.tuozhi.zhlw.admin.mapper.CustomMenuMapper;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysCustomMenuService;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/26 10:29
 **/

@Service
public class SysCustomMenuServiceImpl extends AbstractMapperServiceImpl<CustomMenuMapper, SysCustomMenuEntity>
        implements SysCustomMenuService {

    @Resource
    CustomMenuMapper customMenuMapper;

    @Autowired
    SysMenuService menuService;

    @Override
    public List<SysCustomMenuEntity> findAllByUserId(Long userId) {
        return customMenuMapper.findAllByUserId(userId);
    }

    @Override
    public List createCustomMenuTree(List<SysCustomMenuEntity> customMenuList) {

        Map<Long, SysMenu> sysMenuMap = getSysMenuMap();
        List objList = new ArrayList();
        for (SysCustomMenuEntity customMenuEntity : customMenuList) {
            Map<String, Object> objMap = new HashMap<>();
            objMap.put("id", customMenuEntity.getId());
            objMap.put("parentId", 0);
            objMap.put("parentName", 0);
            objMap.put("text", customMenuEntity.getCustomMenuName());
            objMap.put("qtip", customMenuEntity.getCustomMenuName());
            objMap.put("orderindex", 1);
            objMap.put("leaf", false);
            objMap.put("isLeaf", 0);
           // objMap.put("iconCls", customMenuEntity.getIconcls());
            objMap.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            objMap.put("viewType", "menu" + 1);
            objMap.put("routeId", "menu" + 1);
            List childList = createCustomMenuDetailList(customMenuEntity, sysMenuMap);
            objMap.put("children",childList);
            objMap.put("expanded", true);
            objList.add(objMap);
        }
        return objList;
    }

    @Override
    public boolean createNewCustomMenu(SysCustomMenuEntity sysCustomMenuEntity) {
        return this.saveNotNull(sysCustomMenuEntity) == 1;
    }

    @Override
    public boolean updateCustomMenuName(SysCustomMenuEntity sysCustomMenuEntity) {
        return customMenuMapper.updateCustMenuName(sysCustomMenuEntity) ;
    }

    @Override
    public boolean deleteCustMenu(String sysCustomMenuId) {
        return super.deleteByIds(sysCustomMenuId) == 1 ;
    }

    private List createCustomMenuDetailList(SysCustomMenuEntity sysCustomMenuEntity,Map<Long, SysMenu> sysMenuMap) {

        List objList = new ArrayList();
        int index = 0;
        for (SysCustomMenuDetailsEntity detailsEntity : sysCustomMenuEntity.getDetailList()) {
            Map<String, Object> objMap = new HashMap<>();
            SysMenu sysMenu = sysMenuMap.get(detailsEntity.getMenuId());

            objMap.put("id", sysMenu.getFunctionCode());
            objMap.put("parentId", 0);
            objMap.put("parentName", 0);
            objMap.put("text", sysMenu.getFunctionName());
            objMap.put("qtip", sysMenu.getFunctionName());
            objMap.put("orderindex", index);
            objMap.put("leaf", true);
            objMap.put("url", sysMenu.getUrl());
            objMap.put("isLeaf", 1);
            objMap.put("menuType", sysMenu.getMenuType());
            objMap.put("iconCls", sysMenu.getIconcls());
            objMap.put("viewType", "menu" + sysMenu.getFunctionCode());
            objMap.put("routeId", "menu" + sysMenu.getFunctionCode());
            objMap.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            objMap.put("leaf", "true");
            objList.add(objMap);
            index++;
        }
        return objList;
    }

    private Map<Long,SysMenu> getSysMenuMap() {
        List<SysMenu> allMenu = menuService.findAllMenu();
        Map<Long, SysMenu> sysMenuMap = new HashMap<>();

        for (SysMenu menu : allMenu) {
            sysMenuMap.put(menu.getId(),menu);
        }
        return sysMenuMap;
    }
}
