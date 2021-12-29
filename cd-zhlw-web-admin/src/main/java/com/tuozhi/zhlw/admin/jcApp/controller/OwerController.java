package com.tuozhi.zhlw.admin.jcApp.controller;

import com.alibaba.fastjson.JSONObject;
import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.jc.service.BlacklistRequestQueryService;
import com.tuozhi.zhlw.admin.jcApp.service.JCAppETCCardService;
import com.tuozhi.zhlw.admin.jcApp.service.OwerService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
@Slf4j
@RequestMapping("/JC/APP")
public class OwerController extends BaseController {
    @Resource
    private LoginService loginService;

    @Resource
    private OwerService owerService;

    @Resource
    private JCAppETCCardService jcAppETCCardService;

    @Resource
    BlacklistRequestQueryService blacklistRequestQueryService;

    /**
     * 获取当前登录用户已添加的撤销黑名单信息
     *
     * @param loginName
     * @return
     */
    @RequestMapping("/getJCAppBlackAddOrBlackRevoked")
    public ResultMsg getJCAppBlackAddOrBlackRevoked(@RequestParam("loginName") String loginName, @Param("logicType") Integer logicType) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        List<Map<String, Object>> jcAppBlackRevoked = owerService.getJCAppBlackAddOrBlackRevoked(loginUser.getUserId(), logicType);
        result.setMessage("查询成功");
        result.setSuccess(true);
        result.setData(jcAppBlackRevoked);
        return result;
    }

    /**
     * 获取ETC发行信息(上报信息)（JCAPP添加）
     *
     * @param loginName
     * @return
     */
    @RequestMapping("/getJCAppETCCard")
    public ResultMsg getJCAppETCCard(@RequestParam(value = "loginName") String loginName) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        List etcCardBlackList = jcAppETCCardService.getJCAppETCCard(loginUser.getUserId());//获取ETC发行信息
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(etcCardBlackList);
        return result;
    }

    /**
     * 根据黑名单（撤销）请求编号查询附件
     *
     * @param requestId
     * @return
     */
    @RequestMapping("/getBlackListAddOrblackOrdersRevokedAttachment")
    public ResultMsg getBlackListAddOrblackOrdersRevokedAttachment(@RequestParam("requestId") Long requestId, @RequestParam(value = "isApp", required = false) String isApp) {
        ResultMsg result = new ResultMsg();
        List blackListAddOrblackOrdersRevokedAttachment = null;
        try {
            blackListAddOrblackOrdersRevokedAttachment = owerService.getBlackListAddOrblackOrdersRevokedAttachment(requestId, isApp);
            result.setSuccess(true);
            result.setData(blackListAddOrblackOrdersRevokedAttachment);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @param etcParams (或车牌，或ETC卡号，或OBU号)
     * @return
     * @author wwx
     * @desc 获取ETC卡黑名单(暂定模糊查询)
     * @data 2020-11-16
     */
    @RequestMapping("/findEtcBlackList")
    public ResultMsg findEtcBlackList(@RequestParam(value = "etcParams",required = false) String etcParams) {
        ResultMsg result = new ResultMsg();
        List<Map<String,Object>> etcBlackList = owerService.findEtcBlackList(etcParams);
        result.setSuccess(true);
        result.setData(etcBlackList);
        return result;
    }

    /**
     * 查询该请求编号为sRequestId名单的车辆是否已撤销追缴黑名单
     *
     * @param jsonStr
     * @return
     */
    @RequestMapping("/getBlackListBySrequestId")
    public ResultMsg getBlackListBySrequestId(@RequestParam("jsonStr") String jsonStr) {
        ResultMsg result = new ResultMsg();
        JSONObject jsonobject = JSONObject.parseObject(jsonStr);
        Map<String, Object> blackListBySrequestId = blacklistRequestQueryService.getBlackListBySrequestId(jsonobject.getLong("sRequestId").longValue());
        if (ObjectUtils.isEmpty(blackListBySrequestId)) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setData(blackListBySrequestId);
        }
        return result;
    }


    /**
     * 该Controller异常解析方法
     * @param e
     * @return
     */
    @ExceptionHandler
    public ResultMsg exceptionMethod(Exception e) {
        log.info(e.getMessage());
        //e.printStackTrace();
        ResultMsg result = new ResultMsg();
        result.setSuccess(false);
        result.setMessage("查询失败");
        return result;
    }
}
