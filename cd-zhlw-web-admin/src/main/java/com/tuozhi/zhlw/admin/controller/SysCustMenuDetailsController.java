package com.tuozhi.zhlw.admin.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuozhi.zhlw.admin.entity.SysCustomMenuDetailsEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/custmenudetails")
@Slf4j
public class SysCustMenuDetailsController extends BaseController{

    @Autowired
    SysCustomMenuDetailsService sysCustomMenuDetailsService;

    @Autowired
    SysMenuService sysMenuService ;


    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public ResultMsg save(HttpServletRequest request) {
        String jsonData = request.getParameter("jsonData") ;
        JSONObject jsonObject = JSONObject.parseObject(jsonData) ;
        List<SysCustomMenuDetailsEntity> sysCustomMenuDetailsEntityList = new ArrayList<>() ;
        Date currDate = new Date() ;
        Map<String, SysMenu> menuMap = this.sysMenuService.getGroupMenuMapByFunctionCode() ;
        jsonObject.forEach((key,value) -> {
            JSONArray jsonArray = JSONArray.parseArray(String.valueOf(value)) ;
            jsonArray.forEach(functionCode -> {
                SysCustomMenuDetailsEntity sysCustomMenuDetailsEntity = new SysCustomMenuDetailsEntity() ;
                sysCustomMenuDetailsEntity.setCreateTime(currDate);
                sysCustomMenuDetailsEntity.setMenuId(menuMap.get(String.valueOf(functionCode)).getId());
                sysCustomMenuDetailsEntity.setCustomMenuId(Long.parseLong(key));
                sysCustomMenuDetailsEntityList.add(sysCustomMenuDetailsEntity) ;
            });
        });
        try {
            // TODO
            this.sysCustomMenuDetailsService.saveBatchCustMenuDetails(sysCustomMenuDetailsEntityList) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK) ;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    public ResultMsg remove(@RequestParam("custMenuId") Long custMenuId,
                            @RequestParam("functionCode") String functionCode,
                            HttpServletRequest request){
        LoginUser loginUser = super.getLoginUser(request) ;
        SysCustomMenuDetailsEntity sysCustomMenuDetailsEntity = new SysCustomMenuDetailsEntity() ;
        sysCustomMenuDetailsEntity.setCustomMenuId(custMenuId);
        try {
            Map<String,SysMenu> allMenusMap = this.sysMenuService.getGroupMenuMapByFunctionCode() ;
            Long menuId = allMenusMap.get(functionCode).getId() ;
            sysCustomMenuDetailsEntity.setMenuId(menuId);
            this.sysCustomMenuDetailsService.deleteSingleDetailsByPairId(sysCustomMenuDetailsEntity);
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK,loginUser.getUserName()) ;
    }


}
