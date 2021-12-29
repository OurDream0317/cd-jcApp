package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuEntity;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/26 10:30
 **/

public interface SysCustomMenuService extends BaseMapperService<SysCustomMenuEntity>{

    List<SysCustomMenuEntity> findAllByUserId(Long userId) ;

    List createCustomMenuTree(List<SysCustomMenuEntity> customMenuList) ;

    boolean createNewCustomMenu(SysCustomMenuEntity sysCustomMenuEntity) ;

    boolean updateCustomMenuName(SysCustomMenuEntity sysCustomMenuEntity) ;

    boolean deleteCustMenu(String sysCustomMenuId) ;

}
