package com.tuozhi.zhlw.admin.jc.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.*;
import com.tuozhi.zhlw.admin.jc.mapper.*;
import com.tuozhi.zhlw.admin.jc.service.BlacklistRequestQueryService;
import com.tuozhi.zhlw.admin.jc.util.DateFormat;
import com.tuozhi.zhlw.admin.jc.util.ExcelUtils;
import com.tuozhi.zhlw.admin.mapper.UserMapper;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

import static com.tuozhi.zhlw.admin.jcApp.util.getBase64String;
import static com.tuozhi.zhlw.common.utils.CommonUtils.getFileType;


/**
 * @ClassName BlacklistRequestQueryServiceImpl
 * @Descriotion TODO 黑名单申请查询Service实现
 * @Author
 * @Date 2019/11/1 14:16
 * @Version 1.0
 */
@Service
public class BlacklistRequestQueryServiceImpl implements BlacklistRequestQueryService {
    @Resource
    private BlackListRequestMapper blackListRequestMapper;
    @Resource
    private BlackListFlowpathMapper blackListFlowpathMapper;
    @Resource
    private BlackListAttachmentMapper blackListAttachmentMapper;
    @Resource
    private JcWorkFlowNodeMapper jcWorkFlowNodeMapper;
    @Resource
    private BaseDeptMapper baseDeptMapper;
    @Resource
    private UserMapper userMapper;

    @Override
    public ResultExtGrid getBlacklistRequestListByParam(BlackListRequest blackListRequest, Integer revokeStatus, String requestStatuss, String ifCarRecord, Long deptId, QueryParams queryParams, Boolean flag, Integer all) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        //把查询条件（状态）转换为list
        requestStatuss = "[" + requestStatuss + "]";
        List<Integer> requestStatusListArr = JSONArray.parseArray(requestStatuss, Integer.class);
        Integer requestDeptStatus = null;
        /*
        requestDeptStatus是sql中判断查询状态时是否同时过滤部门权限
        查看选择的状态数组 没有数据置为null
        查看数组中是否有1如果有就把requestDeptStatus设置为1
        查看数组中是否有99如果有就把requestDeptStatus设置为2
        数组中1和99都有就把requestDeptStatus设置为null（不过滤部门权限）
         */
        List<Integer> requestStatusList1 = new ArrayList<>();
        List<Integer> requestStatusList2 = new ArrayList<>();
        if (requestStatusListArr.isEmpty()) {
            requestStatusList1 = null;
            requestStatusList2 = null;
        } else {
            if (requestStatusListArr.contains(1)) {
                requestStatusList1.add(1);
                requestDeptStatus = 1;
            }
            if (requestStatusListArr.contains(2)) {
                requestStatusList1.add(1);
                requestDeptStatus = 2;
            }
            if (requestStatusListArr.contains(3)) {
                requestStatusList2.add(2);
            }
            if (requestStatusListArr.contains(4)) {
                requestStatusList2.add(3);
            }
            if (requestStatusListArr.contains(1) && requestStatusListArr.contains(2)) {
                requestDeptStatus = null;
            }
            if (StringUtils.isNotEmpty(ifCarRecord)) {
                deptId = null;
            }
        }
        //判断参数中的车牌是否为空如果为空就置为null
        if (StringUtils.isEmpty(blackListRequest.getCarNumber())) {
            blackListRequest.setCarNumber(null);
        }

