package com.tuozhi.zhlw.admin.jc.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.*;
import com.tuozhi.zhlw.admin.jc.mapper.*;
import com.tuozhi.zhlw.admin.jc.service.BlackListRequestService;
import com.tuozhi.zhlw.admin.jc.service.BlackListRoadService;
import com.tuozhi.zhlw.admin.jc.service.JcEtcCardBlackListService;
import com.tuozhi.zhlw.admin.jc.util.JCOperationLogUtil;
import com.tuozhi.zhlw.admin.mapper.UserMapper;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.utils.CommonUtils;
import com.tuozhi.zhlw.common.utils.DateFormat;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tuozhi.zhlw.admin.jc.util.JcProperties.gxName;
import static com.tuozhi.zhlw.admin.jcApp.util.getBase64String;


@Slf4j
@Service
@EnableAspectJAutoProxy(proxyTargetClass = true,exposeProxy = true)
public class BlackListRequestServiceImpl implements BlackListRequestService {

    @Resource
    private BlackListRequestMapper blacklistrequestmapper;
    @Resource
    BlackListFlowpathMapper blackListFlowpathMapper;
    @Resource
    UserMapper userMapper;
    @Resource
    private BaseDeptMapper baseDeptMapper;
    @Resource
    JcWorkFlowNodeMapper jcWorkFlowNodeMapper;
    @Resource
    private BlackListAttachmentMapper blackListAttachmentMapper;
    @Resource
    private BlackListRoadService blackListRoadService;
    @Resource
    JcEtcCardBlackListService jcEtcCardBlackListService;
    @Resource
    private BlackListRequestMapper blackListRequestMapper;
    @Autowired
    private JCOperationLogUtil jcOperationLogUtil;

    @Override
    public PageInfo<BlackListRequest> findAllPage(Map<String, Object> conditions) {
        String pageno = conditions.get("pageno").toString();
        String pagesize = conditions.get("pagesize").toString();
        int pagenoint = Integer.parseInt(pageno);
        int pagesizeint = Integer.parseInt(pagesize);
        PageHelper.startPage(pagenoint, pagesizeint);
        List<BlackListRequest> blacklistrequests = blacklistrequestmapper.findAll();
        return new PageInfo<>(blacklistrequests);
    }

    @Override
    public Map<String, Object> getCountByVehicle(Map<String, Object> map) {
        return blacklistrequestmapper.getCountByVehicle(map);
    }

    @Override
    public PageInfo<BlackListRequest> selectBlackListInform(Map<String, Object> conditions, QueryParams queryParams) {
        List<BlackListRequest> blacklistrequests = blacklistrequestmapper.getAll(conditions);
        return new PageInfo<BlackListRequest>(blacklistrequests);
    }

    @Override
    public int insertBlackListForm(BlackListRequest blackListRequest) {
        return blacklistrequestmapper.insert(blackListRequest);
    }

    @Override
    public boolean deleteBlackListInfo(List<Integer> integetlist) {

        return blacklistrequestmapper.deleteBlackListInfo(integetlist);
    }


    @Override
    public PageInfo<BlackListRequest> findAllById(Map<String, Object> conditions) {
        List<BlackListRequest> blacklistrequests = blacklistrequestmapper.findAllById(conditions);
        return new PageInfo<BlackListRequest>(blacklistrequests);
    }

    @Override
    public boolean updateBlackList(BlackListRequest blackListRequest) {

        return blacklistrequestmapper.updateBlackList(blackListRequest);
    }

    @Override
    public int saveBlackListForm(BlackListRequest blackListRequest) {

        return blacklistrequestmapper.saveBlackListForm(blackListRequest);
    }

    @Override
    public PageInfo<BlackListRequest> findAllRevocation(Map<String, Object> conditions) {
        String pageno = conditions.get("pageno").toString();
        String pagesize = conditions.get("pagesize").toString();
        int pagenoint = Integer.parseInt(pageno);
        int pagesizeint = Integer.parseInt(pagesize);
        PageHelper.startPage(pagenoint, pagesizeint);
        List<BlackListRequest> blacklistrequests = blacklistrequestmapper.findAllRevocation();
        return new PageInfo<>(blacklistrequests);
    }

