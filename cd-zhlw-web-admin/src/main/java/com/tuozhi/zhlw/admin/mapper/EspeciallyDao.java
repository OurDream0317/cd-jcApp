package com.tuozhi.zhlw.admin.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EspeciallyDao {

    List<Map<String, String>> findContainerRecord(@Param("vehicleId") String vehicleId, @Param("etcCardId") String etcCardId);

    Integer insertEspeciallyCar(Map<String, Object> params);

    @Select("SELECT VEHICLEID FROM CD_PASS.SPECIAL_CAR WHERE  VEHICLEID = #{vehicleId,jdbcType=VARCHAR} " +
            "AND ROWNUM <= 1")
    Map<String, String> findRecordByPlate(String vehicleId);

    @Select("SELECT ETCCARDID FROM CD_PASS.SPECIAL_CAR WHERE  ETCCARDID =#{etcCarId,jdbcType=VARCHAR} " +
            "AND ROWNUM <= 1")
    Map<String, String> findRecordByCardId(String etcCarId);

    @Delete("DELETE CD_PASS.SPECIAL_CAR WHERE ID =#{ID,jdbcType=NUMERIC}")
    Integer deleteRecordById(Long id);
}
