package com.tuozhi.zhlw.admin.controller;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.pojo.SysMenu;
import com.tuozhi.zhlw.admin.service.SysMenuService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import com.tuozhi.zhlw.common.utils.TzBeanUtils;
import com.tuozhi.zhlw.common.valid.UpdateValid;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
@Slf4j
public class SysMenuController extends BaseController {

    @Autowired
    SysMenuService sysMenuService;
    @Autowired
	private RedisTemplate redisTemplate;
    @Autowired
    TokenManager tokenManager;

    final int IS_AVAILABLE = 1;

    @RequestMapping("/getMenuTree")
    public Map<String, Object> getMenuTree(HttpServletRequest request) {
        String appId = request.getParameter("appId");
        String checkFlag = request.getParameter("checkFlag");
        if (checkFlag == null) {
            checkFlag = "";
        }

        Long appIdLong = null;
        if (!StringUtils.isEmpty(appId)) {
            appIdLong = Long.valueOf(appId);
        }
        List<SysMenu> allMenu = sysMenuService.findAllMenuByAppId(appIdLong);
        List menuTreeNode = sysMenuService.createMenuTreeNode("0", allMenu, checkFlag,true);

        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("success", true);
        dataMap.put("expanded", true);
        dataMap.put("children", menuTreeNode);
        return dataMap;
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = {"flag=add"})
    public ResultMsg save(SysMenuEntity menu, HttpServletRequest request) {

        try {
            menu.setValidStatus(IS_AVAILABLE);
            LoginUser loginUser = getLoginUser(request);

            String[]  roleIds =  loginUser.getRoleIds().split(",");

            sysMenuService.saveMenuAndRolesMenu(menu,roleIds);
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK, menu.getFunctionCode());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, params = {"flag=update"})
    public ResultMsg update(@Validated({UpdateValid.class}) SysMenuEntity menu,
                            @RequestParam("functionCode") Long functionCode) {
        SysMenuEntity target = sysMenuService.getEntityByFunctionCode(functionCode);
        TzBeanUtils.copyNotNullProperties(menu, target);
        try {
            sysMenuService.updateNotNull(target);
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(), e);
            ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK);
    }

    @RequestMapping("/delete")
    public ResultMsg deleteMenu(HttpServletRequest request) {

        String functionCode = request.getParameter("functionCode");
        try {
            sysMenuService.deleteMenuByFunction(functionCode);
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK, functionCode);
    }


    /**
     * 查询非叶节点的菜单
     *
     * @param req
     * @param res
     * @return
     */
    @RequestMapping("/getSysMenuTreeIsNotLeafNode")
    public Map<String, Object> getSysMenuTreeIsNotLeafNode(HttpServletRequest req, HttpServletResponse res) {
        Map<String, Object> map = new HashMap<String, Object>();
        String checkFlag = "";
        checkFlag = req.getParameter("checkFlag");
        String appIdString = req.getParameter("appId");
        if (checkFlag == null) {
            checkFlag = "";
        }
        Long appId = null;
        if (!StringUtils.isEmpty(appIdString)) {
            appId = Long.valueOf(appIdString);
        }

        List nodeList = sysMenuService.getSysMenuTreeIsNotLeafNode(appId);

        List menuTreeNodeList = sysMenuService.createMenuTreeNode("0", nodeList, checkFlag,true);
        if (!CollectionUtils.isEmpty(nodeList)) {
            map.put("expanded", true);
            map.put("children", menuTreeNodeList);
            return map;
        }
        map.put("success", false);
        return map;
    }

    @RequestMapping("/sysPreMenuList")
    @ResponseBody
    public Map<String, Object> sysPreMenuList(HttpServletRequest req,
    		@ModelAttribute QueryParams queryParams) throws JsonParseException, JsonMappingException, IOException, SQLException {
        Map<String, Object> map = new HashMap<String, Object>();
        // 获取用户的相关ROLE_FUNCTIONIDS(功能编号)
        // HttpSession session = req.getSession();
        //Integer userid = Integer.parseInt(this.getUserId());

        // CdError_ObjectList<Object[]>
        // list=sysMenuService.getMenuTreeByUserId(userid.intValue());
       /* Set keys = redisTemplate.keys("ACCESS_TOKEN*");
		String key ="";
		Iterator<String> it1 = keys.iterator();
		while (it1.hasNext()) {
		  key=it1.next();
		}
		LoginUserForRedis loginUserForRedis = new ObjectMapper().readValue((String)redisTemplate.opsForValue().get(key),LoginUserForRedis.class);
		*/
       LoginUser loginUserForRedis=getLoginUser(req);
		Long userID=loginUserForRedis.getUserId();
        String string=req.getParameter("FUNCTIONID");
		String FUNCTIONNAME=req.getParameter("FUNCTIONNAME");
		if (StringUtils.isNotEmpty(string)) {
			Integer FUNCTIONID= Integer.parseInt(string);
			map.put("FUNCTIONID", FUNCTIONID);
		}
		if (StringUtils.isNotEmpty(FUNCTIONNAME)) {
			map.put("FUNCTIONNAME",FUNCTIONNAME);
		}
		if (userID!=null) {
			map.put("userID",userID);
		}

		/*List<Map<String,Object>> all = sysMenuService.selectPredefineMenu(map, queryParams);
		JSONArray array= JSON.parseArray(JSON.toJSONString(all));
		if(all) {
			
		}*/
       /* List<Map<String, Object>> predefineMenuList = sysMenuService.getPredefineMenuTreeByUserId(1L);
        String fram = req.getParameter("fram");
        if (fram == null) {
            fram = "";
        }*/
		List<Map<String, Object>> predefineMenuList = sysMenuService.getPredefineMenuTreeByUserId(map);
        String fram = req.getParameter("fram");
        if (fram == null) {
            fram = "";
        }
        List<Object> objList = sysMenuService.getSysMenuTreeCrecursion((long) 0, predefineMenuList, fram);
        if (objList.size() > 0) {
            map.put("expanded", false);
            map.put("expanderOnly", true);
            map.put("children", objList);
            return map;
        }
        map.put("success", false);
        return map;
    }

    /**
     * 根据菜单页面查修相关功能按钮
     */
    @RequestMapping("/selectMenuIsvalId")
    @ResponseBody
    public ResultExtGrid selectMenuIsvalId(Long functionId) {
        List<Map<String, Object>> menuList;
        try {
            menuList = sysMenuService.selectMenuIsvalId(functionId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
            return ResultExtGridUtil.isError(ResultMsgEnum.QUERY_ERROR);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.QUERY_OK, menuList, (long) menuList.size());
    }

    /**
     * 菜单添加时，服务端编号验重
     * @param functionCode 输入的编号
     * @return 包含了输入编号是否已存在的消息体，存在返回true，不存在返回false
     */
    @RequestMapping(value = "/queryMenuRepeat",method = RequestMethod.POST)
    public ResultMsg queryMenuFunctionRepeat(@Param("functionCode") String functionCode){
        try {
            if (this.sysMenuService.queryMenuRepeat(functionCode))
                return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS) ;
            else
                return ResultMsgUtil.isError(ResultMsgEnum.SUCCESS);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isError(ResultMsgEnum.ERROR) ;
    }

}
