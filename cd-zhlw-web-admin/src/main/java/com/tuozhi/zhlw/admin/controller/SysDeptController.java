package com.tuozhi.zhlw.admin.controller;

import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.SysDeptService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.CommonUtils;
import com.tuozhi.zhlw.common.utils.CookieUtils;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ma-zy
 * @Date: 2019/9/11 17:58
 * @Description:
 */
@RestController
@RequestMapping("/dept")
@Slf4j
public class SysDeptController {


    @Autowired
    private SysDeptService sysDeptService ;
    @Autowired
    TokenManager tokenManager;


    @RequestMapping("/findAllDept")
    public Map findAllDept(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysDeptEntity> deptList = new ArrayList<SysDeptEntity>();
        List deptTree = new ArrayList<Object>();
        String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        LoginUser loginUser =  tokenManager.validate(token);
        if(null==loginUser) {
        	  return null;
        }
        boolean flag=false;
        String[] roles= loginUser.getRoleIds().split(",");
        for(int i=0;i<roles.length;i++) {
        	if("1".equals(roles[i])) {
        		flag=true;
        		break;
        	}
        }
        if(flag) {
        	deptList = sysDeptService.findAllDeptList();
        	deptTree = sysDeptService.createDeptTree(0L, deptList, true, null);
        }else {
        	deptList = sysDeptService.getAllChildIdsById(loginUser.getDeptId());
        	deptTree = sysDeptService.createDeptTree(loginUser.getDeptId(), deptList, true, null);
        }
       
        map.put("expanded", true);
        map.put("children", deptTree);
        return map;
    }

    @RequestMapping(value = "/saveDept" ,method = RequestMethod.POST,params = "flag!=update")
    public ResultMsg saveDept(@Validated SysDeptEntity dept, HttpServletRequest request) {
        String privilegeid = request.getParameter("PRIVILEGEID");
        dept.setPrivilegeId(privilegeid);
        try {
            sysDeptService.saveNotNull(dept);
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
    }

    @RequestMapping(value = "/saveDept" ,method = RequestMethod.POST,params = "flag=update")
    public ResultMsg update(HttpServletRequest request, @Validated SysDeptEntity dept) {
        if (dept.getDeptId() == null) {
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
         dept.setId(dept.getDeptId());
        try {
            sysDeptService.updateNotNull(dept);
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(),e);
            return ResultMsgUtil.isError(ResultMsgEnum.UPDATE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK);
    }

    @RequestMapping("/delDeptByDeptId")
    public ResultMsg delDeptByDeptId( @RequestParam("deptId") Long deptId) {
        if (deptId == null) {
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }

        try {
            sysDeptService.deleteByKey(deptId);
        } catch (Exception e) {
            log.error(ResultMsgEnum.DELETE_ERROR.getMsg(),e);
            return ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK);
    }
    
    @RequestMapping("/DeptTreeCheckedByList")
    public Map DeptTreeCheckedByList(HttpServletRequest request, HttpServletResponse response) {
    	
    	String arrstr = request.getParameter("arrstr");
    	System.out.println("arrstr"+arrstr);
    	List<SysDeptEntity> deptList = sysDeptService.findAllDeptList();
    	List deptTree=null;
    	if("".equals(arrstr)||arrstr==null) {
    		deptTree = sysDeptService.DeptTreeChecked(0L, deptList, true, null);
    	}else {
    		
        	String[] arr=arrstr.split(",");
        	System.err.println("arr"+arr);
        	deptTree = sysDeptService.DeptTreeCheckedByList(0L, deptList, true, null,arr);
    	}
    	
        Map<String, Object> map = new HashMap<String, Object>();

        map.put("expanded", true);
        map.put("children", deptTree);
        return map;
    }
    @RequestMapping("/findAllDeptChecked")
    public Map findAllDeptChecked(HttpServletRequest request, HttpServletResponse response) {
    	
    	
        Map<String, Object> map = new HashMap<String, Object>();
        List<SysDeptEntity> deptList = sysDeptService.findAllDeptList();
        List deptTree = sysDeptService.DeptTreeChecked(0L, deptList, true, null);
       
        map.put("expanded", true);
        map.put("children", deptTree);
        return map;
    }
    
    

    /**
     * 校验deptWork是否存在
     *
     * @param deptWork
     * @return
     */
    @GetMapping("/verifyExist")
    public ResultMsg verifyExist(@RequestParam("deptWork") String deptWork) {
        ResultMsg result = null;
        try {
            result = sysDeptService.verifyExist(deptWork);
        } catch (Exception e) {
            return CommonUtils.systemErrorDispose(e, result);
        }
        return result;
    }

}
