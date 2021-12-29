package com.tuozhi.zhlw.admin.jc.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * @ClassName TechFeerateIntervalMapper
 * @Descriotion TODO 收费单元车型基本费率表Mapper
 * @Author fujiankang
 * @Date 2019/11/14 16:11
 * @Version 1.0
 */
public interface TechFeerateIntervalMapper {


    double getActualFee(@Param("tollintervalId") String tollintervalId, @Param("formerVehicleType") String formerVehicleType, @Param("newVehicleType") Integer newVehicleType, @Param("orginalFee") Double orginalFee);
    /**
     * 获取通行费用 根据收费单元、旧的收费实际车型和新的收费实际车型、实收金额计算
     *
     * @param tollintervalId
     * @param newVehicleType
     * @paramversion（版本号版本号 (2 位省域编码+YYYYMMDD+3位顺序码)）
     * @return
     */
    double getActualFeeByVersion(@Param("tollintervalId") String[] tollintervalId, @Param("newVehicleType") Integer newVehicleType, @Param("version") String version);

    String getMaxVersion();

}
