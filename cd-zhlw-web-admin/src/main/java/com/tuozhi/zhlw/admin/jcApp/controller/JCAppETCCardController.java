package com.tuozhi.zhlw.admin.jcApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.jc.service.EtcCardBlackListService;
import com.tuozhi.zhlw.admin.jcApp.entity.JCAppLabelTypeEntity;
import com.tuozhi.zhlw.admin.jcApp.service.JCAppETCCardService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/JC/APP")
public class JCAppETCCardController extends BaseController {
    @Autowired
    JCAppETCCardService service;
    @Autowired
    private EtcCardBlackListService etcCardBlackListService;
    @Autowired
    private LoginService loginService;

    /**
     * 根据车牌号查询ETC发行数据（暂时不用）
     *
     * @param vehicle（车牌号）
     */
    @RequestMapping(value = "/getJCAppETCCardByVehicle")
    public ResultMsg getJCAppETCCardByVehicle(@RequestParam("vehicle") String vehicle) {
        Map<String, Object> selectMap = new HashMap<String, Object>();
        ResultMsg result = new ResultMsg();
        selectMap.put("vehicleId", vehicle);
        List<Map<String, Object>> all = etcCardBlackListService.findAll(selectMap);
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(all);
        return result;
    }

    /**
     * jcAPP 发行信息变更审核入库
     *
     * @param archiveFile
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/addJCAppETCCard", method = RequestMethod.POST)
    public ResultMsg addJCAppETCCard(@RequestParam("archiveFile") String archiveFile, @RequestParam("loginName") String loginName) throws IOException {
        ResultMsg result = new ResultMsg();
        ObjectMapper objectMapper = new ObjectMapper();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        Map map = objectMapper.readValue(archiveFile, Map.class);
        map.put("CREATEDEPTID", loginUser.getDeptId());
        map.put("CREATEUSERID", loginUser.getUserId());
        int i = service.addJCAppETCCard(map);
        if (i > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }

        return result;
    }

    /**
     * 稽查APP打标
     *
     * @param : labelType(打标种类)
     *          vehicle（车牌）
     *          vehicleColor（车牌颜色）
     * @return
     */
    @RequestMapping(value = "/addJCAppLabelType")
    public ResultMsg addJCAppLabelType(HttpServletRequest request, @RequestParam("loginName") String loginName) {
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        if (ObjectUtils.isEmpty(loginUser)) {
            return ResultMsgUtil.isError(ResultMsgEnum.RETURN_LOGIN);
        }
        String labelType = request.getParameter("labelType");
        String vehicle = request.getParameter("vehicle");
        // String vehicleColor = request.getParameter("vehicleColor");
        Map<String, Object> map = new HashMap();
        map.put("vehicle", vehicle);
        // map.put("vehicleColor",vehicleColor);
        map.put("labelType", labelType);
        map.put("createUserId", loginUser.getUserId());
        map.put("createDeptId", loginUser.getDeptId());
        int i = service.addJCAppLabelType(map);
        if (i > 0) {
            return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
        } else {
            log.error(ResultMsgEnum.SAVE_ERROR.getMsg());
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
    }

    /**
     * 稽查App 获取当前登录用户下所有车辆的打标信息
     *
     * @param loginName
     * @return
     */
    @RequestMapping(value = "/getJCAppLabelType")
    public ResultMsg getJCAppLabelType(@RequestParam("loginName") String loginName) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        List<JCAppLabelTypeEntity> jcAppLabelTypeList = service.getJCAppLabelType(loginUser.getUserId());
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(jcAppLabelTypeList);
        return result;
    }

    /**
     * 查询稽查部下的所有用户
     *
     * @return
     */
    @RequestMapping(value = "/getAllUser")
    public ResultMsg getAllUser() {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> allUser = service.getAllUser();
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(allUser);
        return result;
    }

    /**
     * 添加追缴黑名单接口
     * url:/blacklistRequestQuery/saveBlackListAddRequest
     * params: @ModelAttribute BlackListRequest blackListRequest, HttpServletRequest request
     */

    /**
     * 查询本登录用户发送的消息
     *
     * @param flag      1为查询本用户已发信息（未删除），2为本用户接收的信息（未删除）
     * @param loginName 登录用户名（例：100413）
     */
    @RequestMapping(value = "/getSendorAcceptMessageByUserId")
    public ResultMsg getSendorAcceptMessageByUserId(@RequestParam(value = "flag", required = false) Integer flag, @RequestParam("loginName") String loginName) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        List<Map<String, Object>> sendMessageByUserId = service.getSendorAcceptMessageByUserId(loginUser.getUserId());
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(sendMessageByUserId);
        return result;
    }

    /**
     * 查询信息详情
     *
     * @param sendUserId
     * @param acceptUserId
     * @return
     */
    @RequestMapping("/getMessageDetial")
    public ResultMsg getMessageDetial(@RequestParam("sendUserId") Long sendUserId, @RequestParam("acceptUserId") Long acceptUserId) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> sendMessageByUserId = service.getMessageDetial(sendUserId, acceptUserId);
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(sendMessageByUserId);
        return result;
    }

    /**
     * 新增用户发送消息接口
     * acceptUserId(接收消息的用户ID)
     * message(要发送的消息)
     */
    @RequestMapping(value = "/insertSendMessage")
    public ResultMsg insertSendMessage(HttpServletRequest request, @RequestParam("loginName") String loginName) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        String acceptUserId = request.getParameter("acceptUserId");
        String message = request.getParameter("message");
        Integer integer = service.insertSendMessage(loginUser.getUserId(), Long.parseLong(acceptUserId), message);
        if (integer <= 0) {
            result.setSuccess(false);
            result.setMessage("发送失败");
        } else {
            result.setSuccess(true);
            result.setMessage("发送成功");
        }
        return result;
    }

    /**
     * 根据省份名称获取全国收费站
     *
     * @param provincename 省份名称
     * @param stationname  收费站名称
     * @return
     */
    @RequestMapping("/getStationByProvincename")
    public ResultMsg getStationByProvincename(@RequestParam(value = "provincename", required = false) String provincename,
                                              @RequestParam(value = "stationname", required = false) String stationname) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> stationByProvincename = service.getStationByProvincename(provincename, stationname);
        if (stationByProvincename.size() <= 0) {
            result.setSuccess(false);
            result.setMessage("查询无结果");
        } else {
            result.setSuccess(true);
            result.setMessage("查询成功");
            result.setData(stationByProvincename);
        }
        return result;
    }

    /**
     * 根据版本号是否新增版本
     *
     * @return
     */
    @RequestMapping("/getNewVersionFilePath")
    public ResultMsg getNewVersionFilePath() {
        ResultMsg result = service.getNewVersionFilePath();
        return result;
    }
}
