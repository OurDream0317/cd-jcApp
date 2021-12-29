package com.tuozhi.zhlw.admin.jc.mapper;


import com.tuozhi.zhlw.admin.jc.entity.BlackListAfterPayment;
import com.tuozhi.zhlw.admin.jc.entity.BlackListRequest;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019-08-26 13:04
 **/


public interface BlackListRequestMapper {

    List<BlackListRequest> findAll();
    Map<String,Object> getCountByVehicle(Map<String, Object> map);

    List<String>  getJCBlackAttachmentByRequestId(Map<String, Object> map);

    List<BlackListRequest> getAll(Map<String, Object> conditions);

    int save(BlackListRequest blackListRequest);

    int insert(BlackListRequest blackListRequest);

    boolean deleteBlackListInfo(@Param("requestId") List<Integer> integetlist);

    List<BlackListRequest> findAllById(Map<String, Object> conditions);

    boolean updateBlackList(BlackListRequest blackListRequest);

    int saveBlackListForm(BlackListRequest blackListRequest);

    List<BlackListRequest> findAllRevocation();

    /**
     * 获取 黑名单添加申请 数据根据参数
     *
     * @param blackListRequest
     * @param requestStatuss1
     * @param requestStatuss2
     * @param requestDeptStatus
     * @param deptId
     * @return
     */
    List<BlackListRequest> getDataByParam(@Param("blackListRequest") BlackListRequest blackListRequest,@Param("revokeStatus")Integer revokeStatus, @Param("requestStatuss1") List<Integer> requestStatuss1, @Param("requestStatuss2") List<Integer> requestStatuss2, @Param("requestDeptStatus") Integer requestDeptStatus, @Param("deptId") Long deptId);

    /**
     * 获取 黑名单添加申请 数据根据参数（导出数据时使用）
     *
     * @param blackListRequest
     * @param requestStatuss1
     * @param requestStatuss2
     * @param requestDeptStatus
     * @param deptId
     * @return
     */
    List<BlackListRequest> getExportDataByParam(@Param("blackListRequest") BlackListRequest blackListRequest,@Param("revokeStatus") Integer revokeStatus, @Param("requestStatuss1") List<Integer> requestStatuss1, @Param("requestStatuss2") List<Integer> requestStatuss2, @Param("requestDeptStatus") Integer requestDeptStatus, @Param("deptId") Long deptId);

    List<BlackListRequest> exportBlacklistVehicleRevocation(@Param("blackListRequest") BlackListRequest blackListRequest, @Param("requestStatuss1") List<Integer> requestStatuss1, @Param("requestStatuss2") List<Integer> requestStatuss2, @Param("requestDeptStatus") Integer requestDeptStatus, @Param("deptId") Long deptId);
    /**
     * 获取 黑名单申请 根据编号
     *
     * @param requestId
     * @return
     */
    BlackListRequest getDataByRequestId(@Param("requestId") Long requestId);

    /**
     * 更新 黑名单申请表
     *
     * @param blackListRequest
     * @return
     */
    int updateDataByRequestId(BlackListRequest blackListRequest);

    /**
     * 新增 黑名单添加申请数据
     *
     * @param blackListRequest
     * @return
     */
    int insertData(BlackListRequest blackListRequest);

    /**
     * 获取 路段汇总的数据 根据创建时间
     *
     * @param beginTime
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getExsectionSumData(@Param("beginTime") Date beginTime, @Param("endDate") Date endDate);

    /**
     * 获取 逃费类型的数据 根据创建时间
     *
     * @param beginTime
     * @param endDate
     * @return
     */
    List<Map<String, Object>> getEludeMoneyTypeSumData(@Param("beginTime") Date beginTime, @Param("endDate") Date endDate);

    /**
     * 获取 黑名单数据 根据多个黑名单ID
     *
     * @param requestIdList
     * @return
     */
    List<BlackListRequest> getDataByRequestIdList(List<Long> requestIdList);

    /**
     * 获取 稽核追缴黑名单车辆通行费交接明细表 数据
     *
     * @return
     */
    List<Map<String, Object>> getConnectData(@Param("beginTime") Date beginTime, @Param("endDate") Date endDate);

    /**
     * 黑名单添加申请-新增
     */
    int uptBlackListAddRequest(BlackListRequest blackListRequest);

    /**
     * 黑名单撤销申请-新增
     *
     * @param blackListRequest
     * @return
     */
    int addBlackRevoked(BlackListRequest blackListRequest);

    /**
     * 更新当前环节编号
     *
     * @param flowpathId
     * @return
     */
    int uptFlowPathId(@Param("flowpathId") Integer flowpathId, @Param("requestId") Long requestId);

    /**
     * 修改
     */
    int uptBlackListRequest(BlackListRequest blackListRequest);

    /**
     * 删除
     */
    int delBlackRequest(@Param("requestIds") List<Long> requestIds);

    /**
     * 查询黑名单撤销申请车辆，根据requestId
     */
    BlackListRequest findByRequestId(@Param("requestId") Long requestId);

    /**
     * 根据收费站名称查询
     *
     * @param name
     * @return
     */
    List<Map<String, Object>> getStationByName(@Param("name") String name);
    List<Map<String, Object>> newGetStationByName(@Param("name") String name,@Param("deptId") Long deptId);

    List selectALLLog(@Param("carNumber") String carNumber, @Param("carColour") Integer carColour, @Param("flag") Boolean flag);

    /**
     * 查询黑名单申请添加的车辆，根据车牌号、车牌颜色
     *
     * @param carNumber
     * @param carColour
     * @return
     */
    BlackListRequest getRevokeBlackList(@Param("carNumber") String carNumber, @Param("carColour") Integer carColour);

    List<Map<String,Object>> searchBlackListNo(Map<String,Object> param);

    /**
     *查询黑名单退费补缴证据数据
     */
    List<Map<String,Object>> getBlackListAfterPaymentData(Map<String,Object> conditions);

    /**
     *查询黑名单退费补缴证据数据总条数
     */
    Long getBlackListAfterPaymentDataCount(Map<String,Object> conditions);

    Integer saveBlackListAfterPaymentData(BlackListAfterPayment pay);

    Integer deleteBlackListAfterPaymentData(@Param("ids") long[] ids);

    Map<String,Object> getBlackListBySrequestId(@Param("sRequestId") long sRequestId);

}
