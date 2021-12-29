package com.tuozhi.zhlw.admin.jcApp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuozhi.zhlw.admin.controller.BaseController;
import com.tuozhi.zhlw.admin.jc.entity.*;
import com.tuozhi.zhlw.admin.jc.service.*;
import com.tuozhi.zhlw.admin.jc.util.JCOperationLogUtil;
import com.tuozhi.zhlw.admin.jcApp.service.JCAuditStatusService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.admin.service.LoginService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.CommonUtils;
import com.tuozhi.zhlw.common.utils.ResultMsgUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.util.StringUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Slf4j
@RequestMapping("/JC/APP")
/**
 * 稽查App 稽核状态车辆查询
 */
public class JCAuditStatusController extends BaseController {
    @Resource
    JCAuditStatusService service;
    @Resource
    EtcCardBlackListService etcCardBlackListService;
    @Resource
    private ExitPassDataService exitPassDataService;
    @Resource
    private LoginService loginService;
    @Resource
    private BlacklistRequestQueryService blacklistRequestQueryService;
    @Resource
    private BlackListRequestService blackListRequestService;
    @Resource
    private JCOperationLogUtil jcOperationLogUtil;
    @Resource
    private BlackListRequestService blacklistrequestService;
    @Resource
    private JcLogService jcLogService;


    private final static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd 00:00:00");

    /**
     * JCAPP首页查询黑灰名单，查询当天（默认）
     *
     * @param vehicle
     * @return
     * @throws ParseException
     */
    @RequestMapping("/getBlackOrGreyListByVehicle")
    public ResultMsg getBlackListByVehicle(@RequestParam(value = "vehicle", required = false) String vehicle) throws ParseException {
        Date nowDate = null;
        if (StringUtil.isEmpty(vehicle)) {
            nowDate = sdf1.parse(sdf1.format(new Date()));
        }
        ResultMsg result = new ResultMsg();
        Map mapData = new HashMap();
        List<Map<String, Object>> localBlackList = service.getLocalBlackList(vehicle, nowDate);//查询本地黑名单详情信息
        List<Map<String, Object>> localGreyList = service.getLocalGreyList(vehicle, nowDate);//查询本地关注名单详情信息
        mapData.put("localBlackList", localBlackList);
        mapData.put("localGreyList", localGreyList);
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(mapData);
        return result;
    }

