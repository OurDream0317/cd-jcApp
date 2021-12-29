package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.PredefineMenu;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.mapper.PredefineMenuMapper;
import com.tuozhi.zhlw.admin.service.ColumnInfoService;
import com.tuozhi.zhlw.admin.service.PredefineMenuService;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import com.tuozhi.zhlw.admin.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author linqi
 * @create 2019/09/19 7:00
 **/
@Service
public class PredefineMenuServiceImpl extends AbstractMapperServiceImpl<PredefineMenuMapper, PredefineMenu>
        implements PredefineMenuService {

    @Resource
    PredefineMenuMapper predefineMenuMapper;

    @Autowired
    ColumnInfoService columnInfoService;

    @Autowired
    SysMenuService menuService;


    @Autowired
    SysRoleService sysRoleService;


    @Override
    public PredefineMenu findAllByFunctionId(Long functionId) {
        return predefineMenuMapper.findAllByFunctionId(functionId);
    }

    @Override
    public String getByDeptLoginIdOfAdmin() {
        return predefineMenuMapper.getByDeptLoginIdOfAdmin();
    }

    @Override
    public String getByDeptLoginId(Long deptId) {
        return predefineMenuMapper.getByDeptLoginId(deptId);
    }

    @Override
    @Transactional
    public void deletePredefineMenu(Long functionId) {
        //1.删除PredefineMenu by functionId (code)
        //2.删除BASE_FUNC_SQL table by functionId (code)
        //3.删除SYS_MENU 关联 by PreDefineMenuId
        //4.删除SYS_ROLES_MENU 关联 by functionId
        //4.删除SYS_SQL_COLUMNS 关联 by functionId

        predefineMenuMapper.deleteByFunctionId(functionId);
        predefineMenuMapper.deleteBaseFuncSqlParamByFunctionId(functionId);
        columnInfoService.delSysColumnsInfo(functionId);

        SysMenuEntity sysMenuEntity = menuService.getEntityByPreDefineMenuId(functionId);
        menuService.deleteMenuByFunction(functionId.toString());
        if (null != sysMenuEntity) {
            sysRoleService.deleteRoleMenuByFunctionId(sysMenuEntity.getId());
        }

    }


}
