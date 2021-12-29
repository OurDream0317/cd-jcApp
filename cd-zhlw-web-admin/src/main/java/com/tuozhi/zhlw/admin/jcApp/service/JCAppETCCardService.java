package com.tuozhi.zhlw.admin.jcApp.service;

import com.tuozhi.zhlw.admin.jcApp.entity.JCAppLabelTypeEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;
import java.util.Map;

public interface JCAppETCCardService {
   int addJCAppETCCard( Map map);
   List<Map<String,Object>> getJCAppETCCard( Long userId);
   int addJCAppLabelType(Map<String,Object> map);
   List<JCAppLabelTypeEntity> getJCAppLabelType(Long userId);
   List<Map<String,Object>> getAllUser();
   List<Map<String,Object>> getSendorAcceptMessageByUserId( Long userId);
   List<Map<String,Object>> getMessageDetial( Long sendUserId, Long acceptUserId);
   Integer insertSendMessage(Long sendUserId,Long acceptUserId,String message);
   List<Map<String,Object>> getStationByProvincename(String provincename,String stationname);
   ResultMsg getNewVersionFilePath();
}