    /**
     * ???????????????????????????????????????????????????????????????
     * ??????ETC???????????????????????????????????????
     * @param blackListRequest
     * @return
     */
    @Override
    @Transactional
    public ResultMsg addBlackRevokedAndUpdateRoadOrCardBlackList(BlackListRequest blackListRequest, String title, LoginUser loginUser, String flag, MultipartFile[] file1)throws IOException {
        ResultMsg result;
        BlackListRequestService blackListRequestService = (BlackListRequestService) AopContext.currentProxy();
        result = blackListRequestService.addBlackRevoked(blackListRequest, title, loginUser, flag, file1);
        StringBuffer stringBuffer=new StringBuffer();
        if (result.isSuccess()) {
            stringBuffer.append(result.getMessage());
            JcOperationLogEntity jcOperationLogEntity = new JcOperationLogEntity(blackListRequest.getCarNumber(), blackListRequest.getCarColour(), blackListRequest.getCarType(), blackListRequest.getEludeMoneyType().toString(), blackListRequest.getEludeMoneyTypeItem().toString(), 2);
            jcOperationLogUtil.addJcOperationLogOfJCAPP(jcOperationLogEntity, loginUser);//?????????????????? -???????????????

            if (loginUser.getGxName().equals(gxName)) {
                Long requestId=Long.parseLong(result.getData().toString());
                //???????????????????????????????????????????????????????????????????????????
                BlackListRequest newBlackListRequest = blackListRequestMapper.getDataByRequestId(requestId);
                String caseDescription = "?????????" + DateFormat.formatTime(new Date()) + "??????????????????????????????(???????????????)???????????????????????????????????????(???????????????),??????????????????";
                String htmlHead = "<!DOCTYPE html><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /><title></title></head>" +
                        "<style type=\"text/css\">body{width:700px;height:500px;margin:0px;padding:0px;}table{width:700px;margin-top:10px;border-collapse:separate;border-spacing:5px;table-layout:fixed}" +
                        "table td{height:24px;font-size:16px;color:black;text-align:center;background-color:#e5e5e5}</style><body>";
                String bodyStr = "<table>" +
                        "<tr><td>????????????????????????</td><td id =\"blackListRank\">" + "??????????????????" + "</td><td>????????????</td></tr>" +
                        "<tr><td>????????????</td><td id =\"vehicleLicense\">" + newBlackListRequest.getCarNumber() + "</td><td rowspan=\"11\" id=\"caseDescription\" style=\"word-wrap:break-word;letter-spacing:4px;line-height:20px;\"><b>\n" + caseDescription +
                        "</b></td></tr>" +
                        "<tr><td>????????????</td><td id =\"vehicleLicenseColor\">" + newBlackListRequest.getCarColourName() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"evasionType\">" + newBlackListRequest.getEludeMoneyTypeName() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"evasionKind\">" + newBlackListRequest.getEludeMoneyTypeItemName() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"lostCard\">" + newBlackListRequest.getHaveCardName() + "</td></tr>" +
                        "<tr><td>????????????</td><td><b id=\"evadeToll\">" + "0" + "</b>???</td></tr>" +
                        "<tr><td>????????????</td><td id =\"evadeTime\">" + newBlackListRequest.getExitStationTime1() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"evadeSite\">" + newBlackListRequest.getExitStationName1() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"phoneNumber\">" + newBlackListRequest.getPhone1() + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"stopFlag\">" + "?????????" + "</td></tr>" +
                        "<tr><td>????????????</td><td id =\"stopStatusType\">" + "?????????" + "</td></tr>";
                String htmlFoot = "</body></html>";
                String htmlStr = htmlHead + bodyStr + htmlFoot;
                try {
                    result = blackListRoadService.findHistoryAll(null, newBlackListRequest.getCarNumber(), Long.parseLong(newBlackListRequest.getCarColour() + ""), newBlackListRequest.getDisgorgementMoneyNumber(), htmlStr, requestId);//?????????????????????????????????????????????
                    if (!result.isSuccess()) {
                        log.info("???????????????????????????????????????????????????");
                        stringBuffer.append("??????????????????????????????????????????????????????");
                    }
                }catch (Exception e){
                    log.info(e.getMessage());
                    stringBuffer.append("?????????????????????????????????????????????");
                }

                try {
                    result = jcEtcCardBlackListService.findBlackByVehicleId(newBlackListRequest.getCarNumber(), newBlackListRequest.getCarColour().toString(), newBlackListRequest.getDisgorgementMoneyNumber());//??????ETC?????????????????????????????????
                    if (result.isSuccess()) {
                        result = jcEtcCardBlackListService.delEtcCardBlackList(new ObjectMapper().convertValue(result.getData(), JcEtcCardBlackListEntity.class).getAvtId(), 1);//ETC??????????????????????????????
                    } else {
                        log.info("ETC????????????????????????????????????");
                        stringBuffer.append("???ETC????????????????????????????????????");
                    }
                }catch (Exception e){
                    log.info(e.getMessage());
                    stringBuffer.append("?????????ETC?????????????????????");
                }
            }
        }
        result.setMessage(stringBuffer.toString());
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultMsg addBlackRevoked(BlackListRequest blackListRequest, String title, LoginUser loginUser, String flag, MultipartFile[] file1) throws IOException {
        ResultMsg result = new ResultMsg();
        BigDecimal disgorgementMoneyNumber = BigDecimal.valueOf(blackListRequest.getDisgorgementMoneyNumber()).multiply(new BigDecimal(100));
        if (flag.equals("update")) {
            BlackListRequest blackListRequest1 = new BlackListRequest();
            blackListRequest1.setRequestId(blackListRequest.getRequestId());
            blackListRequest1.setCarNumber(blackListRequest.getCarNumber());
            blackListRequest1.setCarColour(blackListRequest.getCarColour());
            blackListRequest1.setCreateUserName(blackListRequest.getCreateUserName());
            blackListRequest1.setFerretOutStationId(blackListRequest.getFerretOutStationId());
            blackListRequest1.setFerretOutStationTime(blackListRequest.getFerretOutStationTime());
            blackListRequest1.setCpuCardId(blackListRequest.getCpuCardId());
            blackListRequest1.setObuId(blackListRequest.getObuId());
            blackListRequest1.setDevcarNumber(blackListRequest.getDevcarNumber());
            blackListRequest1.setRequestDescription(blackListRequest.getRequestDescription());
            blackListRequest1.setDisgorgementMoneyNumber(disgorgementMoneyNumber.doubleValue());
            blackListRequest1.setSRequestId(blackListRequest.getSRequestId());
            blackListRequest1.setEntryStationId(blackListRequest.getEntryStationId());
            blackListRequest1.setExitStationId(blackListRequest.getExitStationId());
            blackListRequest1.setCarType(blackListRequest.getCarType());
            blackListRequest1.setEludeMoneyType(blackListRequest.getEludeMoneyType());
            blackListRequest1.setEludeMoneyTypeItem(blackListRequest.getEludeMoneyTypeItem());
            blackListRequest1.setRepairFeeUserName(blackListRequest.getRepairFeeUserName());
            blackListRequest1.setRepairFeePhone(blackListRequest.getRepairFeePhone());
            int uptCount = blacklistrequestmapper.uptBlackListRequest(blackListRequest1);
            if (uptCount < 1) {
                throw new RuntimeException("???????????????????????????");
            }
            result.setSuccess(true);
            result.setMessage("???????????????");
        } else if (flag.equals("add")) {
            //????????????????????????
            JcWorkFlowNodeEntity jcWorkFlowNode = jcWorkFlowNodeMapper.getDataByDefinationInitDept(1, loginUser.getDeptId().toString());
            if (jcWorkFlowNode == null) {
                result.setSuccess(false);
                result.setMessage("???????????????????????????");
                return result;
            }
            Long deptId = baseDeptMapper.getDeptId(loginUser.getDeptId());//???????????????????????????????????????ID
            int addCount;
            blackListRequest.setRequestStatus(1); //0?????????, 1 ??????, 2 ??????, 3 ????????????
            blackListRequest.setLogicType(2); //1?????????????????? 2????????????????????????
            blackListRequest.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId());
            blackListRequest.setLoginUserId(loginUser.getUserId()); //????????????
            blackListRequest.setCreateDeptid(loginUser.getDeptId()); //????????????
            blackListRequest.setDisgorgementMoneyNumber(disgorgementMoneyNumber.doubleValue()); //????????????
            blackListRequest.setDeleteFlag(1); //???????????????????????????1?????????
            blackListRequest.setCompanyDeptid(deptId);
            addCount = blacklistrequestmapper.addBlackRevoked(blackListRequest);
            if (addCount < 1) {
                throw new RuntimeException("???????????????????????????");
            }
            //???????????????????????????
            BlackListFlowpath blackListFlowpath = new BlackListFlowpath();
            blackListFlowpath.setRequestId(blackListRequest.getRequestId());
            blackListFlowpath.setFlowpathName(title); //????????????
            //???????????????????????????????????????????????????ID??????????????????ID
			/*String userId = userMapper.findLoginInfo(loginUser.getDeptId());
			blackListFlowpath.setOperateUserId(userId);*/
            blackListFlowpath.setOperateDeptId(loginUser.getDeptId()); //????????????
            blackListFlowpath.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId()); //???????????????
            blackListFlowpath.setWorkflowNodeId(jcWorkFlowNode.getNodeId()); //??????????????????
            addCount = blackListFlowpathMapper.addBlackFlowPath(blackListFlowpath);
            if (addCount < 1) {
                throw new RuntimeException("???????????????????????????");
            }
            //??????????????????????????????????????????
            Integer flowpathId = blackListFlowpath.getFlowpathId();
            Long requestId = blackListRequest.getRequestId();
            blacklistrequestmapper.uptFlowPathId(flowpathId, requestId);
            /* ???????????????????????? */
            //??????????????????
            for (MultipartFile file : file1) {
                if (!ObjectUtils.isEmpty(file)) {
                    //????????????
                    BlackListAttachment blackListAttachment = new BlackListAttachment();
                    int fileType = CommonUtils.getFileType(file);
                    blackListAttachment.setAttachmentName(file.getOriginalFilename());
                    blackListAttachment.setAttachmentSize(file.getSize());
                    blackListAttachment.setAttachmentPath(getBase64String(file));
                    blackListAttachment.setFileType(fileType);
                    blackListAttachment.setUserId(loginUser.getUserId());
                    blackListAttachment.setUserName(loginUser.getUserName());
                    blackListAttachment.setDeptId(loginUser.getDeptId());
                    blackListAttachment.setDeptName(loginUser.getDeptName());
                    blackListAttachment.setRequestId(blackListRequest.getRequestId());
                    blackListAttachment.setFlowpathId(blackListFlowpath.getFlowpathId());
                    //??????
                    blackListAttachmentMapper.saveBlacklistAttachment(blackListAttachment);
                }
            }
            result.setSuccess(true);
            result.setMessage("???????????????");
            result.setData(requestId);
        }
        return result;
    }

    /**
     * ?????????????????????-????????????
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg deleteBlackList(List<Long> requestIds) {
        ResultMsg result = new ResultMsg();
        //???????????????????????????
		/*BlackListRequest blackList = blacklistrequestmapper.findByRequestId(requestId);
		Integer flowpathId = 0;
		if(blackList != null){
			flowpathId = blackList.getCurrentFlowpathId();
		}*/
        int delCount = blacklistrequestmapper.delBlackRequest(requestIds);
        if (delCount < 1) {
            throw new RuntimeException("???????????????????????????");
        }
        //?????????????????????????????????
		/*int delCount1 = blackListFlowpathMapper.delBlackFlowPath(flowpathId);
		if(delCount1 < 1){
			throw new RuntimeException("???????????????????????????");
		}*/
        result.setSuccess(true);
        result.setMessage("???????????????");
        return result;
    }

    /**
     * ????????????????????????name
     *
     * @param name
     * @return
     */
    @Override
    public ResultMsg getStationByName(String name) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> tollStationData = blacklistrequestmapper.getStationByName(name);
        if (tollStationData.size() == 0) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setData(tollStationData);
        }
        return result;
    }

    @Override
    public ResultMsg newGetStationByName(String name, Long deptId) {
        ResultMsg result = new ResultMsg();
        List<Map<String, Object>> tollStationData = blacklistrequestmapper.newGetStationByName(name, deptId);
        if (tollStationData.size() == 0) {
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setData(tollStationData);
        }
        return result;
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param carNumber
     * @param carColour
     * @return
     */
    @Override
    public ResultMsg findRevokeBlackList(String carNumber, Integer carColour) {
        ResultMsg result = new ResultMsg();
        BlackListRequest blackListData = blacklistrequestmapper.getRevokeBlackList(carNumber, carColour);
        if (blackListData != null) {
            result.setSuccess(true);
            result.setData(blackListData);
        } else {
            result.setSuccess(false);
            result.setMessage("???????????????????????????????????????????????????");
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> searchBlackListNo(String carNumber, Integer carColour, Double eludeMoneyNumber) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("carNumber", carNumber);
        param.put("carColour", carColour);
        param.put("eludeMoneyNumber", eludeMoneyNumber);
        return blacklistrequestmapper.searchBlackListNo(param);
    }


}
