package com.tuozhi.zhlw.admin.jcApp.mapper;

import com.alibaba.fastjson.JSONArray;
import com.tuozhi.zhlw.admin.jc.entity.EtctsEntrypassdata;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JCAuditStatusMapper {
    List<Map<String,Object>> getLocalBlackList(@Param("vehicle") String vehicle,@Param("nowDate") Date nowDate);
    List<Map<String,Object>> getLocalGreyList(@Param("vehicle") String vehicle,@Param("nowDate") Date nowDate);
    void insertJCAppAttachment(@Param("requestId") String requestId,@Param("type") String type,@Param("array") JSONArray array);
    void insertJCAppViw(Map map);
    List<Map<String,Object>> getBasePathByRequestId(@Param("requestId") Long requestId);
    List<Map<String,Object>> getJCBlacklistEvidenceDetails(@Param("required") String required);
    void updateJCBlacklistEvidenceDetailsBySid(@Param("requestId") String requestId);
    List<EtctsEntrypassdata> getEtctsEntrypassdataInfo(Map<String, Object> conditions);
}
