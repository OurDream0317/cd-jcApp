package com.tuozhi.zhlw.admin.controller;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysAppEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysAppService;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

/**
 * @author linqi
 * @create 2019/09/03 23:38
 **/

@RestController
@RequestMapping("/app")
@Slf4j
public class SysAppController extends BaseController {

    @Autowired
    SysAppService sysAppService;

    @Autowired
    SysMenuService sysMenuService;
    //admin角色
    private static final String adminRole="1";


    /**
     * 获取应用下包含菜单的树结构
     * @param request
     * @return
     */
    @RequestMapping("/appList")
    public Map<String, Object> appList(HttpServletRequest request) {

        Map<String, Object> dataMap = new HashMap<>();

        LoginUser loginUser = getLoginUser(request);

        String checkFlag = request.getParameter("checkFlag");
        String noCustom = request.getParameter("noCustom");

        if (checkFlag == null) {
            checkFlag = "";
        }
        Boolean expandedFlag = true ;
        if(!StringUtils.isEmpty(noCustom)) {
        	expandedFlag = false ;
        }
        String rolesStr=loginUser.getRoleIds();
        String[] roles=new String[] {};
        if(StringUtils.isNotEmpty(rolesStr)) {
        	roles=rolesStr.split(",");
        }
        Boolean flag=false;
        for(String e : roles) {
        	if(adminRole.equals(e)) {
        		flag=true;
        		break;
        	}
        }

        List<SysAppEntity> appList = flag?sysAppService.findAllCondition():sysAppService.findAllCondition(loginUser.getUserId());
        List<SysMenu> sysMenuList = flag?sysMenuService.findAllMenuAppAll():sysMenuService.findAllByUserId(loginUser.getUserId());
        Map<Long, List<Object>> appMap = genGroupAppMap(sysMenuList, checkFlag,expandedFlag);

        List<Object> childList = new ArrayList<>();
        for (SysAppEntity sysAppEntity : appList) {
            if (appMap.get(sysAppEntity.getId()) == null) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sysAppEntity.getId());
            map.put("parentId", 0);
            map.put("parentName", "根节点");
            map.put("text", sysAppEntity.getName());
            map.put("qtip", sysAppEntity.getName());
            map.put("orderindex", 1);
            map.put("leaf", false);
            map.put("isLeaf", 0);
            map.put("viewType", "menu" + sysAppEntity.getId());
            map.put("routeId", "menu" + sysAppEntity.getId());
            map.put("iconCls", sysAppEntity.getIconCls());
            map.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            map.put("children", appMap.get(sysAppEntity.getId()));
            map.put("expanded", expandedFlag);
            childList.add(map);
        }

        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", childList);

        //welcome 页面要显示自定义应用管理，所以要单独添加到Tree上
		 if (childList.size() != 0 && StringUtils.isEmpty(noCustom)) {
		  sysAppService.addCustomMenuTree(dataMap, loginUser.getUserId());
		 }