        if (requestStatusList1.isEmpty()) {
            requestStatusList1 = null;
        }
        if (requestStatusList2.isEmpty()) {
            requestStatusList2 = null;
        }
        //如果flag为true，则为稽查业务查询，只根据车牌和车牌颜色查询
        if (flag != null) {
            requestStatusList1 = null;
            requestStatusList2 = null;
            requestDeptStatus = null;
            deptId = null;
        }
        //查询
        List<BlackListRequest> blackListRequestList = new ArrayList<>();
        if (blackListRequest.getRequestId() == null) {
            if (Objects.nonNull(all)) {//查询所有待办
                blackListRequestList = blackListRequestMapper.getDataByParam(blackListRequest, revokeStatus, requestStatusList1, requestStatusList2, requestDeptStatus, null);
            } else {
                //页面查询和撤销走这个
                blackListRequestList = blackListRequestMapper.getDataByParam(blackListRequest, revokeStatus, requestStatusList1, requestStatusList2, requestDeptStatus, deptId);
            }
        } else {
            BlackListRequest blackListRequestEntity = blackListRequestMapper.getDataByRequestId(blackListRequest.getRequestId());
            blackListRequestList.add(blackListRequestEntity);
        }
        PageInfo pageInfo = new PageInfo<>(blackListRequestList);
        //返回数据
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public ResultMsg getExportBlacklistRequestListByParam(BlackListRequest blackListRequest, Integer revokeStatus, String requestStatuss, String ifCarRecord, Long deptId) {
        ResultMsg result = new ResultMsg();
        //把查询条件（状态）转换为list
        requestStatuss = "[" + requestStatuss + "]";
        List<Integer> requestStatusListArr = JSONArray.parseArray(requestStatuss, Integer.class);
        Integer requestDeptStatus = null;
        /*
        requestDeptStatus是sql中判断查询状态时是否同时过滤部门权限
        查看选择的状态数组 没有数据置为null
        查看数组中是否有1如果有就把requestDeptStatus设置为1
        查看数组中是否有99如果有就把requestDeptStatus设置为2
        数组中1和99都有就把requestDeptStatus设置为null（不过滤部门权限）
         */
        List<Integer> requestStatusList1 = new ArrayList<>();
        List<Integer> requestStatusList2 = new ArrayList<>();
        if (requestStatusListArr.isEmpty()) {
            requestStatusList1 = null;
            requestStatusList2 = null;
        } else {
            if (requestStatusListArr.contains(1)) {
                requestStatusList1.add(1);
                requestDeptStatus = 1;
            }
            if (requestStatusListArr.contains(2)) {
                requestStatusList1.add(1);
                requestDeptStatus = 2;
            }
            if (requestStatusListArr.contains(3)) {
                requestStatusList2.add(2);
            }
            if (requestStatusListArr.contains(4)) {
                requestStatusList2.add(3);
            }
            if (requestStatusListArr.contains(1) && requestStatusListArr.contains(2)) {
                requestDeptStatus = null;
            }
            if (StringUtils.isNotEmpty(ifCarRecord)) {
                deptId = null;
            }
        }
        //判断参数中的车牌是否为空如果为空就置为null
        if (StringUtils.isEmpty(blackListRequest.getCarNumber())) {
            blackListRequest.setCarNumber(null);
        }

        if (requestStatusList1.isEmpty()) {
            requestStatusList1 = null;
        }
        if (requestStatusList2.isEmpty()) {
            requestStatusList2 = null;
        }
        //查询
        List<BlackListRequest> blackListRequestList = blackListRequestMapper.getExportDataByParam(blackListRequest, revokeStatus, requestStatusList1, requestStatusList2, requestDeptStatus, deptId);
        //返回数据
        result.setSuccess(true);
        result.setData(blackListRequestList);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultMsg getBlacklistRequest(Long requestId) {
        ResultMsg result = new ResultMsg();
        BlackListRequest blackListRequest = blackListRequestMapper.getDataByRequestId(requestId);
        if (blackListRequest == null) {
            result.setSuccess(false);
            result.setMessage("查询失败");
            return result;
        }
        result.setSuccess(true);
        result.setData(blackListRequest);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultMsg getBlackListRequestFlowPathList(Long requestId) {
        ResultMsg result = new ResultMsg();
        List<BlackListFlowpath> blackListFlowpaths = blackListFlowpathMapper.getDataByRequestId(requestId);
        result.setSuccess(true);
        result.setData(blackListFlowpaths);
        result.setTotalProperty(blackListFlowpaths.size());
        result.setMessage("查询成功");
        return result;
    }




    @Override
    public ResultMsg getMaySendDeptUser(Integer flowPathId, Long requestId, LoginUser loginUser) {
        ResultMsg result = new ResultMsg();
        Map<String, Object> map = new HashMap<>();
        BaseDept baseDept = null;
        //查询当前环节数据
        BlackListFlowpath blackListFlowpath = blackListFlowpathMapper.getDataByFlowPathIdAndRequestId(flowPathId, requestId);
        if (blackListFlowpath != null && blackListFlowpath.getOperateDeptId().equals(loginUser.getDeptId())) {
            //根据黑名单id和对应的当前环节id查询当前流程的节点
            JcWorkFlowNodeEntity jcWorkFlowNode = blackListFlowpathMapper.getDataByRequestIdAndFlowPathId(flowPathId, requestId, false);
            if (jcWorkFlowNode != null && jcWorkFlowNode.getNodeType() != 3) {
                //根据下个节点的权限处理部门和处理部门角色查询可以发送的部门用户
                long workFlowDeptRoleNext = jcWorkFlowNode.getWorkFlowDeptRoleNext();
                long operateDeptId = blackListFlowpath.getOperateDeptId();
                baseDept = baseDeptMapper.getPrevDeptByDeptAndWorkFlowDeptRole(operateDeptId, workFlowDeptRoleNext);
            } else if (blackListFlowpath.getFlowpathStatus() == null || (blackListFlowpath.getFlowpathStatus() != 1 && blackListFlowpath.getFlowpathStatus() != 3 && blackListFlowpath.getFlowpathStatus() != 4)) {
                map.put("endWorkStatus", true);
            }
        }
        map.put("baseDept", baseDept);
        //设置可以发送的部门用户
        result.setSuccess(true);
        result.setData(map);
        result.setMessage("查询成功");
        return result;
    }



    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg sentBlackListRequest(Long requestId, Integer flowPathId, Long deptId, LoginUser loginUser, String operateUserName, String handleSuggestion) {
        ResultMsg result = new ResultMsg();
        //获取当前流程节点
        JcWorkFlowNodeEntity jcWorkFlowNode = blackListFlowpathMapper.getDataByRequestIdAndFlowPathId(flowPathId, requestId, true);
        if (jcWorkFlowNode == null) {
            result.setSuccess(false);
            result.setMessage("该工作流已停止");
            return result;
        }
        //创建下个环节
        BlackListFlowpath blackListFlowpath = new BlackListFlowpath();
        blackListFlowpath.setRequestId(requestId);
        blackListFlowpath.setFlowpathName("黑名单");
        blackListFlowpath.setCreateTime(new Date());
        blackListFlowpath.setOperateDeptId(deptId);
        blackListFlowpath.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId());
        blackListFlowpath.setWorkflowNodeId(jcWorkFlowNode.getNextNodeId());
        int newLinkCount = blackListFlowpathMapper.insertData(blackListFlowpath);
        if (newLinkCount < 1) {
            result.setSuccess(false);
            result.setMessage("发送失败");
            return result;
        }
        //更新黑名单的当前环节指向 刚创建的下个环节编号
        BlackListRequest blackListRequest = new BlackListRequest();
        blackListRequest.setRequestId(requestId);
        blackListRequest.setCurrentFlowpathId(blackListFlowpath.getFlowpathId());
        int updateCount1 = blackListRequestMapper.updateDataByRequestId(blackListRequest);
        if (updateCount1 < 1) {
            throw new RuntimeException("发送失败");
        }
        //更新当前环节数据
        BlackListFlowpath blackListFlowpath1 = new BlackListFlowpath();
        blackListFlowpath1.setFlowpathId(flowPathId);
        blackListFlowpath1.setRequestId(requestId);
        blackListFlowpath1.setOperateTime(new Date());
        blackListFlowpath1.setHandleSuggestion(handleSuggestion);
        blackListFlowpath1.setOperateUserName(operateUserName);
        blackListFlowpath1.setSendDirection(2);
        int updateCount2 = blackListFlowpathMapper.updateDataByFlowpathIdAndRequestId(blackListFlowpath1);
        if (updateCount2 < 1) {
            throw new RuntimeException("发送失败");
        }

        result.setSuccess(true);
        result.setData(blackListFlowpath1);
        result.setMessage("发送成功");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg sendBackBlackListRequest(Long requestId, Integer flowPathId, String operateUserName, String handleSuggestion) {
        ResultMsg result = new ResultMsg();
        //获取当前流程节点
        JcWorkFlowNodeEntity jcWorkFlowNode = blackListFlowpathMapper.getDataByRequestIdAndFlowPathId(flowPathId, requestId, true);
        if (jcWorkFlowNode == null) {
            result.setSuccess(false);
            result.setMessage("该工作流已停止");
            return result;
        }
        if (jcWorkFlowNode.getPrevNodeId() == null) {
            result.setSuccess(false);
            result.setMessage("没有上一个环节");
            return result;
        }
        //获取上个环节数据
        BlackListFlowpath blackListFlowpath = blackListFlowpathMapper.getPrevDataByPrevWorkFlow(jcWorkFlowNode.getDefinationId(), jcWorkFlowNode.getPrevNodeId(), requestId);
        //创建退回的环节
        BlackListFlowpath blackListFlowpath2 = new BlackListFlowpath();
        blackListFlowpath2.setRequestId(requestId);
        blackListFlowpath2.setFlowpathName("黑名单");
        blackListFlowpath2.setCreateTime(new Date());
        blackListFlowpath2.setOperateDeptId(blackListFlowpath.getOperateDeptId());
        blackListFlowpath2.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId());
        blackListFlowpath2.setWorkflowNodeId(jcWorkFlowNode.getPrevNodeId());
        int newLinkCount = blackListFlowpathMapper.insertData(blackListFlowpath2);
        if (newLinkCount < 1) {
            result.setSuccess(false);
            result.setMessage("退回失败");
            return result;
        }
        //更新黑名单的当前环节指向 刚创建的退回环节编号
        BlackListRequest blackListRequest = new BlackListRequest();
        blackListRequest.setRequestId(requestId);
        blackListRequest.setCurrentFlowpathId(blackListFlowpath2.getFlowpathId());
        int updateCount1 = blackListRequestMapper.updateDataByRequestId(blackListRequest);
        if (updateCount1 < 1) {
            throw new RuntimeException("退回失败");
        }
        //更新当前环节数据
        BlackListFlowpath blackListFlowpath3 = new BlackListFlowpath();
        blackListFlowpath3.setFlowpathId(flowPathId);
        blackListFlowpath3.setRequestId(requestId);
        blackListFlowpath3.setOperateTime(new Date());
        blackListFlowpath3.setHandleSuggestion(handleSuggestion);
        blackListFlowpath3.setOperateUserName(operateUserName);
        blackListFlowpath3.setFlowpathStatus(1);
        int updateCount2 = blackListFlowpathMapper.updateDataByFlowpathIdAndRequestId(blackListFlowpath3);
        if (updateCount2 < 1) {
            throw new RuntimeException("退回失败");
        }

        result.setSuccess(true);
        result.setMessage("退回成功");
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg endBlackListRequest(Long requestId, Integer flowPathId, Integer requestStatus, String operateUserName, String handleSuggestion) {
        ResultMsg result = new ResultMsg();
        //获取当前流程节点
        JcWorkFlowNodeEntity jcWorkFlowNode = blackListFlowpathMapper.getDataByRequestIdAndFlowPathId(flowPathId, requestId, true);
        if (jcWorkFlowNode == null) {
            result.setSuccess(false);
            result.setMessage("该工作流已停止");
            return result;
        }
        //设置环节表的状态
        Integer flowPathStatus = null;
        String errorMag = null;
        String succeed = null;
        if (requestStatus == 2) {
            flowPathStatus = 3;
            errorMag = "同意办结失败";
            succeed = "同意办结成功";
        } else if (requestStatus == 3) {
            flowPathStatus = 4;
            errorMag = "拒绝办结失败";
            succeed = "拒绝办结成功";
        }
        //更新当前环节数据
        BlackListFlowpath blackListFlowpath = new BlackListFlowpath();
        blackListFlowpath.setFlowpathId(flowPathId);
        blackListFlowpath.setRequestId(requestId);
        blackListFlowpath.setOperateTime(new Date());
        blackListFlowpath.setHandleSuggestion(handleSuggestion);
        blackListFlowpath.setOperateUserName(operateUserName);
        blackListFlowpath.setFlowpathStatus(flowPathStatus);
        int updateCount2 = blackListFlowpathMapper.updateDataByFlowpathIdAndRequestId(blackListFlowpath);
        if (updateCount2 < 1) {
            result.setSuccess(false);
            result.setMessage(errorMag);
            return result;
        }
        //更新黑名单的状态
        BlackListRequest blackListRequest = new BlackListRequest();
        blackListRequest.setRequestId(requestId);
        blackListRequest.setRequestStatus(requestStatus);
        int updateCount1 = blackListRequestMapper.updateDataByRequestId(blackListRequest);
        if (updateCount1 < 1) {
            throw new RuntimeException(errorMag);
        }
        result.setSuccess(true);
        result.setMessage(succeed);
        return result;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public ResultMsg saveBlackListAddRequest(BlackListRequest blackListRequest, LoginUser loginUser, MultipartFile[] file1) throws IOException {
        String pictureList = blackListRequest.getPictureList();
        ResultMsg result = new ResultMsg();
        Date newDate = new Date();
        //查询当前匹配环节
        JcWorkFlowNodeEntity jcWorkFlowNode = jcWorkFlowNodeMapper.getDataByDefinationInitDept(1, loginUser.getDeptId().toString());
        if (jcWorkFlowNode == null) {
            result.setSuccess(false);
            result.setMessage("没有匹配的工作流程");
            return result;
        }
        Long deptId = baseDeptMapper.getDeptId(loginUser.getDeptId());
        //新增 黑名单添加申请
        blackListRequest.setCreateTime(newDate);
        blackListRequest.setLoginUserId(loginUser.getUserId());
        blackListRequest.setRequestStatus(1);
        blackListRequest.setCreateDeptid(loginUser.getDeptId());
        blackListRequest.setLogicType(1);
        blackListRequest.setDeleteFlag(1);
        blackListRequest.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId());
        BigDecimal a = new BigDecimal(String.valueOf(blackListRequest.getPayMoneyNumber() == null ? 0 : blackListRequest.getPayMoneyNumber()));
        BigDecimal a1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setPayMoneyNumber(a.multiply(a1).doubleValue());
        BigDecimal b = new BigDecimal(String.valueOf(blackListRequest.getEludeMoneyNumber() == null ? 0 : blackListRequest.getEludeMoneyNumber()));
        BigDecimal b1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setEludeMoneyNumber(b.multiply(b1).doubleValue());
        BigDecimal c = new BigDecimal(String.valueOf(blackListRequest.getFactMoneyNumber() == null ? 0 : blackListRequest.getFactMoneyNumber()));
        BigDecimal c1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setFactMoneyNumber(c.multiply(c1).doubleValue());


        BigDecimal d = new BigDecimal(String.valueOf(blackListRequest.getCardLossFee() == null ? 0 : blackListRequest.getCardLossFee()));
        BigDecimal d1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setCardLossFee(d.multiply(d1));


        //获取 入口路段编号和出口路段编号 根据入口站和出口站（截取后三位就是路段）
        String enSectionId = blackListRequest.getEntryStationId().substring(0, blackListRequest.getEntryStationId().length() - 3);
        String exSectionId = blackListRequest.getExitStationId().substring(0, blackListRequest.getExitStationId().length() - 3);
        blackListRequest.setEnSectionId(enSectionId);
        blackListRequest.setExSectionId(exSectionId);
        blackListRequest.setCompanyDeptid(deptId);
        int addCount = blackListRequestMapper.insertData(blackListRequest);
        if (addCount < 1) {
            result.setSuccess(false);
            result.setMessage("保存失败");
            return result;
        }
        //新增 对应的初始环节
        BlackListFlowpath blackListFlowpath = new BlackListFlowpath();
        blackListFlowpath.setRequestId(blackListRequest.getRequestId());
        blackListFlowpath.setFlowpathName("黑名单添加申请");
        blackListFlowpath.setCreateTime(newDate);
        blackListFlowpath.setOperateDeptId(loginUser.getDeptId());
        blackListFlowpath.setWorkflowDefinationId(jcWorkFlowNode.getDefinationId());
        blackListFlowpath.setWorkflowNodeId(jcWorkFlowNode.getNodeId());
        addCount = blackListFlowpathMapper.insertData(blackListFlowpath);
        if (addCount < 1) {
            throw new RuntimeException("保存失败");
        }
        //更新刚新增的 黑名单添加申请 数据 更新当前环节字段
        blackListRequest = new BlackListRequest();
        blackListRequest.setRequestId(blackListFlowpath.getRequestId());
        blackListRequest.setCurrentFlowpathId(blackListFlowpath.getFlowpathId());
        int updateCount = blackListRequestMapper.updateDataByRequestId(blackListRequest);
        if (updateCount < 1) {
            throw new RuntimeException("保存失败");
        }
        for(MultipartFile file:file1){
            if(Objects.nonNull(file)) {
                BlackListAttachment blackListAttachment = new BlackListAttachment();
                blackListAttachment.setAttachmentName(file.getOriginalFilename());
                blackListAttachment.setAttachmentSize(file.getSize());
                blackListAttachment.setAttachmentPath(getBase64String(file));
                blackListAttachment.setFileType(getFileType(file.getOriginalFilename()));
                blackListAttachment.setUserId(loginUser.getUserId());
                blackListAttachment.setUserName(loginUser.getUserName());
                blackListAttachment.setDeptId(loginUser.getDeptId());
                blackListAttachment.setDeptName(loginUser.getDeptName());
                blackListAttachment.setRequestId(blackListRequest.getRequestId());
                blackListAttachment.setFlowpathId(blackListFlowpath.getFlowpathId());
                blackListAttachmentMapper.saveBlacklistAttachment(blackListAttachment);

            }
        }
        result.setSuccess(true);
        result.setMessage("保存成功");
        result.setData(blackListRequest.getRequestId());
        return result;
    }


    @Override
    public ResultMsg getFileImage(Integer attachmentId) throws UnsupportedEncodingException {
        ResultMsg result = new ResultMsg();
        BlackListAttachment blackListAttachment = blackListAttachmentMapper.getDataByAttachmentId(attachmentId);
        if (blackListAttachment == null || blackListAttachment.getFileType() == null || blackListAttachment.getFileType() != 3) {
            result.setSuccess(false);
            result.setMessage("该文件不是图片，不支持查看!");
            return result;
        }
        //转换为字节码
        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(blackListAttachment.getAttachmentPath());
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            result.setSuccess(false);
            result.setMessage("显示图片失败");
            return result;
        }
        //转换为Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        String attachmentPath = encoder.encodeToString(data);
        result.setSuccess(true);
        result.setData(attachmentPath);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultExtGrid getExsectionSumData(Date beginTime, Date endDate, QueryParams queryParams) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        //获取 汇总数据 根据创建时间、按照路段分组
        List<Map<String, Object>> exSectionSumDataList = blackListRequestMapper.getExsectionSumData(beginTime, endDate);
        PageInfo pageInfo = new PageInfo<>(exSectionSumDataList);
        //返回数据
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public ResultExtGrid getEludeMoneyTypeSumData(Date beginTime, Date endDate, QueryParams queryParams) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        //获取 汇总数据 根据创建时间、按照路段分组
        List<Map<String, Object>> exSectionSumDataList = blackListRequestMapper.getEludeMoneyTypeSumData(beginTime, endDate);
        PageInfo pageInfo = new PageInfo<>(exSectionSumDataList);
        //返回数据
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public void exportBlacklistExsectionSum(Date beginTime, Date endDate) throws Exception {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        //查询
        List<Map<String, Object>> exSectionSumDataList = blackListRequestMapper.getExsectionSumData(beginTime, endDate);
        String[] headTextNames = {
                "公司", "路段", "路段名", "提交数量", "提交金额（元）", "添加申请提交数量", "撤销申请提交数量", "添加申请提交金额（元）",
                "撤销申请提交金额（元）", "流程中数量", "流程中金额（元）", "添加申请流程中数量", "撤销申请流程中数量", "添加申请流程中金额（元）",
                "撤销申请流程中金额（元）", "同意办结数量（申黑和撤黑总计）", "同意办结金额（元）（申黑和撤黑总计）", "添加申请同意办结数量", "撤销申请同意办结数量", "添加申请同意办结金额（元）",
                "撤销申请同意办结金额（元）", "拒绝办结数量", "拒绝办结金额（元）", "添加申请拒绝办结数量", "撤销申请拒绝办结数量", "添加申请拒绝办结金额（元）",
                "撤销申请拒绝办结金额（元）"
        };
        String[] rows = {"COMPANYNAME", "EXSECTIONID", "EXSECTIONNAME", "SUBMITCOUNT", "SUBMITSUM", "ADDREQUESTSUBMITCOUNT", "DELETEREQUESTSUBMITCOUNT",
                "ADDREQUESTSUBMITSUM", "DELETEREQUESTSUBMITSUM", "INCOUNT", "INSUM", "ADDREQUESTINCOUNT", "DELETEREQUESTINCOUNT", "ADDREQUESTINSUM", "DELETEREQUESTINSUM", "AGREEENDCOUNT", "AGREEENDSUM",
                "ADDREQUESTAGREEENDCOUNT", "DELETEREQUESTAGREEENDCOUNT", "ADDREQUESTAGREEENDSUM", "DELETEREQUESTAGREEENDSUM", "REFUSEENDCOUNT", "REFUSEENDSUM", "ADDREQUESTREFUSEENDCOUNT", "DELETEREQUESTREFUSEENDCOUNT",
                "ADDREQUESTREFUSEENDSUM", "DELETEREQUESTREFUSEENDSUM"};
        ExcelUtils.excelUtil(response, headTextNames, exSectionSumDataList, rows, "黑名单路段统计表");

    }

    @Override
    public void exportBlacklistEludeMoneyTypeSum(Date beginTime, Date endDate) throws Exception {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        //查询
        List<Map<String, Object>> exSectionSumDataList = blackListRequestMapper.getEludeMoneyTypeSumData(beginTime, endDate);
        String[] headTextNames = {
                "逃费类型", "逃费种类", "提交数量", "提交金额（元）", "添加申请提交数量", "撤销申请提交数量", "添加申请提交金额（元）",
                "撤销申请提交金额（元）", "流程中数量", "流程中金额（元）", "添加申请流程中数量", "撤销申请流程中数量", "添加申请流程中金额（元）",
                "撤销申请流程中金额（元）", "同意办结数量", "同意办结金额（元）", "添加申请同意办结数量", "撤销申请同意办结数量", "添加申请同意办结金额（元）",
                "撤销申请同意办结金额（元）", "拒绝办结数量", "拒绝办结金额（元）", "添加申请拒绝办结数量", "撤销申请拒绝办结数量", "添加申请拒绝办结金额（元）",
                "撤销申请拒绝办结金额（元）"
        };
        //导出Excle
        String[] rows = {"ELUDEMONEYTYPENAME", "ELUDEMONEYTYPEITEMNAME", "SUBMITCOUNT", "SUBMITSUM", "ADDREQUESTSUBMITCOUNT", "DELETEREQUESTSUBMITCOUNT",
                "ADDREQUESTSUBMITSUM", "DELETEREQUESTSUBMITSUM", "INCOUNT", "INSUM", "ADDREQUESTINCOUNT", "DELETEREQUESTINCOUNT", "ADDREQUESTINSUM", "DELETEREQUESTINSUM", "AGREEENDCOUNT", "AGREEENDSUM",
                "ADDREQUESTAGREEENDCOUNT", "DELETEREQUESTAGREEENDCOUNT", "ADDREQUESTAGREEENDSUM", "DELETEREQUESTAGREEENDSUM", "REFUSEENDCOUNT", "REFUSEENDSUM", "ADDREQUESTREFUSEENDCOUNT", "DELETEREQUESTREFUSEENDCOUNT",
                "ADDREQUESTREFUSEENDSUM", "DELETEREQUESTREFUSEENDSUM"};
        ExcelUtils.excelUtil(response, headTextNames, exSectionSumDataList, rows, "黑名单逃费类型统计表");
    }

    @Override
    public ResultMsg<List<List<String>>> exportBlacklistByRequestIdList(List<Long> requestIdList) {
        ResultMsg result = new ResultMsg();
        if (requestIdList.isEmpty()) {
            result.setSuccess(true);
            result.setData(null);
            return result;
        }
        //查询
        List<BlackListRequest> blackListRequestList = blackListRequestMapper.getDataByRequestIdList(requestIdList);
        //添加导出Excle的内容
        List<List<String>> data = new ArrayList<>();
        for (BlackListRequest blackListRequest : blackListRequestList) {
            List<String> blackListRequestData = new ArrayList<>();
            blackListRequestData.add(blackListRequest.getEntryStationId() == null ? "" : blackListRequest.getEntryStationId().toString());
            blackListRequestData.add(blackListRequest.getEntryStationTime() == null ? "" : DateFormat.getFormatDate(blackListRequest.getEntryStationTime()));
            blackListRequestData.add(blackListRequest.getExitStationId() == null ? "" : blackListRequest.getExitStationId().toString());
            blackListRequestData.add(blackListRequest.getExitStationTime() == null ? "" : DateFormat.getFormatDate(blackListRequest.getExitStationTime()));
            blackListRequestData.add(getCarColourName(blackListRequest.getCarColour()) + blackListRequest.getCarNumber());
            blackListRequestData.add(blackListRequest.getCarType() == null ? "" : blackListRequest.getCarType().toString());
            blackListRequestData.add(blackListRequest.getAxleType());
            blackListRequestData.add(blackListRequest.getTotalWeight() == null ? "" : blackListRequest.getTotalWeight().toString());
            blackListRequestData.add(blackListRequest.getFactMoneyNumber() == null ? "" : blackListRequest.getFactMoneyNumber().toString());
            blackListRequestData.add(blackListRequest.getEludeMoneyNumber() == null ? "" : blackListRequest.getEludeMoneyNumber().toString());
            data.add(blackListRequestData);
        }
        result.setSuccess(true);
        result.setData(data);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultMsg<List<List<String>>> exportBlacklistVehicleRevocation(BlackListRequest blackListRequest, String requestStatuss, Long deptId) {
        ResultMsg result = new ResultMsg();
        //把查询条件（状态）转换为list
        requestStatuss = "[" + requestStatuss + "]";
        List<Integer> requestStatusListArr = JSONArray.parseArray(requestStatuss, Integer.class);
        Integer requestDeptStatus = null;
        /*
        requestDeptStatus是sql中判断查询状态时是否同时过滤部门权限
        查看选择的状态数组 没有数据置为null
        查看数组中是否有1如果有就把requestDeptStatus设置为1
        查看数组中是否有99如果有就把requestDeptStatus设置为2
        数组中1和99都有就把requestDeptStatus设置为null（不过滤部门权限）
         */
        List<Integer> requestStatusList1 = new ArrayList<>();
        List<Integer> requestStatusList2 = new ArrayList<>();
        if (requestStatusListArr.isEmpty()) {
            requestStatusList1 = null;
            requestStatusList2 = null;
        } else {
            if (requestStatusListArr.contains(1)) {
                requestStatusList1.add(1);
                requestDeptStatus = 1;
            }
            if (requestStatusListArr.contains(2)) {
                requestStatusList1.add(1);
                requestDeptStatus = 2;
            }
            if (requestStatusListArr.contains(3)) {
                requestStatusList2.add(2);
            }
            if (requestStatusListArr.contains(4)) {
                requestStatusList2.add(3);
            }
            if (requestStatusListArr.contains(1) && requestStatusListArr.contains(2)) {
                requestDeptStatus = null;
            }
        }
        //判断参数中的车牌是否为空如果为空就置为null
        if (StringUtils.isEmpty(blackListRequest.getCarNumber())) {
            blackListRequest.setCarNumber(null);
        }

        if (requestStatusList1.isEmpty()) {
            requestStatusList1 = null;
        }
        if (requestStatusList2.isEmpty()) {
            requestStatusList2 = null;
        }
        //查询
        List<BlackListRequest> blackListRequestList = blackListRequestMapper.exportBlacklistVehicleRevocation(blackListRequest, requestStatusList1, requestStatusList2, requestDeptStatus, deptId);
        //添加导出Excle的内容
        List<List<String>> data = new ArrayList<>();
        for (BlackListRequest blackListRequest2 : blackListRequestList) {
            List<String> blackListRequest3 = new ArrayList<>();
            blackListRequest3.add(blackListRequest2.getCarNumber());
            blackListRequest3.add(blackListRequest2.getCarColourName());
            blackListRequest3.add(blackListRequest2.getEludeMoneyTypeName());
            blackListRequest3.add(blackListRequest2.getEludeMoneyTypeItemName());
            blackListRequest3.add(blackListRequest2.getCpuCardId());
            blackListRequest3.add(blackListRequest2.getObuId());
            blackListRequest3.add(blackListRequest2.getCarFeature() == null ? (blackListRequest2.getCarFeature1() == null ? "" : blackListRequest2.getCarFeature1()) : blackListRequest2.getCarFeature());
            blackListRequest3.add(blackListRequest2.getCarTypeName());
            blackListRequest3.add(blackListRequest2.getAxleType());
            blackListRequest3.add(blackListRequest2.getSeatNum() == null ? "" : blackListRequest2.getSeatNum());
            blackListRequest3.add(blackListRequest2.getCreateTime() == null ? "" : DateFormat.getFormatDate(blackListRequest2.getCreateTime()));
            blackListRequest3.add(blackListRequest2.getDisgorgementMoneyNumber() == null ? (blackListRequest2.getDisgorgementMoneyNumber() == null ? "" : blackListRequest2.getDisgorgementMoneyNumber().toString()) : blackListRequest2.getDisgorgementMoneyNumber().toString());
            blackListRequest3.add(blackListRequest2.getExitStationTime() == null ? (blackListRequest2.getExitStationTime1() == null ? "" : blackListRequest2.getExitStationTime1().toString()) : DateFormat.getFormatDate(blackListRequest2.getExitStationTime()));
            blackListRequest3.add(blackListRequest2.getExitStationName() == null ? (blackListRequest2.getExitStationName1() == null ? "" : blackListRequest2.getExitStationName1().toString()) : blackListRequest2.getExitStationName());
            blackListRequest3.add(blackListRequest2.getFerretOutStationName());
            blackListRequest3.add(blackListRequest2.getRequestDescription());
            data.add(blackListRequest3);
        }
        result.setSuccess(true);
        result.setData(data);
        result.setMessage("查询成功");
        return result;
    }

    @Override
    public ResultExtGrid getBlacklistConnectData(Date beginTime, Date endDate, QueryParams queryParams) {
        //设置分页
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        //获取 稽核追缴黑名单车辆通行费交接明细表 数据
        List<Map<String, Object>> connecrtData = blackListRequestMapper.getConnectData(beginTime, endDate);
        PageInfo pageInfo = new PageInfo<>(connecrtData);
        //返回数据
        return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
    }

    @Override
    public ResultMsg<List<List<String>>> exportBlacklistConnectData(Date beginTime, Date endDate) throws ParseException {
        ResultMsg result = new ResultMsg();
        //查询
        List<Map<String, Object>> connectDataList = blackListRequestMapper.getConnectData(beginTime, endDate);
        //添加导出Excle的内容
        List<List<String>> data = new ArrayList<>();
        for (Map<String, Object> connect : connectDataList) {
            List<String> connectData = new ArrayList<>();
            connectData.add(connect.get("FERRETOUTSTATIONTIME") == null ? "" : DateFormat.getFormatDate(DateFormat.StringToDate3(connect.get("FERRETOUTSTATIONTIME").toString())));
            connectData.add(connect.get("CARNUMBER") == null ? "" : connect.get("CARNUMBER").toString());
            connectData.add(connect.get("CARTYPE") == null ? "" : connect.get("CARTYPE").toString());
            connectData.add(connect.get("ELUDEMONEYTYPENAME") == null ? "" : connect.get("ELUDEMONEYTYPENAME").toString());
            connectData.add(connect.get("EXITSTATIONNAME") == null ? "" : connect.get("EXITSTATIONNAME").toString());
            connectData.add(connect.get("FERRETOUTSTATIONNAME") == null ? "" : connect.get("FERRETOUTSTATIONNAME").toString());
            connectData.add(connect.get("ELUDEMONEYNUMBER") == null ? "" : connect.get("ELUDEMONEYNUMBER").toString());
            connectData.add(connect.get("DISGORGEMENTMONEYNUMBER") == null ? "" : connect.get("DISGORGEMENTMONEYNUMBER").toString());
            connectData.add(connect.get("DEALCOMPANY") == null ? "" : connect.get("DEALCOMPANY").toString());
            connectData.add("");
            connectData.add(connect.get("DEALCOMPANY") == null ? "" : connect.get("DEALCOMPANY").toString());
            connectData.add("");
            connectData.add(connect.get("FERRETOUTSTATIONID") == null ? "" : connect.get("FERRETOUTSTATIONID").toString());
            connectData.add("");
            connectData.add("");
            connectData.add("");
            data.add(connectData);
        }
        result.setSuccess(true);
        result.setData(data);
        result.setMessage("查询成功");
        return result;
    }

    /**
     * 黑名单添加申请-修改
     *
     * @param blackListRequest
     * @return
     */
    @Override
    public ResultMsg updateBlackListAddRequest(BlackListRequest blackListRequest) {
        ResultMsg result = new ResultMsg();
        blackListRequest.setRequestId(blackListRequest.getRequestId());
        blackListRequest.setInvestigationId(blackListRequest.getInvestigationId());
        blackListRequest.setCarFeature(blackListRequest.getCarFeature());
        blackListRequest.setEludeMoneyType(blackListRequest.getEludeMoneyType());
        blackListRequest.setHaveDamageDevice(blackListRequest.getHaveDamageDevice());
        blackListRequest.setEntryStationId(blackListRequest.getEntryStationId());
        blackListRequest.setExitStationId(blackListRequest.getExitStationId());
        blackListRequest.setCreateUserName(blackListRequest.getCreateUserName());
        blackListRequest.setCpuCardId(blackListRequest.getCpuCardId());
        blackListRequest.setCarColour(blackListRequest.getCarColour());
        blackListRequest.setCarNumber(blackListRequest.getCarNumber());
        blackListRequest.setSeatNum(blackListRequest.getSeatNum());
        blackListRequest.setEludeMoneyTypeItem(blackListRequest.getEludeMoneyTypeItem());
        blackListRequest.setHaveVideo(blackListRequest.getHaveVideo());
        BigDecimal a = new BigDecimal(String.valueOf(blackListRequest.getPayMoneyNumber() == null ? 0 : blackListRequest.getPayMoneyNumber()));
        BigDecimal a1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setPayMoneyNumber(a.multiply(a1).doubleValue());
        BigDecimal b = new BigDecimal(String.valueOf(blackListRequest.getEludeMoneyNumber() == null ? 0 : blackListRequest.getEludeMoneyNumber()));
        BigDecimal b1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setEludeMoneyNumber(b.multiply(b1).doubleValue());
        BigDecimal c = new BigDecimal(String.valueOf(blackListRequest.getFactMoneyNumber() == null ? 0 : blackListRequest.getFactMoneyNumber()));
        BigDecimal c1 = new BigDecimal(String.valueOf(100));

        BigDecimal d = new BigDecimal(String.valueOf(blackListRequest.getCardLossFee() == null ? 0 : blackListRequest.getCardLossFee()));
        BigDecimal d1 = new BigDecimal(String.valueOf(100));
        blackListRequest.setCardLossFee(d.multiply(d1));


        blackListRequest.setFactMoneyNumber(c.multiply(c1).doubleValue());
        blackListRequest.setEntryStationTime(blackListRequest.getEntryStationTime());
        blackListRequest.setExitStationTime(blackListRequest.getExitStationTime());
        blackListRequest.setPhone(blackListRequest.getPhone());
        blackListRequest.setObuId(blackListRequest.getObuId());
        blackListRequest.setCarType(blackListRequest.getCarType());
        blackListRequest.setAxleType(blackListRequest.getAxleType());
        blackListRequest.setDevcarNumber(blackListRequest.getDevcarNumber());
        blackListRequest.setHaveCard(blackListRequest.getHaveCard());
        blackListRequest.setTotalWeight(blackListRequest.getTotalWeight());
        blackListRequest.setRecoverTime(blackListRequest.getRecoverTime());
        blackListRequest.setPhysicalTruthStatus(blackListRequest.getPhysicalTruthStatus());
        blackListRequest.setSumtotal(blackListRequest.getSumtotal());
        blackListRequest.setCardId(blackListRequest.getCardId());
        blackListRequest.setRequestDescription(blackListRequest.getRequestDescription());
        blackListRequest.setIssuer(blackListRequest.getIssuer());
        int uptCount = blackListRequestMapper.uptBlackListAddRequest(blackListRequest);
        if (uptCount < 1) {
            result.setSuccess(false);
            result.setMessage("修改失败，请重试");
            return result;
        }
        result.setSuccess(true);
        result.setMessage("修改成功！");
        result.setData(blackListRequest.getRequestId());
        return result;
    }

    @Override
    public PageInfo<BlackListRequest> selectALLLog(QueryParams queryParams, String carNumber, Integer carColour, Boolean flag) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List list = blackListRequestMapper.selectALLLog(carNumber, carColour, flag);
        return new PageInfo<BlackListRequest>(list);
    }



    /**
     * 根据车牌颜色获取对应的中文
     *
     * @param carColour
     * @return
     */
    private String getCarColourName(Integer carColour) {
        if (carColour == 0) {
            return "蓝";
        } else if (carColour == 1) {
            return "黄";
        } else if (carColour == 2) {
            return "黑";
        } else if (carColour == 3) {
            return "白";
        } else if (carColour == 4) {
            return "渐变绿";
        } else if (carColour == 5) {
            return "黄绿双拼";
        } else if (carColour == 6) {
            return "蓝白渐变";
        } else if (carColour == 7) {
            return "临时牌照";
        } else if (carColour == 9) {
            return "未确定";
        } else if (carColour == 10) {
            return "所有";
        } else if (carColour == 11) {
            return "绿色";
        } else if (carColour == 12) {
            return "红色";
        }
        return "";
    }

    /**
     * 根据车牌颜色获取对应的数值
     *
     * @param name
     * @return
     */
    private Integer getCarColourNumber(String name) {
        if (name.contains("蓝")) {
            return 0;
        } else if (name.contains("黄")) {
            return 1;
        } else if (name.contains("黑")) {
            return 2;
        } else if (name.contains("白")) {
            return 3;
        } else if (name.contains("渐变绿")) {
            return 4;
        } else if (name.contains("黄绿双拼")) {
            return 5;
        } else if (name.contains("蓝白渐变")) {
            return 6;
        } else if (name.contains("临时牌照")) {
            return 7;
        } else if (name.contains("未确定")) {
            return 9;
        } else if (name.contains("所有")) {
            return 10;
        } else if (name.contains("绿色")) {
            return 11;
        } else if (name.contains("红色")) {
            return 12;
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getBlackListAfterPaymentData(Map<String, Object> conditions) {
        return blackListRequestMapper.getBlackListAfterPaymentData(conditions);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultMsg<Map<String, Object>> readBlackListAfterPaymentExcelInfo(File file, Long requestId) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ResultMsg result = new ResultMsg();
        FileInputStream fis = null;
        Workbook workbook = null;

        String filePath = file.toString();
        String extString = filePath.substring(filePath.lastIndexOf("."));
        try {
            fis = new FileInputStream(file);
            if (".xls".equals(extString)) {
                workbook = new HSSFWorkbook(fis);// 得到工作簿// 2003版本的excel，用.xls结尾
            } else if (".xlsx".equals(extString)) {
                workbook = new XSSFWorkbook(fis);// 得到工作簿// 2007版本的excel，用.xlsx结尾
            } else {
                workbook = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 得到一个工作表
        Sheet sheet = workbook.getSheetAt(0);
        // 获得数据的总行数
        int totalRowNum = sheet.getLastRowNum();

        //声明要获得的属性
        String listId = null; //
        String passId = null; //
        String vehicleLicense = null; //
        Integer vehicleLicensecolor = null; //
        String enstationName = null; //
        String exstationName = null; //
        String cardId = null; //
        String obuId = null; //
        String vehicleType = null; //
        Integer correctVehicleType = null;
        BigDecimal fee = null; //
        BigDecimal correctFee = null; //
        BigDecimal oweFee = null; //
        String issure = null;
        String realAxle = null;
        String vehicleClass = null;
        String transprovincial = null;
        BlackListAfterPayment pay = null;
        // 遍历获得所有数据
        for (int i = 1; i <= totalRowNum; i++) {
            pay = new BlackListAfterPayment();
            // 获得第i行对象
            Row row = sheet.getRow(i);
            // 获得第i行第0列的int类型对象
            Cell cell = null;
            cell = row.getCell(1);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                listId = cell.getStringCellValue().trim();
                pay.setListId(listId);
            } else {
                pay.setListId(null);
            }
            cell = row.getCell(2);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                passId = cell.getStringCellValue().trim();
                pay.setPassId(passId);
            } else {
                pay.setPassId(null);
            }
            cell = row.getCell(3);

            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                cardId = cell.getStringCellValue().trim();
                pay.setCardId(cardId);
            } else {
                pay.setCardId(null);
            }

            cell = row.getCell(4);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                obuId = cell.getStringCellValue().trim();
                pay.setObuId(obuId);
            } else {
                pay.setObuId(null);
            }
            cell = row.getCell(5);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                issure = cell.getStringCellValue().trim();
                pay.setIssure(issure);
            } else {
                pay.setIssure(null);
            }
            cell = row.getCell(6);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                vehicleLicense = cell.getStringCellValue().trim();
                if (StringUtils.isBlank(vehicleLicense)) {
                    continue;
                }
                pay.setVehicleLicense(vehicleLicense.split("_")[0]);
                String tmp = vehicleLicense.split("_")[1];
                if (StringUtils.isNoneEmpty(tmp)) {
                    if (Pattern.compile("[0-9]*").matcher(tmp).matches()) {
                        pay.setVehicleLicensecolor(Integer.parseInt(tmp));
                    } else {
                        Integer carColourNumber = getCarColourNumber(tmp);
                        pay.setVehicleLicensecolor(carColourNumber);
                    }
                } else {
                    pay.setVehicleLicensecolor(null);
                }
            } else {
                continue;
            }

            cell = row.getCell(7);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                enstationName = cell.getStringCellValue().trim();
                pay.setEnstationName(enstationName);
            } else {
                pay.setEnstationName(null);
            }

            cell = row.getCell(8);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String enTimeStr = cell.getStringCellValue().trim();
                pay.setEnTime(sdf.parse(enTimeStr));
            } else {
                pay.setEnTime(null);
            }

