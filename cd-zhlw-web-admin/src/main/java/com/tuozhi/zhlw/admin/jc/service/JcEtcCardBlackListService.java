package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListHistoryEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;

/**
 * @ClassName JcEtcCardBlackListService
 * @Descriotion TODO ETC发行黑名单
 * @Author liyuyan
 * @Date 2019/11/29 10:55
 * @Version 1.0
 */
public interface JcEtcCardBlackListService {

    /**
     * 查询所有Etc发行黑名单
     * @param jcEtcCardBlackListEntity
     * @return
     */
    ResultExtGrid findEtcBlackListAll(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, QueryParams queryParams);

    /**
     * 根据编号查询Etc黑名单历史记录
     * @param avtId
     * @return
     */
    List<JcEtcCardBlackListHistoryEntity> findEtcBlackHistoryByAvtId(Long avtId);

    /**
     * ETC黑名单添加
     * @param jcEtcCardBlackListEntity
     * @return
     */
    ResultMsg addEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser);

    void changeEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser);

    /**
     * 删除、转白操作
     */
    ResultMsg delEtcCardBlackList(Long avtId, Integer flag);

    /**
     * 根据车牌号、车牌颜色查询
     * @param vehicleLicense
     * @param vehicleColor
     * @return
     */
    ResultMsg findBlackByVehicleId(String vehicleLicense, String vehicleColor, Double sumMoneny);

    /**
     * 更新新增的Etc黑名单
     * @param jcEtcCardBlackListEntity
     * @return
     */
    ResultMsg updateEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity, LoginUser loginUser);
}