    @RequestMapping("/getCountByVehicle")
    public ResultMsg getCountByVehicle(HttpServletRequest request, @RequestParam(value = "flag", required = false) String flag) {
        ResultMsg result = new ResultMsg();
        String carNumber = request.getParameter("carNumber");
        String carColour = request.getParameter("carColour");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNoneEmpty(carNumber)) {
            map.put("carNumber", carNumber);
        }
        if (StringUtils.isNoneEmpty(carColour)) {
            map.put("carColour", carColour);
        }
        if (StringUtils.isNoneEmpty(flag)) {
            map.put("flag", flag);
        }
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -6);
        map.put("oldDate", sdf.format(c.getTime()));
        Map<String, Object> map1 = blacklistrequestService.getCountByVehicle(map);//获取用户名与创建时间 用于重复添加撤销黑名单判断
        if (!ObjectUtils.isEmpty(map1)) {
            result.setData(map1);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * @param file
     * @return
     * @desc JCAPP新增稽查追缴黑名单
     */

    @RequestMapping("/insertJCBlackListRequest/{loginName}")
    public ResultMsg insertJCBlackListRequest(MultipartFile[] file, HttpServletRequest request, @PathVariable String loginName) {
        ResultMsg result = new ResultMsg();
        String blackListJsonMap = request.getParameter("jsonStr");
        // 调用工具类
        try {
            LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
            BlackListRequest blackListRequest = new ObjectMapper().readValue(blackListJsonMap, BlackListRequest.class);
            blackListRequest.setIsApp(1);
            result = blacklistRequestQueryService.saveBlackListAddRequest(blackListRequest, loginUser, file);
            if (result.isSuccess()) {
                JcOperationLogEntity jcOperationLogEntity = new JcOperationLogEntity(blackListRequest.getCarNumber(), blackListRequest.getCarColour(), blackListRequest.getCarType(), blackListRequest.getEludeMoneyType().toString(), blackListRequest.getEludeMoneyTypeItem().toString(), 2);
                jcOperationLogUtil.addJcOperationLogOfJCAPP(jcOperationLogEntity, loginUser);
            }
            return result;
        } catch (Exception e) {
            return CommonUtils.systemErrorDispose(e, result);
        }
    }

    /**
     * @param jsonStr
     * @param loginName
     * @return
     * @desc JCAPP新增稽查撤销追缴黑名单
     */
    @RequestMapping("/insertJCBlackOrdersRevoked/{loginName}")
    public ResultMsg insertJCBlackOrdersRevoked(String jsonStr, MultipartFile[] file, @PathVariable String loginName) {
        ResultMsg result = new ResultMsg();
        // 调用工具类
        try {
            LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
            BlackListRequest blackListRequest = new ObjectMapper().readValue(jsonStr, BlackListRequest.class);
            Long sRequestId = blackListRequest.getSRequestId();//获取黑名单编号，去查询黑名单数据
            BlackListRequest blacklistRequest1 = (BlackListRequest) blacklistRequestQueryService.getBlacklistRequest(sRequestId).getData();
            blackListRequest.setCarType(blacklistRequest1.getCarType());//车型
            blackListRequest.setEntryStationId(blacklistRequest1.getEntryStationId());//入口站编号
            blackListRequest.setExitStationId(blacklistRequest1.getExitStationId());//出口站编号
            blackListRequest.setEludeMoneyType(blacklistRequest1.getEludeMoneyType());//逃费类型
            blackListRequest.setEludeMoneyTypeItem(blacklistRequest1.getEludeMoneyTypeItem());//逃费种类
            // blackListRequest.setRequestDescription(blacklistRequest1.getRequestDescription());//描述
            blackListRequest.setIsApp(1);
            result = blackListRequestService.addBlackRevokedAndUpdateRoadOrCardBlackList(blackListRequest, "黑名单撤销申请", loginUser, "add", file);
            result.setSuccess(true);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("新增失败");
        }
        return result;
    }

    /**
     * 查询ETC发行信息
     *
     * @param vehicleOrCardId （车牌号或ETC卡号）
     * @return
     * @date 2020/07/13
     */
    @RequestMapping("/getEtcCardBlackList")
    public ResultMsg getEtcCardBlackList(@RequestParam(value = "vehicleOrCardId") String vehicleOrCardId) {
        ResultMsg result = new ResultMsg();
        Map selectMap = new HashMap();
        selectMap.put("vehicleOrCardId", vehicleOrCardId);
        List etcCardBlackList = etcCardBlackListService.findAll(selectMap);//获取ETC发行信息
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(etcCardBlackList);
        return result;
    }

    /**
     * 稽查app补证接口
     * json 实例
     * {
     * "requestId": "91009",                             关注名单（或追缴名单）的名单编号
     * "type": "1",                                     图片以type分类 1.本地灰名单，2本地黑名单
     * "data": [
     * {
     * "attachment": "11111111111111111111111",    文件的base64字符串
     * "fileType": "1"                             文件类型 1图片，2视频
     * },
     * {
     * "attachment": "222222222222222222222222",
     * "fileType": "2"
     * },
     * {
     * "attachment": "3333333333333333333333333",
     * "fileType": "1"
     * }
     * ]
     * }
     */
    //暂时不用
    @RequestMapping("/insertJCAppAttachment")
    public ResultMsg insertJCAppAttachment(@RequestParam("allParams") String allParams) {
        JSONObject object = JSONObject.parseObject(allParams);
        String requestId = object.getString("requestId");
        String type = object.getString("type");
        JSONArray array = (JSONArray) object.get("data");
        ResultMsg result = service.insertJCAppAttachment(requestId, type, array);
        return result;
    }

    //补证
    @RequestMapping("/insertJCAppView/{loginName}")
    public ResultMsg insertJCAppViw(MultipartFile[] file, @RequestParam("jsonStr") String jsonStr, @PathVariable String loginName) {
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        ResultMsg result = service.insertJCAppViw(file, jsonStr, loginUser);
        return result;
    }

    /**
     * 根据requestId查询附件
     *
     * @param requestId
     * @return
     */
    @RequestMapping("/getBasePathByRequestId")
    public ResultMsg getBasePathByRequestId(@RequestParam("requestId") Long requestId) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> basePathByRequestId = service.getBasePathByRequestId(requestId);
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(basePathByRequestId);
        return result;
    }

    /**
     * 查询黑名单补费流水证据表的补费信息
     *
     * @param required 黑名单请求编号
     * @return
     */
    @RequestMapping("/getJCBlacklistEvidenceDetails")
    public ResultMsg getJCBlacklistEvidenceDetails(@RequestParam(value = "required", required = false) String required) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> jcBlacklistEvidenceDetails;
        try {
            jcBlacklistEvidenceDetails = service.getJCBlacklistEvidenceDetails(required);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            result.setData(e);
            return result;
        }
        result.setSuccess(true);
        result.setMessage("查询成功");
        result.setData(jcBlacklistEvidenceDetails);
        return result;
    }

    /**
     * @param requestId
     * @return
     * @desc 稽查app补费接口
     */
    @RequestMapping("/updateJCBlacklistEvidenceDetailsBySid")
    public ResultMsg updateJCBlacklistEvidenceDetailsBySid(@RequestParam("requestId") String requestId) {
        ResultMsg result = service.updateJCBlacklistEvidenceDetailsBySid(requestId);
        return result;
    }

    /**
     * 获取出口/入口通行数据
     *  flag    1:出口通行 2 入口通行
     * @param request
     * @return
     */
    @RequestMapping("/getExitPassData")
    public ResultMsg getExitPassData(HttpServletRequest request) {
        ResultMsg result = new ResultMsg();
        Map<String, Object> conditions = new HashMap<String, Object>();
        try {
            String flag = request.getParameter("flag");//1出口，2入口
            String carNumber = request.getParameter("carNumber");//车牌号码
            String carColor = request.getParameter("carColor");//车牌颜色
            String startDate = request.getParameter("startDate");//起始日期
            String endDate = request.getParameter("endDate");//结束日期
            if (StringUtils.isNotEmpty(carNumber)) {
                conditions.put("carNumber", carNumber);
            }
            if (StringUtils.isNotEmpty(carColor)) {
                conditions.put("carColor", carColor);
            }
            if (StringUtils.isNotEmpty(startDate)) {
                conditions.put("startDate", sdf.parse(startDate));
            }
            if (StringUtils.isNotEmpty(endDate)) {
                conditions.put("endDate", sdf.parse(endDate));
            }
            if(flag.equals("1")) {//出口
                List<TradePassEntity> tradePassEntity = exitPassDataService.getTradePassByParams(conditions);
                result.setData(tradePassEntity);
            }else if (flag.equals("2")){//入口
                List<EtctsEntrypassdata> etctsEntrypassdataInfo = service.getEtctsEntrypassdataInfo(conditions);
                result.setData(etctsEntrypassdataInfo);
            }
            result.setSuccess(true);
            result.setMessage("查询成功");

        } catch (ParseException e) {
            log.info(e.getMessage());
            result.setSuccess(false);
            result.setMessage("查询失败");
        }
        return result;
    }


    /**
     * @param vehicle
     * @param vehicleColor
     * @param file
     * @return
     * @date 2020-12-24
     * @desc ETC发行稽查附件新增
     */
    @RequestMapping(value = "/addOperationLogFiles")
    public ResultMsg addOperationLogFiles(@RequestParam("vehicle") String vehicle, @RequestParam("vehicleColor") Integer vehicleColor,
                                          @RequestParam("loginName") String loginName,
                                          @RequestParam("file") MultipartFile[] file) {
        LoginUser loginUser = loginService.findBaseUserByLoginName(loginName);
        try {
            jcLogService.addOperationLogFiles(vehicle, vehicleColor, file, loginUser);
        } catch (Exception e) {
            log.error(ResultMsgEnum.ERROR.getMsg(), e);
            return ResultMsgUtil.isError(ResultMsgEnum.SAVE_ERROR);
        }
        return ResultMsgUtil.isSuccess(ResultMsgEnum.SAVE_OK);
    }

    /**
     * @param vehicle
     * @param vehicleColor
     * @return
     * @date 2020-12-24
     * @desc ETC发行稽查附件查询
     */
    @RequestMapping("/getETCOperationLogFiles")
    public ResultMsg getETCOperationLogFiles(@RequestParam("vehicle") String vehicle, @RequestParam(value = "vehicleColor", required = false) Integer vehicleColor) {
        ResultMsg result = new ResultMsg();
        List<JCOperationlogFiles> etcOperationLogFiles = jcLogService.getETCOperationLogFiles(vehicle, vehicleColor);
        result.setSuccess(true);
        result.setData(etcOperationLogFiles);
        return result;
    }

    /**
     * @param attachmentId
     * @return
     * @date 2020-12-24
     * 删除发行信息中车辆档案中的附件
     */
    @RequestMapping("/delOperationLogFiles")
    public ResultMsg delOperationLogFiles(@RequestParam("attachmentId") Long attachmentId) {
        ResultMsg result = new ResultMsg();
        try {
            jcLogService.delOperationLogFiles(attachmentId);
            result.setSuccess(true);
            result.setMessage("删除成功");
        } catch (Exception e) {
            log.error(e.getMessage());
            result.setSuccess(false);
            result.setMessage("删除失败");
        }
        return result;
    }


}
