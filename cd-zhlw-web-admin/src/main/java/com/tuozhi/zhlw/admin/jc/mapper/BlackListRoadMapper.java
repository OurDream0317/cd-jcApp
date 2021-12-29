package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.*;
import com.tuozhi.zhlw.admin.jc.entity.grayListEntity.JCGrayListAttachment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @ClassName BlackListRoadMapper
 * @Descriotion TODO 稽核下发黑名单车道Mapper
 * @Author liyuyan
 * @Date 2019/11/06 21:02
 * @Version 1.0
 */
public interface BlackListRoadMapper {

    //查询
    List<JcRoadBlackListEntity> findBlackListAll(JcRoadBlackListEntity jcRoadBlackListEntity);
    //查询黑名单历史记录
    List<JcRoadBlackListHistoryEntity> findBlackListHistroyAll(JcRoadBlackListEntity jcRoadBlackListEntity);
    //根据车牌号和车牌颜色查询
    JcRoadBlackListEntity findByVehicleId(@Param("vehicleLicense") String vehicleLicense,
                                                @Param("vehicleLicenseColor") Long vehicleLicenseColor);

    //新增
    int addRoadBlackList(JcRoadBlackListEntity roadBlackListEntity);
    int addBlackListHistory(JcRoadBlackListEntity jcRoadBlackListEntity);

    //删除车道黑名单信息
    int delBlackList(@Param("listId") String listId);
    //更新为灰名单不拦截，修改状态
    int uptIsGreyList(@Param("listId") String listId,@Param("htmlStr") String htmlStr,@Param("requestId") Long requestId);

    //获取UUID
    String findListId();

    //更新黑名单，根据车牌
    int updateBlackList(JcRoadBlackListEntity jcRoadBlackListEntity);

    //更新灰名单，根据车牌
    int updateGreyList(JcRoadBlackListEntity jcRoadBlackListEntity);

    int newUpdateGreyList(JcRoadBlackListEntity jcRoadBlackListEntity);
    /**
     * 导出-条件查询
     * @param selectmap
     * @return
     */
    List<Map<String, Object>> selectBlackGreyAll(Map<String, Object> selectmap);

    void deleteLocalGreyList(@Param("sids") long[] sids);
    /**
     * 查询黑名单证据附件
     * @param requestId
     * @return
     */
    List<BlackListAttachment> getDataByRequestId(@Param("requestId") Long requestId);

    /**
     * 查询灰名单证据文件
     * @param grayRequestId
     * @return
     */
    List<JCGrayListAttachment> getGreyAttachmentById(@Param("grayRequestId") Long grayRequestId);

    JcRoadBlackListTempEntity getLocalBlackListQueryDetailsBySid(@Param("sid") long sid);
    Integer addJCBlacklistPayment(JCBlacklistPayment jcBlacklistPayment);
    List<JCBlacklistPayment> getJCBlacklistPayment(@Param("vehicle") String vehicle,@Param("vehicleColor") Integer vehicleColor);
}
