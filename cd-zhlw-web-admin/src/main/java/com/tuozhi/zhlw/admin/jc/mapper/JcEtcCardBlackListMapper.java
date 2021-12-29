package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListEntity;
import com.tuozhi.zhlw.admin.jc.entity.JcEtcCardBlackListHistoryEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName JcEtcCardBlackListMapper
 * @Descriotion TODO ETC发行黑名单Mapper
 * @Author liyuyan
 * @Date 2019/11/29 10:02
 * @Version 1.0
 */
public interface JcEtcCardBlackListMapper {

    /**
     * 1、查询所有Etc黑名单信息 2、根据条件查询
     * @param jcEtcCardBlackListEntity
     * @return
     */
    List<JcEtcCardBlackListEntity> findEtcBlackListAll(JcEtcCardBlackListEntity jcEtcCardBlackListEntity);

    /**
     * 查询Etc黑名单历史记录，根据编号
     * @param avtId
     * @return
     */
    List<JcEtcCardBlackListHistoryEntity> findEtcBlackListHistroyAll(@Param("avtId") Long avtId);

    /**
     * 根据车牌号，车牌颜色查询
     * @param vehicleLicense
     * @param vehicleColor
     * @return
     */
    JcEtcCardBlackListEntity findBlackByVehicleId(@Param("vehicleLicense") String vehicleLicense,
                                                  @Param("vehicleColor") String vehicleColor,
                                                  @Param("sumMoneny") Double sumMoneny);

    /**
     * ETC黑名单添加
     * @param jcEtcCardBlackListEntity
     * @return
     */
    int addEtcCardBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity);

    /**
     * ETC黑名单历史记录添加
     * @param jcEtcCardBlackListEntity
     * @return
     */
    int addEtcBlackListHistory(JcEtcCardBlackListEntity jcEtcCardBlackListEntity);

    /**
     * 删除
     * @param avtId
     * @return
     */
    int delEtcBlackList(@Param("avtId") Long avtId);

    /**
     * 更新
     * @param avtId
     * @return
     */
    int uptEtcBlackList(@Param("avtId") Long avtId);

    /**
     * 更新ETC黑名单汇总金额
     * @param jcEtcCardBlackListEntity
     * @return
     */
    int updateBlackToll (JcEtcCardBlackListEntity jcEtcCardBlackListEntity);

    void changeEtcBlackList(JcEtcCardBlackListEntity jcEtcCardBlackListEntity);
}
