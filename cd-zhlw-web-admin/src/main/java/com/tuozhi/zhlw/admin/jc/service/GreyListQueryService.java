package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCGrayListAttachment;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCGrayListFlowPath;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCGrayListRequest;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCRoadBlackListFeedBack;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface GreyListQueryService {
    Long selectAllDataCount(Map map);
    List<JCGrayListRequest>  findGrayListRequestQuery(Map map);
    ResultMsg saveNewGrayList(JCGrayListRequest jcGrayListRequest, MultipartFile file, LoginUser loginUserForRedis, HttpServletRequest request);
    ResultMsg GetCurrentAndNextNodeData(Integer definationId, Integer CurrentNodeID, Integer requestId, String deptId);
    List findDeptByDeptId(String deptId, String[] arr2);
    JCGrayListRequest GrayListRequestDetails(Integer requestId);
    PageInfo<JCGrayListFlowPath>  findGrayListFlowPath(QueryParams queryParams, Integer requestId);
    PageInfo<JCGrayListAttachment>  GrayListAttachmentQuery(QueryParams queryParams, Integer requestId);
    JCGrayListAttachment findAllAttachmentByAttachmentId(Integer attachmentId);
    PageInfo<JCRoadBlackListFeedBack>  RoadBlackListFeedbackByCarNumber(QueryParams queryParams, String carNumber);
    Map findGreyListFlowPathByRequestId(Integer requestId);
    ResultMsg SentGreyListRequest(String deptWork, Integer requestId, String planContent, String NameOfHandler, Integer flowPathId);
    ResultMsg AgreeToAdivseOfGreyList(Integer requestStatus, Integer requestId, String planContent, String NameOfHandler, Integer flowPathId, Long userId, HttpServletRequest request);
    void selectReadTime(Integer requestId, String deptId);
    ResultMsg findHistoryFlowPath(Integer requestId, Integer flowPathId, String handleSuggestion, String OperateUserName);
    ResultMsg delAllByRequestId(Integer[] requestIds);
    ResultMsg editNewGrayList(JCGrayListRequest jcGrayListRequest);
    /**
     * 获取 可以上传的部门用户
     * @param flowPathId
     * @param requestId
     * @param loginUser
     * @return
     */
    ResultMsg getMaySendDeptUser(Integer flowPathId, Integer requestId, LoginUser loginUser);
    void saveAttachment(JCGrayListAttachment attachmentInform);
    void onAttchmentDelete(Integer attachmentId);
    Integer isShowBlack(Integer requestId, LoginUser loginUser);

    PageInfo<JCGrayListRequest> findGrayCarnumber(QueryParams queryParams, HttpServletRequest request);

    PageInfo<JCGrayListRequest> AllGrayListRequestQuery(QueryParams queryParams, Date checkTimeBegin, Date checkTimeEnd, String carNumber, Integer requestId, Integer licenseColorId, Integer[] requestStatusStr, LoginUser loginUser, boolean flag,String exTollRoadId);
     void greyListApplicationExport(Map map, HttpServletResponse response) throws IOException;
}