        return dataMap;
    }


    /**
     * 获得菜单表里所有可用的menu 包括按钮，但是不包括自定义菜单应用节点
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/getAppTreeWithoutCustomMenu")
    public Map<String, Object> appListWithoutCustomMenu(HttpServletRequest request) {

        Map<String, Object> dataMap = new HashMap<>();

        String checkFlag = request.getParameter("checkFlag");
        String parentRoleId = request.getParameter("parentRoleId");
        
        String roleId = request.getParameter("roleId");
        if (checkFlag == null) {
            checkFlag = "";
        }
        LoginUser loginUser = getLoginUser(request);
        String rolesStr=loginUser.getRoleIds();
        String[] roles=new String[] {};
        if(StringUtils.isNotEmpty(rolesStr)) {
        	roles=rolesStr.split(",");
        }
        Boolean flag=false;
        for(String e : roles) {
        	if(adminRole.equals(e)) {
        		flag=true;
        		break;
        	}
        }
        Boolean roleFlag=false;
        if(roleId !=null) {
        	 for(String e : roles) {
        		 if(roleId.equals(e)) {
            		roleFlag=true;
            		break;
            	}
            }
        }
        List<SysAppEntity> appList = new ArrayList<SysAppEntity>();
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
        if(null != roleId && roleFlag) {
        	appList = flag?sysAppService.findAllCondition():sysAppService.findAllCondition(roleId);
            sysMenuList = flag?sysMenuService.findAllMenu():sysMenuService.findAllMenuByRoleId(roleId);
        }else {
        	appList = parentRoleId ==null?(flag?sysAppService.findAllCondition():sysAppService.findAllCondition(loginUser.getUserId())):sysAppService.findAllCondition(parentRoleId);
            sysMenuList = parentRoleId ==null?(flag?sysMenuService.findAllMenu():sysMenuService.findAllMenuByRoleId(roles[0])):sysMenuService.findAllMenuByRoleId(parentRoleId);
        }
      
        Map<Long, List<Object>> appMap = genGroupAppMap(sysMenuList, checkFlag,true);

        List<Object> childList = new ArrayList<>();
        for (SysAppEntity sysAppEntity : appList) {
            if (appMap.get(sysAppEntity.getId()) == null) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sysAppEntity.getId());
            map.put("parentId", 0);
            map.put("parentName", "根节点");
            map.put("text", sysAppEntity.getName());
            map.put("qtip", sysAppEntity.getName());
            map.put("orderindex", 1);
            map.put("leaf", false);
            map.put("isLeaf", 0);
            map.put("viewType", "menu" + sysAppEntity.getId());
            map.put("routeId", "menu" + sysAppEntity.getId());
            map.put("iconCls", sysAppEntity.getIconCls());
            map.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            map.put("children", appMap.get(sysAppEntity.getId()));
            map.put("expanded", false);
            childList.add(map);
        }

        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", childList);

        return dataMap;
    }



    /**
     * 查询所有应用
     *
     * @param queryParams 分页参数
     * @return grid类型返回集
     */
    @RequestMapping("/findAll")
    public ResultExtGrid findAll(QueryParams queryParams,
                                 @RequestParam(name = "orfieldvalue",required = false) String appName,
                                 @RequestParam(name = "appName",required = false) String muchAppName,
                                 @RequestParam(name = "custAppId",required = false) String custAppId,
                                 @RequestParam(name = "querymodel",required = false) String queryModel) {
        Long appId = (custAppId != null ? Long.parseLong(custAppId) : null) ;
        // 点击自定义查询中的‘查询统计’按
        // 钮之后 querymodel赋值为"dialog"
        String realAppName = "dialog".equals(queryModel) ? muchAppName : appName;
        PageInfo<SysAppEntity> all = null;
        try {
            all = sysAppService.findAllByPageHelper(queryParams, realAppName,appId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_OK.getMsg(), e);
            return ResultExtGridUtil.isError(ResultMsgEnum.ERROR) ;
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, all.getList(), (long)all.getTotal());
    }

    /**
     * 保存新应用
     */
    @RequestMapping(value = "/insertApp", method = RequestMethod.POST, params = {"flag!=update"})
    @ResponseBody
    public ResultMsg InsertOrUpdateUserInfoManage(@Valid SysAppEntity appEntity) throws Exception {
        appEntity.setValidStatus(1);
        appEntity.setCreateTime(new Date());
        ResultMsg resultMsg = null;
        try {
            sysAppService.saveSysAppEntity(appEntity) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_OK.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
    }


    /**
     * 更新应用信息
     */
    @RequestMapping(value = "/insertApp", method = RequestMethod.POST, params = {"flag=update"})
    @ResponseBody
    public ResultMsg UpdateUserInfoManage(@Valid SysAppEntity appEntity) throws Exception {
        if (appEntity.getValidStatus() == null) {
            // 用户可能将标志位删掉
            appEntity.setValidStatus(0);
        }
        try {
            sysAppService.updateInfoById(appEntity) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_OK.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.UPDATE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK) ;
    }

    /**
     * 删除应用
     */
    @RequestMapping(value = "/deleteAppInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg deleteAppStatus(@RequestParam(name = "appId") Long appId) {
        try {
            sysAppService.deleteAppByMark(appId.toString()) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }

    private  Map<Long, List<Object>> genGroupAppMap(List<SysMenu> sysMenuList,String checkFlag,Boolean expandedFlag) {

        Map<Long, List<Object>> appMap = new HashMap<>();
        Map<Long, List<SysMenu>> appGroupMap = new HashMap<>();

        for (SysMenu sysMenu : sysMenuList) {
            if (appGroupMap.get(sysMenu.getAppId()) == null) {
                List<SysMenu> menuList = new ArrayList<>();
                menuList.add(sysMenu);
                appGroupMap.put(sysMenu.getAppId(), menuList);
            } else {
                List<SysMenu> menuList = appGroupMap.get(sysMenu.getAppId());
                menuList.add(sysMenu);
            }
        }
        for (Map.Entry<Long, List<SysMenu>> entry : appGroupMap.entrySet()) {
            List<Object> menuTreeNode = sysMenuService.createMenuTreeNode("0", entry.getValue(), checkFlag,expandedFlag);
            appMap.put(entry.getKey(),menuTreeNode);
        }
        return appMap;
    }


    /**
     * 应用添加或修改时，服务端验重
     * @param appName 输入的应用名
     * @return 包含了应用名是否已存在的消息体，存在返回true，不存在返回false
     */
    @RequestMapping(value = "/queryAppRepeat",method = RequestMethod.POST,params = {"flag=appName"})
    public ResultMsg queryAppNameRepeat(@Param("appName") String appName){
        try {
            if (this.sysAppService.checkRepeatAppName(appName))
                return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS) ;
            else
                return ResultMsgUtil.isError(ResultMsgEnum.SUCCESS);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isError(ResultMsgEnum.ERROR) ;
    }

    /**
     * 应用添加或修改时，服务端验重
     * @param appCode 输入的应用编号
     * @return 包含了应用编号是否已存在的消息体，存在返回true，不存在返回false
     */
    @RequestMapping(value = "/queryAppRepeat",method = RequestMethod.POST,params = {"flag=appCode"})
    public ResultMsg queryAppCodeRepeat(@Param("appCode") String appCode){
        try {
            if (this.sysAppService.checkRepeatAppCode(appCode))
                return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS) ;
            else
                return ResultMsgUtil.isError(ResultMsgEnum.SUCCESS);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isError(ResultMsgEnum.ERROR) ;
    }

    /**
     * 查找可用的应用管理
     * @return
     */
    @RequestMapping("/listByAvailable")
    public ResultExtGrid listByAvailable() {
        List<SysAppEntity> sysAppList = sysAppService.listByAvailable();
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.QUERY_OK,sysAppList,(long)sysAppList.size());
    }

    /**
     * 获得当前角色下里所有可用的menu 包括按钮，但是不包括自定义菜单应用节点
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/getAppTreeWithoutCustomMenuCurr")
    public Map<String, Object> appListWithoutCustomMenuCurr(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<>();
        String roleId = request.getParameter("roleId");
        List<SysAppEntity> appList = new ArrayList<SysAppEntity>();
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
        List<Object> childList = new ArrayList<>();
        if(StringUtils.isEmpty(roleId)|| roleId.trim().equals("")) {
        	  dataMap.put("success", true);
              dataMap.put("expanded", true);
              dataMap.put("children", childList);
              return dataMap;
        }
        appList = sysAppService.findAllCondition(roleId);
        sysMenuList = sysMenuService.findAllMenuByRoleId(roleId);
        Map<Long, List<Object>> appMap = genGroupAppMap(sysMenuList, "",true);

        for (SysAppEntity sysAppEntity : appList) {
            if (appMap.get(sysAppEntity.getId()) == null) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sysAppEntity.getId());
            map.put("parentId", 0);
            map.put("parentName", "根节点");
            map.put("text", sysAppEntity.getName());
            map.put("qtip", sysAppEntity.getName());
            map.put("orderindex", 1);
            map.put("leaf", false);
            map.put("isLeaf", 0);
            map.put("viewType", "menu" + sysAppEntity.getId());
            map.put("routeId", "menu" + sysAppEntity.getId());
            map.put("iconCls", sysAppEntity.getIconCls());
            map.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            map.put("children", appMap.get(sysAppEntity.getId()));
            map.put("expanded", true);
            childList.add(map);
        }

        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", childList);

        return dataMap;
    }
    /**
     * 获得上一次角色下里所有可用的menu 包括按钮，但是不包括自定义菜单应用节点
     * @param request
     * @param
     * @return
     */
    @RequestMapping("/getAppTreeWithoutCustomMenuHis")
    public Map<String, Object> appListWithoutCustomMenuHis(HttpServletRequest request) {
        Map<String, Object> dataMap = new HashMap<>();
        String roleId = request.getParameter("roleId");
        List<SysAppEntity> appList = new ArrayList<SysAppEntity>();
        List<SysMenu> sysMenuList = new ArrayList<SysMenu>();
        List<Object> childList = new ArrayList<>();
        if(StringUtils.isEmpty(roleId)|| roleId.trim().equals("")) {
        	  dataMap.put("success", true);
              dataMap.put("expanded", true);
              dataMap.put("children", childList);
              return dataMap;
        }
        appList = sysAppService.findAllConditionHis(roleId);
        sysMenuList = sysMenuService.findAllMenuHisByRoleId(roleId);
        Map<Long, List<Object>> appMap = genGroupAppMap(sysMenuList, "",true);

        for (SysAppEntity sysAppEntity : appList) {
            if (appMap.get(sysAppEntity.getId()) == null) {
                continue;
            }
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", sysAppEntity.getId());
            map.put("parentId", 0);
            map.put("parentName", "根节点");
            map.put("text", sysAppEntity.getName());
            map.put("qtip", sysAppEntity.getName());
            map.put("orderindex", 1);
            map.put("leaf", false);
            map.put("isLeaf", 0);
            map.put("viewType", "menu" + sysAppEntity.getId());
            map.put("routeId", "menu" + sysAppEntity.getId());
            map.put("iconCls", sysAppEntity.getIconCls());
            map.put("rowCls", "nav-tree-badge nav-tree-badge-new");
            map.put("children", appMap.get(sysAppEntity.getId()));
            map.put("expanded", true);
            childList.add(map);
        }

        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", childList);

        return dataMap;
    }

}