            cell = row.getCell(9);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String inAxle = cell.getStringCellValue().trim();
                pay.setInAxle(inAxle);
            } else {
                pay.setInAxle(null);
            }

            cell = row.getCell(10);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                exstationName = cell.getStringCellValue().trim();
                pay.setExstationName(exstationName);
            } else {
                pay.setExstationName(null);
            }

            cell = row.getCell(11);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String exTimeStr = cell.getStringCellValue().trim();
                pay.setExTime(sdf.parse(exTimeStr));
            } else {
                pay.setExTime(null);
            }

            cell = row.getCell(12);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                String outAxle = cell.getStringCellValue().trim();
                pay.setOutAxle(outAxle);
            } else {
                pay.setOutAxle(null);
            }

            cell = row.getCell(13);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                vehicleType = cell.getStringCellValue().trim();
                pay.setVehicleType(vehicleType);
            } else {
                pay.setVehicleType(null);
            }

            cell = row.getCell(14);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                pay.setFee(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                pay.setFee(null);
            }
            cell = row.getCell(15);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                pay.setCorrectFee(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                pay.setCorrectFee(null);
            }

            cell = row.getCell(16);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                pay.setOweFee(new BigDecimal(cell.getNumericCellValue()).setScale(2, BigDecimal.ROUND_HALF_UP));
            } else {
                pay.setOweFee(null);
            }
            cell = row.getCell(17);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_NUMERIC);
                correctVehicleType = (int) cell.getNumericCellValue();
                pay.setCorrectVehicleType(correctVehicleType);
            } else {
                pay.setCorrectVehicleType(null);
            }

            cell = row.getCell(18);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                realAxle = cell.getStringCellValue().trim();
                pay.setRealAxle(realAxle);
            } else {
                pay.setRealAxle(null);
            }
            cell = row.getCell(19);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                vehicleClass = cell.getStringCellValue().trim();
                pay.setVehicleClass(vehicleClass);
            } else {
                pay.setVehicleClass(null);
            }
            cell = row.getCell(20);
            if (cell != null) {
                cell.setCellType(Cell.CELL_TYPE_STRING);
                transprovincial = cell.getStringCellValue().trim();
                pay.setTransprovincial(transprovincial);
            } else {
                pay.setTransprovincial(null);
            }
            pay.setRequestId(requestId);
            pay.setFlag(1);
            int addCount = blackListRequestMapper.saveBlackListAfterPaymentData(pay);
            if (addCount < 1) {
                result.setSuccess(false);
                result.setMessage("导入第" + i + "条数据出错，成功导入" + (i - 1) + "条");
                return result;
            }
            result.setSuccess(true);
            result.setMessage("文件导入成功，成功导入" + i + "条");
        }
        return result;
    }

    @Override
    public Long getBlackListAfterPaymentDataCount(Map<String, Object> conditions) {
        return blackListRequestMapper.getBlackListAfterPaymentDataCount(conditions);
    }

    @Override
    public Integer saveBlackListAfterPaymentData(BlackListAfterPayment pay) {
        pay.setFlag(2);
        return blackListRequestMapper.saveBlackListAfterPaymentData(pay);
    }

    @Override
    public Integer deleteBlackListAfterPaymentData(long[] ids) {
        return blackListRequestMapper.deleteBlackListAfterPaymentData(ids);
    }


    @Override
    public void onExportLocalBlackExcel(HttpServletResponse response, Long requestId) throws Exception {
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("requestId", requestId);
        List<Map<String, Object>> blackListAfterPaymentData = blackListRequestMapper.getBlackListAfterPaymentData(conditions);
        int i = 1;
        for (Map map : blackListAfterPaymentData) {
            map.put("序号", i);
            i++;
        }
        String[] titles = {"序号", "出口流水号", "通行标识ID", "ECT卡号", "OBU号", "发行省份", "车牌号码(格式车牌_车牌颜色)", "入口站",
                "入口时间", "入口轴数", "出口站", "出口时间", "出口轴数", "出口车型", "实收金额", "应收金额", "少收金额", "正确车型（必填）1:一型客车,2:二型客车,3:三型客车,4:四型客车,11:一型货车,12:二型货车,13:三型货车,14:四型货车,15:五型货车,16:六型货车,21:一型专项作业车,22:二型专项作业车,23:三型专项作业车,24:四型专项作业车,25:五型专项作业车,26:六型专项作业车\n", "正确轴数", "正确车种必填（必填0:普通,8:军警,10:紧急,14:车队,21:绿通车,22:联合收割机,23:抢险救灾,24:J1集装箱,25:大件运输,26:应急车,27:货车列车或半挂汽车列车,28:J2类型集装箱\n" +
                "）", "是否跨省"};
        String[] rows = {"序号", "LISTID", "PASSID", "CARDID", "OBUID", "ISSURE", "VEHICLE", "ENSTATIONNAME",
                "ENTIME", "INAXLE", "EXSTATIONNAME", "EXTIME", "OUTAXLE", "VEHICLETYPE", "FEE", "CORRECTFEE", "OWEFEE", "CORRECTVEHICLETYPE", "REALAXLE", "VEHICLECLASS", "TRANSPROVINCIAL"};
        ExcelUtils.excelUtil(response, titles, blackListAfterPaymentData, rows, "黑名单申请工单逃费明细");
    }

  /*  @Override
    public ResultMsg getNextDept(LoginUser loginUser) {
        ResultMsg result=new ResultMsg();
        Map map=new HashMap();
        //查询当前匹配环节
        JcWorkFlowNodeEntity jcWorkFlowNode = jcWorkFlowNodeMapper.getDataByDefinationInitDept(1, loginUser.getDeptId().toString());
        if (jcWorkFlowNode == null) {
            result.setSuccess(false);
            result.setMessage("没有匹配的工作流程");
            return result;
        }
        BaseDept baseDept = null;
        if (jcWorkFlowNode != null && jcWorkFlowNode.getNodeType() != 3) {
            //根据下个节点的权限处理部门和处理部门角色查询可以发送的部门用户
            long workFlowDeptRoleNext = jcWorkFlowNode.getWorkFlowDeptRoleNext();
            baseDept = baseDeptMapper.getPrevDeptByDeptAndWorkFlowDeptRole(loginUser.getDeptId(), workFlowDeptRoleNext);
        }
        map.put("baseDept", baseDept);
    //设置可以发送的部门用户
        result.setSuccess(true);
        result.setData(map);
        result.setMessage("查询成功");
        return result;
    }*/

    @Override
    public Map<String, Object> getBlackListBySrequestId(long sRequestId) {
        return blackListRequestMapper.getBlackListBySrequestId(sRequestId);
    }

}