package com.tuozhi.zhlw.admin.jc.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.BlackListAfterPayment;
import com.tuozhi.zhlw.admin.jc.entity.BlackListRequest;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @ClassName BlacklistRequestQueryService
 * @Descriotion TODO 黑名单申请查询Service接口
 * @Author fujiankang
 * @Date 2019/11/1 14:15
 * @Version 1.0
 */
public interface BlacklistRequestQueryService {
    /**
     * 获取 黑名单申请列表 根据参数
     *
     * @param blackListRequest
     * @param requestStatuss
     * @param ifCarRecord
     * @param deptId
     * @param queryParams
     * @return
     */
    ResultExtGrid getBlacklistRequestListByParam(BlackListRequest blackListRequest,Integer revokeStatus, String requestStatuss, String ifCarRecord, Long deptId, QueryParams queryParams, Boolean flag, Integer all);

    /**
     * 获取 黑名单添加申请 数据根据参数（导出数据时使用）
     *
     * @param blackListRequest
     * @param requestStatuss
     * @param ifCarRecord
     * @param deptId
     * @return
     */
    ResultMsg getExportBlacklistRequestListByParam(BlackListRequest blackListRequest, Integer revokeStatus,String requestStatuss, String ifCarRecord, Long deptId);

    /**
     * 获取 黑名单申请数据 根据编号
     *
     * @param requestId
     * @return
     */
    ResultMsg getBlacklistRequest(Long requestId);

    /**
     * 获取 黑名单申请流程 的数据根据黑名单申请编号
     *
     * @param requestId
     * @return
     */
    ResultMsg getBlackListRequestFlowPathList(Long requestId);





    /**
     * 获取 可以上传的部门用户
     *
     * @param flowPathId
     * @param requestId
     * @param loginUser
     * @return
     */
    ResultMsg getMaySendDeptUser(Integer flowPathId, Long requestId, LoginUser loginUser);



    /**
     * 发送到下个流程节点
     *
     * @param requestId
     * @param flowPathId
     * @param deptId
     * @param loginUser
     * @param operateUserName
     * @param handleSuggestion
     * @return
     */
    ResultMsg sentBlackListRequest(Long requestId, Integer flowPathId, Long deptId, LoginUser loginUser, String operateUserName, String handleSuggestion);

    /**
     * 退回上个流程环节节点
     *
     * @param requestId
     * @param flowPathId
     * @param operateUserName
     * @param handleSuggestion
     * @return
     */
    ResultMsg sendBackBlackListRequest(Long requestId, Integer flowPathId, String operateUserName, String handleSuggestion);

    /**
     * 办结（办结和拒绝办结）
     *
     * @param requestId
     * @param flowPathId
     * @param requestStatus
     * @param operateUserName
     * @param handleSuggestion
     * @return
     */
    ResultMsg endBlackListRequest(Long requestId, Integer flowPathId, Integer requestStatus, String operateUserName, String handleSuggestion);

    /**
     * 保存黑名单添加申请数据
     *
     * @param blackListRequest
     * @param loginUser
     * @return
     */
    ResultMsg saveBlackListAddRequest(BlackListRequest blackListRequest, LoginUser loginUser, MultipartFile[] file) throws IOException;





    /**
     * 获取 黑名单附件表中的图片
     *
     * @param attachmentId
     * @return
     */
    ResultMsg getFileImage(Integer attachmentId) throws UnsupportedEncodingException;

    /**
     * 获取 路段汇总的数据 根据创建时间
     *
     * @param beginTime
     * @param endDate
     * @param queryParams
     * @return
     */
    ResultExtGrid getExsectionSumData(Date beginTime, Date endDate, QueryParams queryParams);

    /**
     * 获取 逃费类型的数据 根据创建时间
     *
     * @param beginTime
     * @param endDate
     * @param queryParams
     * @return
     */
    ResultExtGrid getEludeMoneyTypeSumData(Date beginTime, Date endDate, QueryParams queryParams);

    /**
     * 导出黑名单路段统计表
     *
     * @param beginTime
     * @param endDate
     * @return
     */
    void exportBlacklistExsectionSum(Date beginTime, Date endDate) throws Exception;

    /**
     * 导出黑名单逃费类型统计表
     *
     * @param beginTime
     * @param endDate
     * @return
     */
    void exportBlacklistEludeMoneyTypeSum(Date beginTime, Date endDate) throws Exception;

    /**
     * 导出选中黑名单数据
     *
     * @param requestIdList
     * @return
     */
    ResultMsg<List<List<String>>> exportBlacklistByRequestIdList(List<Long> requestIdList);
    ResultMsg<List<List<String>>> exportBlacklistVehicleRevocation(BlackListRequest blackListRequest, String requestStatuss, Long deptId);
    /**
     * 获取 稽核追缴黑名单车辆通行费交接明细表 数据
     *
     * @param beginTime
     * @param endDate
     * @param queryParams
     * @return
     */
    ResultExtGrid getBlacklistConnectData(Date beginTime, Date endDate, QueryParams queryParams);

    /**
     * 导出稽核追缴黑名单车辆通行费交接明细表 数据
     *
     * @param beginTime
     * @param endDate
     * @return
     */
    ResultMsg<List<List<String>>> exportBlacklistConnectData(Date beginTime, Date endDate) throws ParseException;

    /**
     * 修改黑名单添加申请
     *
     * @param blackListRequest
     * @return
     */
    ResultMsg updateBlackListAddRequest(BlackListRequest blackListRequest);

    PageInfo<BlackListRequest> selectALLLog(QueryParams queryParams, String carNumber, Integer carColour, Boolean flag);


    
    List<Map<String,Object>> getBlackListAfterPaymentData(Map<String,Object> conditions);
    Long getBlackListAfterPaymentDataCount(Map<String,Object> conditions);
	ResultMsg<Map<String,Object>> readBlackListAfterPaymentExcelInfo(File file,Long requestId)throws ParseException;
	
	Integer saveBlackListAfterPaymentData(BlackListAfterPayment pay);
	Integer deleteBlackListAfterPaymentData(long[] ids);
 /*   ResultMsg getNextDept(LoginUser loginUser);*/
    void onExportLocalBlackExcel(HttpServletResponse response, Long requestId) throws Exception;

    Map<String,Object> getBlackListBySrequestId(long sRequestId);
}
