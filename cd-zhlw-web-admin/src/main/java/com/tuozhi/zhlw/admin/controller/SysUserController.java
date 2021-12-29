package com.tuozhi.zhlw.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.admin.manager.TokenManager;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.pojo.SessionUtils;
import com.tuozhi.zhlw.admin.service.SysDeptService;
import com.tuozhi.zhlw.admin.service.SysUserService;
import com.tuozhi.zhlw.common.annotation.SysLog;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.CookieUtils;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import com.tuozhi.zhlw.common.utils.SHAUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author linqi
 * @create 2019/09/06 22:00
 **/

@RestController
@RequestMapping("/user")
@Slf4j
public class SysUserController extends BaseController{

    @Autowired
    SysUserService sysUserService;
    
    @Autowired
    private SysDeptService sysDeptService ;

    @RequestMapping("/findAll")
    @SysLog(value = "查询所有用户")
    public ResultExtGrid findAll(HttpServletRequest request, QueryParams queryParams) {
        String userName = request.getParameter("orfieldvalue");
        String muchUserName = request.getParameter("username");
        // 点击自定义查询中的‘查询统计’按钮之后 querymodel赋值为"dialog"
        String queryModel = request.getParameter("querymodel") ;
        String realUsername = "dialog".equals(queryModel) ? muchUserName : userName ;
        
        PageInfo<SysUserEntity> all = null; 
        String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        LoginUser loginUser =  tokenManager.validate(token);
        if(null==loginUser) {
        	  return null;
        }
        List<SysDeptEntity> depts=sysDeptService.getAllChildIdsById(loginUser.getDeptId());
        List<Long> deptIds=new ArrayList<Long>();
        boolean flag=false;
        String[] roles= loginUser.getRoleIds().split(",");
        for(int i=0;i<roles.length;i++) {
        	if("1".equals(roles[i])) {
        		flag=true;
        		break;
        	}
        }
        
        if(flag) {
        	all = sysUserService.findAll(queryParams,realUsername);
        }else {
        	for(SysDeptEntity e : depts) {
        		deptIds.add(e.getId());
        	}
        	all = sysUserService.findAllAndDeptId(queryParams,realUsername,deptIds);
        }
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, all.getList(), all.getTotal());
    }


    /**
     * 保存用户(初始化密码)
     */
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST,params = {"flag=add"})
    @ResponseBody
    public ResultMsg InsertOrUpdateUserInfoManage(@Valid SysUserEntity user) throws Exception {
        SysUserEntity newUser = new SysUserEntity();
        newUser.setLoginName(user.getLoginName());
        newUser.setUserName(user.getUserName());
        String newPassword = SHAUtil.shaEncode(user.getPassword());
        newUser.setPassword(newPassword);
        newUser.setDeptId(user.getDeptId());
        newUser.setValidStatus(1);
        newUser.setLastPasswordModifyTime(new Date());
        try {
            sysUserService.saveSysUserEntity(newUser) ;
        } catch (Exception e) {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR) ;
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK) ;
    }


    /**
     * 保存用户(初始化密码)
     */
    @RequestMapping(value = "/insertUser",method = RequestMethod.POST,params = {"flag=update"})
    @ResponseBody
    public ResultMsg UpdateUserInfoManage(HttpServletRequest request, @Valid SysUserEntity user) throws Exception {

        try {
            user.setPassword(SHAUtil.shaEncode(user.getPassword()));
            user.setLastPasswordModifyTime(new Date());
            sysUserService.updateNotNull(user) ;
            
            LoginUser loginUser = getLoginUser(request);
            if(loginUser.getUserId().equals(user.getId())) {
            	String token = CookieUtils.getCookie(request, TokenManager.TOKEN);
                if (!StringUtils.isEmpty(token)) {
                    tokenManager.remove(token);
                }
                SessionUtils.invalidate(request);
            }
        } catch (Exception e) {
            log.error(ResultMsgEnum.UPDATE_ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.UPDATE_ERROR) ;
        }
            return ResultMsgUtil.isSuccess(ResultMsgEnum.UPDATE_OK) ;
    }



    /**
     *
     * 删除用户
     */
    @RequestMapping(value = "/deleteUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public ResultMsg delUserInfo(@RequestParam Long userId) {
        ResultMsg resultMsg = null ;
            if (sysUserService.deleteUserByMark(userId.toString())){
                resultMsg = ResultMsgUtil.isSuccess(ResultMsgEnum.DELETE_OK) ;
            }else {
                resultMsg = ResultMsgUtil.isError(ResultMsgEnum.DELETE_ERROR) ;
            }
        return resultMsg ;

    }


    /**
     * 人员添加或修改时，服务端用户名验重
     * @param userName 输入的用户名
     * @return 包含了用户名是否已存在的消息体，存在返回true，不存在返回false
     */
    @RequestMapping(value = "/queryUserRepeat",method = RequestMethod.POST,params = {"flag=userName"})
    public ResultMsg queryUserNameRepeat(@Param("userName") String userName){
        try {
            if (this.sysUserService.checkRepeatUserName(userName))
                return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS) ;
            else
                return ResultMsgUtil.isError(ResultMsgEnum.SUCCESS);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isError(ResultMsgEnum.ERROR) ;
    }

    /**
     * 人员添加或修改时，服务端登录名验重
     * @param loginName 输入的登录名
     * @return 包含了用户名是否已存在的消息体，存在返回true，不存在返回false
     */
    @RequestMapping(value = "/queryUserRepeat",method = RequestMethod.POST,params = {"flag=loginName"})
    public ResultMsg queryLoginNameRepeat(@Param("loginName") String loginName){
        try {
            if (this.sysUserService.checkRepeatLoginName(loginName))
                return ResultMsgUtil.isSuccess(ResultMsgEnum.SUCCESS) ;
            else
                return ResultMsgUtil.isError(ResultMsgEnum.SUCCESS);
        } catch (Exception e) {
            log.error(ResultMsgEnum.QUERY_ERROR.getMsg(), e);
        }
        return ResultMsgUtil.isError(ResultMsgEnum.ERROR) ;
    }


    /**
     * 根据输入密码和旧密码验证
     */
    @SuppressWarnings("all")
    @RequestMapping(value = "/checkResetPassWord", method = RequestMethod.POST)
    @ResponseBody
    @SysLog(value = "密码修改")
    public ResultMsg checkResetPassWord(@Valid SysUserEntity user, HttpSession session, HttpServletRequest req)
            throws Exception {

        String newPassOne = req.getParameter("newPassOne");
        String newPassTwo = req.getParameter("newPassTwo");
        String userId = req.getParameter("userId");
        if (!newPassOne.equals(newPassTwo)) {
            return ResultMsgUtil.isError(ResultMsgEnum.PASSWORD_INCONFORMITY);
        }
        String newPassWord = SHAUtil.shaEncode(newPassOne);
        // 调用工具类
        LoginUser loginUser = getLoginUser(req);

        String password = SHAUtil.shaEncode(user.getPassword());

        SysUserEntity sysUserEntity = null;
        if (loginUser == null && StringUtils.isNotEmpty(userId)) {
            sysUserEntity = sysUserService.selectByKey(Long.valueOf(userId));
        } else {
            sysUserEntity = sysUserService.selectByKey(loginUser.getUserId());
        }

        if (sysUserEntity == null) {
            return ResultMsgUtil.isError(ResultMsgEnum.PASSWORD_ERROR);
        }
        if (!sysUserEntity.getPassword().equals(password)) {

            return ResultMsgUtil.isError(ResultMsgEnum.PASSWORD_ERROR);
        }

        sysUserEntity.setPassword(newPassWord);
        sysUserEntity.setLastPasswordModifyTime(new Date());
        String passwordHistory = sysUserEntity.getPasswordHistory();
        if (StringUtils.isBlank(passwordHistory)) {
            sysUserEntity.setPasswordHistory(newPassWord);
        } else {
            String[] passwordHistorys = passwordHistory.split("\\|");
            if (passwordHistory.contains(newPassWord)) {
                return ResultMsgUtil.isError(ResultMsgEnum.PASSWORD_CHECK_1) ;
            }
            if (passwordHistorys.length < 6) {
                passwordHistory = passwordHistory + "|" + newPassWord;
            } else {
                String substring = passwordHistory.substring(passwordHistory.indexOf("|"));
                passwordHistory = substring + "|" + password;
            }
            sysUserEntity.setPasswordHistory(passwordHistory);
        }

        sysUserService.updateAll(sysUserEntity);

        return ResultMsgUtil.isSuccess(ResultMsgEnum.PASSWORD_EDITOR_SUCCESS) ;

    }


}
