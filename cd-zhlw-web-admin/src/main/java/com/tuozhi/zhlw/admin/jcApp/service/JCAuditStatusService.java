package com.tuozhi.zhlw.admin.jcApp.service;

import com.alibaba.fastjson.JSONArray;
import com.tuozhi.zhlw.admin.jc.entity.EtctsEntrypassdata;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface JCAuditStatusService {
    List<Map<String,Object>> getLocalBlackList(String vehicle, Date nowDate);
    List<Map<String,Object>> getLocalGreyList(String vehicle,Date nowDate);
    ResultMsg insertJCAppAttachment(String requestId, String type ,JSONArray array);
    ResultMsg insertJCAppViw(MultipartFile[] files,String jsonStr, LoginUser loginUser);
    List<Map<String,Object>> getBasePathByRequestId( Long requestId);
    List<Map<String,Object>> getJCBlacklistEvidenceDetails(String required);
    ResultMsg updateJCBlacklistEvidenceDetailsBySid(String requestId);
    List<EtctsEntrypassdata> getEtctsEntrypassdataInfo(Map<String, Object> conditions);
}
