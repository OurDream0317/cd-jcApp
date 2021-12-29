package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.admin.jc.entity.JCBlacklistPayment;
import com.tuozhi.zhlw.admin.jc.entity.JcRoadBlackListEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcRoadBlackListTempEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.io.File;
import java.util.Map;

/**
 * @ClassName BlackListRoadService
 * @Descriotion TODO 稽核下发黑名单车道
 * @Author liyuyan
 * @Date 2019/11/06 19:11
 * @Version 1.0
 */
public interface BlackListRoadService {

    /**
     * 黑名单车道添加,并同步添加黑名单历史表中
     * @param roadBlackListEntity
     * @return
     */
    ResultMsg saveRoadBlackList(JcRoadBlackListEntity roadBlackListEntity);


    /**
     * 查询所有黑名单信息
     * @return
     */
    ResultExtGrid findBlackListAll(JcRoadBlackListEntity jcRoadBlackListEntity, QueryParams queryParams);

    /**
     * 查询黑名单历史
     * @param sid
     * @return
     */
    ResultMsg findHistoryAll(Long sid, String vehicleLicense, Long vehicleLicenseColor, Double evadeToll, String htmlStr, Long requestId);

    /**
     * 查询黑名单历史,根据uuid
     * @param listId
     * @return
     */
    ResultExtGrid findHistoryByListId(JcRoadBlackListEntity jcRoadBlackListEntity, QueryParams queryParams);

    /**
     * 根据车牌号、车牌颜色查询
     * @param vehicleLicense
     * @param vehicleLicenseColor
     * @return
     */
    ResultMsg findByVehicleId(String vehicleLicense,Long vehicleLicenseColor);


    /**
     * 更新添加的车道黑名单
     * @param roadBlackListEntity
     * @return
     */
    ResultMsg updateRoadBlackList(JcRoadBlackListEntity roadBlackListEntity);

    /**
     * 更新添加的车道黑名单（修改按钮）
     * @param roadBlackListEntity
     * @return
     */
    ResultMsg newUpdateRoadBlackList(JcRoadBlackListEntity roadBlackListEntity);

    /**
     * 将查询结果导出excel
     * @param selectMap
     * @return
     */
    String exportLocalBlackExcel(Map<String, Object> selectMap);

    /**
     * 删除
     * @params sids
     */
    void deleteLocalGreyList(long[] sids);
    /**
     * 根据ID查询黑名单证据文件
     * @param requestId
     * @return
     */
    ResultExtGrid getAttachmentById (Long requestId, QueryParams queryParams);

    /**
     * 根据ID查询灰名单证据文件
     * @param grayRequestId
     * @return
     */
    ResultExtGrid getGreyAttachmentById (Long grayRequestId, QueryParams queryParams);

    /**
     * 导入Excel
     * @param fileName
     * @return
     */
    ResultMsg readBlackListExcelInfo(File fileName);

    JcRoadBlackListTempEntity getLocalBlackListQueryDetails(Long sid);

    Integer addJCBlacklistPayment(JCBlacklistPayment jcBlacklistPayment,String listId);

    ResultExtGrid getJCBlacklistPayment(QueryParams queryParams,String vehicle,Integer vehicleColor);
}
