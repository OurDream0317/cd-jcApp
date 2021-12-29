package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.dao.SysPrivilegeDao;
import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.admin.entity.SysLogEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesEntity;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysDeptService;
import com.tuozhi.zhlw.admin.service.SysRoleService;
import com.tuozhi.zhlw.common.annotation.SysLog;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.CookieUtils;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 14:54
 **/
@RestController
@RequestMapping("/role")
@Slf4j
public class SysRoleController extends BaseController {

    @Autowired
    SysRoleService roleService;
    
    @Autowired
    private SysDeptService sysDeptService ;

    @RequestMapping("/getPrivilege")
    @ResponseBody
    public ResultExtGrid getPrivilege(HttpServletRequest request) {
        String funcDataPrivis = super.getFunction_DATA_PRIVILEGE_ID(request, "1400");
        List<Map<String, Object>> privilegeList = roleService.getPrivilege(funcDataPrivis);
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, privilegeList, Long.valueOf(privilegeList.size()));
    }


    @RequestMapping("/findAllBaseRole")
    @ResponseBody
    public Map<String, Object> findAllBaseRole(HttpServletRequest request) {
        Map<String, Object> treeMap = new HashMap<String, Object>();
        String flagStr = request.getParameter("flag");
        Boolean flag = false;
        if(StringUtils.isNotEmpty(flagStr) && !flagStr.trim().equals("")) {
        	flag = true ;
        }
        LoginUser loginUser = getLoginUser(request);
        //查找当前登录着的所有权限
        if("25".equals(loginUser.getRoleIds())){
            loginUser.setUserId(1l);
        }
        List<Map<String, Object>> roleList = roleService.findAllRoles(loginUser.getUserId());
        List<Map<String, Object>> allRoles = roleService.findAllBaseRole();

        List curRoleTrees = new ArrayList();
        List<String> parentRoleArray = new ArrayList<String>();
        List<String> maxRole = new ArrayList<String>();

        // 查找当前用户所有角色的父级列表
        for (Map<String, Object> cur : roleList) {
            parentRoleArray.add(getCurentRoleParentTreeId(cur.get("PARENTID").toString(), allRoles) + ","
                    + cur.get("ROLEID").toString());
        }

        for (String i : parentRoleArray) {
            String tempMax = i;
            for (String j : parentRoleArray) {
                if (tempMax.startsWith(j)) {
                    tempMax = j;
                }
            }
            if (!maxRole.contains(tempMax)) {
                maxRole.add(tempMax);
            }
        }

        // 最后得出当前用户角色能控制的角色列表
        for (Map<String, Object> cur : roleList) {
            boolean isContinue = false;
            for (String i : maxRole) {
                if (i.endsWith(("," + cur.get("ROLEID").toString()))) {
                    isContinue = true;
                    break;
                }
            }
            if (!isContinue) {
                continue;
            }
            Map<String, Object> curObj = new HashMap<String, Object>();
            curObj.put("id", cur.get("ROLEID").toString());
            curObj.put("ROLE_ID", cur.get("ROLEID").toString());
            curObj.put("parentid", cur.get("PARENTID").toString());
            curObj.put("ROLE_NAME", cur.get("ROLENAME").toString());
            curObj.put("DATA_PRIVILEGE_ID", cur.get("PRIVILEGEID"));
            curObj.put("PRIVILEGENAME", cur.get("PRIVILEGENAME"));
            curObj.put("parentname", cur.get("PARENTNAME"));
            curObj.put("expanded", true);
            curObj.put("children", getCurentRoleTree(cur.get("ROLEID").toString(), allRoles,
                    cur.get("ROLENAME").toString(),flag));
            curRoleTrees.add(curObj);
        }
        treeMap.put("children", curRoleTrees);


        return treeMap;
    }

    public List<Map<String, Object>> getCurentRoleTree(String parentid, List<Map<String, Object>> allRole,
                                                       String parentName,Boolean flag) {
        List<Map<String, Object>> templist = new ArrayList<Map<String, Object>>();
        for (Map<String, Object> cur : allRole) {
            if (cur.get("ROLE_PARENT_ID").toString().equals(parentid)) {
                Map<String, Object> obj = new HashMap<String, Object>();
                obj.put("id", cur.get("ROLE_ID"));
                obj.put("ROLE_ID", cur.get("ROLE_ID"));
                obj.put("parentid", parentid);
                obj.put("DATA_PRIVILEGE_ID", cur.get("DATA_PRIVILEGE_ID"));
                obj.put("PRIVILEGENAME", cur.get("PRIVILEGENAME"));
                obj.put("parentname", parentName);
                obj.put("expanded", true);
                if(!flag) {
                	 obj.put("checked", false);
                }
                obj.put("ROLE_NAME", cur.get("ROLE_NAME"));
                List<Map<String, Object>> tempArray = getCurentRoleTree(cur.get("ROLE_ID").toString(), allRole,
                        cur.get("ROLE_NAME").toString(),flag);
                if (tempArray.size() > 0) {
                    obj.put("leaf", false);
                    obj.put("children", tempArray);
                } else {
                    obj.put("leaf", true);
                }
                templist.add(obj);
            }
        }
        return templist;
    }


    public String getCurentRoleParentTreeId(String parentid, List<Map<String, Object>> allRole) {
        String templist = parentid;
        for (Map<String, Object> cur : allRole) {
            if (cur.get("ROLE_ID").toString().equals(parentid)) {
                templist = getCurentRoleParentTreeId(cur.get("ROLE_PARENT_ID").toString(), allRole) + "," + templist;
                break;
            }
        }
        return templist;
    }

    @RequestMapping("/insertOrUpdateBaseRole")
    @SysLog(value = "新增或者更新角色")
    public ResultMsg insertOrUpdateBaseRole(HttpServletRequest request,
                                            @Validated SysRolesEntity sysRole,
                                            @RequestParam("roleId") Long roleId,
                                            @RequestParam String flag) {

        String dataPrivilegeId = request.getParameter("data_privilege_id");
        sysRole.setDataPrivilegeId(Long.valueOf(dataPrivilegeId));
        
        String choiceRoleId = request.getParameter("choiceRoleId");
        if(StringUtils.isNotEmpty(choiceRoleId)) {
        	sysRole.setParentRoleId(Long.parseLong(choiceRoleId));
        }else {
        	sysRole.setParentRoleId(null);
        }

        //更新
        if (StringUtils.equals("update", flag)) {
            SysRolesEntity roleEntity = roleService.selectByKey(roleId);
            BeanUtils.copyProperties(sysRole, roleEntity, "id");
            try {
                roleService.updateNotNull(roleEntity);
            } catch (Exception e) {
                log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(), e);
                return ResultMsgUtil.isError(ResultMsgEnum.UPDATE_ERROR);
            }
            return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK, null, sysRole);
        }

        //保存
        SysRolesEntity role = new SysRolesEntity();
        role.setRoleName(sysRole.getRoleName());
        role.setParentRoleId(sysRole.getParentRoleId());
        role.setDataPrivilegeId(Long.valueOf(dataPrivilegeId));

        try {
            roleService.save(role);
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }

        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK, null, role);
    }

    @RequestMapping("/findRoleMenuByRoleId")
    @SysLog(value = "查询角色与菜单列表")
    public ResultExtGrid findRoleMenuByRoleId(@RequestParam("ROLE_ID") Long roleId) {
        List<Map<String, Object>> roleList = null;
        try {
            roleList = roleService.getRoleMenuByRoleId(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
            return ResultExtGridUtil.isError(ResultMsgEnum.QUERY_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, roleList, (long)roleList.size());
    }

    // 角色管理用户权限查询,查询某一个角色下的用户列表
    @RequestMapping("/findRoleUsers")
    @SysLog(value = "查询角色下的用户列表")
    public ResultExtGrid findRoleUsers(@RequestParam("ROLE_ID") Long roleId) {
        List<Map<String, Object>> roleUserList;
        try {
            roleUserList = roleService.findRoleUser(roleId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
            return ResultExtGridUtil.isError(ResultMsgEnum.QUERY_ERROR);
        }

        return ResultExtGridUtil.isSuccess(ResultMsgEnum.QUERY_OK, roleUserList, (long) roleUserList.size());

    }

    @RequestMapping("delete")
    public ResultMsg delete(@RequestParam(value = "ROLE_ID") Long roleId) {

        try {
            roleService.deleteByKey(roleId);
        } catch (Exception e) {
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }

    @RequestMapping("/findRoleUserByRoleId")
    public ResultExtGrid findRoleUserByRoleId(@RequestParam("ROLE_ID") Long roleId,HttpServletRequest request) {
        String searchValue = request.getParameter("searchValue");
        List<Map<String, Object>> userList = new ArrayList<Map<String, Object>>();
        
        
        String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        LoginUser loginUser =  tokenManager.validate(token);
        if(null==loginUser) {
        	  return null;
        }
        List<SysDeptEntity> depts=sysDeptService.getAllChildIdsById(loginUser.getDeptId());
        boolean flag=false;
        String[] roles= loginUser.getRoleIds().split(",");
        List<Long> deptIds = new ArrayList<Long>();
        for(int i=0;i<roles.length;i++) {
        	if("1".equals(roles[i])) {
        		flag=true;
        		break;
        	}
        }
        if(flag) {
        	userList = roleService.findRoleUserByRoleId(roleId,searchValue);
        }else {
        	for(SysDeptEntity e : depts) {
        		deptIds.add(e.getId());
        	}
        	userList = roleService.findRoleUserByRoleIdAndDeptIds(roleId,searchValue,deptIds);
        }
        
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.QUERY_OK,userList,(long)userList.size());
    }

    @RequestMapping("/bindUserRole")
    @SysLog(value = "绑定用户与角色关系")
    public ResultMsg bindUserRole(@RequestParam("ROLE_ID") Long roleId, @RequestParam("USER_ID") String userId) {
        String[] userIds = userId.split(",");
        try {
            roleService.saveUserRoleByUserIds(userIds, roleId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);

    }

    @RequestMapping("/deleteRoleUser")
    @SysLog(value = "删除角色")
    public ResultMsg deleteRoleUser(@RequestParam("ROLE_ID") Long roleId, @RequestParam("USER_ID") Long userId) {

        try {
            roleService.deleteRoleUser(userId, roleId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK);

    }

    @RequestMapping("/saveRoleFuncInfo")
    @SysLog(value = "授权角色与菜单关系")
    public ResultMsg saveRoleFuncInfo(@RequestParam("ROLE_ID") Long roleId, @RequestParam("FUNCTIONIDs") String functionIdString) {
        String[] functionIds = functionIdString.split(",");

        try {
            roleService.deleteAndSaveRoleMenu(roleId,functionIds);
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
    }
    @RequestMapping("/isOrNotChildForCurrRoleIdByRoleId")
    public ResultMsg isOrNotChildForCurrRoleIdByRoleId(@RequestParam("roleId") String roleId, @RequestParam("childRoleId") String childRoleId) {
    	Boolean result = roleService.isOrNotChildForCurrRoleIdByRoleId(roleId, childRoleId);
    	if(result) {
    		return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS);
    	}
		return ResultMsgUtil.isError(ResultMsgEnum.ERROR);
    }

}
