package com.tuozhi.zhlw.admin.jcApp.mapper;

import com.tuozhi.zhlw.admin.jcApp.entity.JCAppLabelTypeEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface JCAppETCCardMapper {
   int addJCAppETCCard(Map map);
   List<Map<String,Object>> getJCAppETCCard(@Param("userId") Long userId);
   int addJCAppLabelType(Map<String,Object> map);
   List<JCAppLabelTypeEntity> getJCAppLabelType(@Param("userId") long userId);
   List<Map<String,Object>> getAllUser();
   List<Map<String,Object>> getSendorAcceptMessageByUserId(@Param("userId") Long userId);
   List<Map<String,Object>> getMessageDetial(@Param("sendUserId") Long sendUserId,@Param("acceptUserId") Long acceptUserId);
   Integer insertSendMessage(@Param("sendUserId") Long sendUserId,@Param("acceptUserId") Long acceptUserId,@Param("message") String message);
   List<Map<String,Object>> getStationByProvincename(@Param("provincename") String provincename,@Param("stationname")String stationname);
   Map<String,Object> getNewVersionFilePath();
   void insertNewVersion(@Param("version") String version,@Param("filePath") String filePath);
}
