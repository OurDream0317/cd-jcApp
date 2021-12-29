package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.dao.SysMenuDao;
import com.tuozhi.zhlw.admin.dao.SysPrivilegeDao;
import com.tuozhi.zhlw.admin.entity.SysAppEntity;
import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesMenuEntity;
import com.tuozhi.zhlw.admin.mapper.RoleMapper;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import com.tuozhi.zhlw.admin.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 15:25
 **/
@Service
@Slf4j
public class SysRoleServiceImpl extends AbstractMapperServiceImpl<RoleMapper, SysRolesEntity>
        implements SysRoleService {

    @Resource
    RoleMapper roleMapper;
    @Autowired
    SysMenuDao sysMenuDao;

    @Autowired
    SysMenuService menuService;

    @Override
    public List<Map<String,Object>> getPrivilege(String pId) {
        Long id;
        if (StringUtils.isEmpty(pId)) {
            id = null;
        } else {
            id = Long.valueOf(pId);
        }
       return roleMapper.queryPrivilegeListById(id);
    }


     @Override
     public void saveRoleMenu(Long roleId, Long functionId) {
        roleMapper.insertRoleMenu(roleId,functionId);
    }

    @Override
    public List<Map<String, Object>> findAllRoles(Long userId) {
        return roleMapper.findAllRoles(userId);
    }

    @Override
    public List<Map<String, Object>> findAllBaseRole() {
        return roleMapper.findAllBaseRole();
    }



    @Override
    public List<Map<String,Object>> getRoleMenuByRoleId(Long roleId) {
        return roleMapper.getRoleMenuByRoleId(roleId);
    }

    @Override
    public List<Map<String,Object>> findRoleUser(Long roleId) {
        return roleMapper.findRoleUser(roleId);
    }

    @Override
    public List<Map<String,Object>> findRoleUserByRoleId(Long roleId,String searchValue) {
       if(StringUtils.isEmpty(searchValue)) {
    	   return roleMapper.findRoleUserByRoleId(roleId);
       }
       return roleMapper.findRoleUserByRoleIdByCondition(roleId,"%"+searchValue+"%");
    }


    @Override
    @Transactional
    public void saveUserRoleByUserIds(String[] userId, Long roleId) {
        for (String uId : userId) {
            roleMapper.insertUserRoles(Long.valueOf(uId),roleId);
        }
    }

    @Override
    @Transactional
    public void deleteRoleUser(Long userId, Long roleId) {
        roleMapper.deleteRoleUser(userId,roleId);
    }

    @Override
    @Transactional
    public void deleteAndSaveRoleMenu(Long roleId, String[] functionIds) {
    	Map<String, SysMenu> groupMenuMap = menuService.getGroupMenuMapByFunctionCode();
    	if (CollectionUtils.isEmpty(groupMenuMap)) {
            throw new RuntimeException("获取所有菜单不能为空！！！！");
        }
    	List<Map<String, Object>> oldList = roleMapper.getFunctionIdsByRoleId(roleId);
    	roleMapper.deleteRoleMenuHisByRoleId(roleId);
    	for (Map<String,Object> oldMap: oldList) {
             roleMapper.insertRoleMenuHis(roleId,oldMap.get("FUNCTIONID")==null?-1l:Long.parseLong(oldMap.get("FUNCTIONID").toString()));
        }
        roleMapper.deleteRoleMenuByRoleId(roleId);
        for (String functionCode : functionIds) {
            SysMenu sysMenu = groupMenuMap.get(functionCode);
            if (null == sysMenu) {
            	log.error("FunctionCode : {} 查询不到",functionCode);
                continue;
            }
            roleMapper.insertRoleMenu(roleId,sysMenu.getId());
        }
    }


    @Override
    @Transactional
    public void deleteRoleMenuByFunctionId(Long functionId) {
        roleMapper.deleteRoleMenuByFunctionId(functionId);
    }


	@Override
	public Boolean  isOrNotChildForCurrRoleIdByRoleId(String roleId, String childRoleId) {
		Integer count = roleMapper.hasChildCountByChildId(roleId, childRoleId);
		if(count == null) {
			return false;
		}else if(count > 0){
			return true;
		}
		return false;
	}


	@Override
	public List<Map<String, Object>> findRoleUserByRoleIdAndDeptIds(Long roleId, String searchValue,
			List<Long> deptIds) {
		if(StringUtils.isEmpty(searchValue)) {
	    	   return roleMapper.findRoleUserByRoleIdAndDeptIds(roleId,deptIds);
	       }
	       return roleMapper.findRoleUserByRoleIdByConditionAndDeptIds(roleId,"%"+searchValue+"%",deptIds);
	}

}
