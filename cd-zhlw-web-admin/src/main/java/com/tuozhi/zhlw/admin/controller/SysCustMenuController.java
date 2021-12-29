package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysCustomMenuDetailsService;
import com.tuozhi.zhlw.admin.service.SysCustomMenuService;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/custmenu")
@Slf4j
public class SysCustMenuController extends BaseController{

    @Autowired
    SysMenuService sysMenuService;

    @Autowired
    TokenManager tokenManager;

    @Autowired
    SysCustomMenuService sysCustomMenuService;

    @Autowired
    SysCustomMenuDetailsService sysCustomMenuDetailsService ;


    @RequestMapping(value = "/save", method = RequestMethod.POST, params = {"flag=add"})
    public ResultMsg save(SysCustomMenuEntity custMenu,HttpServletRequest request) {
        LoginUser loginUser = super.getLoginUser(request) ;
        custMenu.setUserId(loginUser.getUserId());
        custMenu.setCreateTime(new Date());
        custMenu.setUpdateTime(new Date());
        try{
            this.sysCustomMenuService.createNewCustomMenu(custMenu) ;
        } catch (Exception e){
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK,loginUser.getUserName());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = {"flag=update"})
    public ResultMsg update(SysCustomMenuEntity custMenu,HttpServletRequest request){
        LoginUser loginUser = super.getLoginUser(request) ;
        custMenu.setUpdateTime(new Date());
        try {
            this.sysCustomMenuService.updateCustomMenuName(custMenu) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.UPDATE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK,loginUser.getUserName());
    }


    @RequestMapping("/customMenuTree")
    public Map<String,Object> customMenuTree(HttpServletRequest request) {
        LoginUser loginUser = getLoginUser(request);
        List<SysCustomMenuEntity> customMenuList = sysCustomMenuService.findAllByUserId(loginUser.getUserId());

        List customMenuTree = sysCustomMenuService.createCustomMenuTree(customMenuList);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", customMenuTree);

        return dataMap;

    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @Transactional
    public ResultMsg deleteCustMenus(@RequestParam("id") String sysCustomMenuId, HttpServletRequest request){
        LoginUser loginUser = getLoginUser(request);
        try {
            this.sysCustomMenuDetailsService.deleteDetailsByCustMenuId(sysCustomMenuId);
            this.sysCustomMenuService.deleteCustMenu(sysCustomMenuId) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK,loginUser.getUserName()) ;
    }


}
