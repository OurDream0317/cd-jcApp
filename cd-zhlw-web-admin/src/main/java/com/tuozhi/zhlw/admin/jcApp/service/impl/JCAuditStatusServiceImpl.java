package com.tuozhi.zhlw.admin.jcApp.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.tuozhi.zhlw.admin.jc.entity.EtctsEntrypassdata;
import com.tuozhi.zhlw.admin.jcApp.mapper.JCAuditStatusMapper;
import com.tuozhi.zhlw.admin.jcApp.service.JCAuditStatusService;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.tuozhi.zhlw.admin.jcApp.util.getBase64String;
import static com.tuozhi.zhlw.admin.jcApp.util.getFileType;

@Service
public class JCAuditStatusServiceImpl implements JCAuditStatusService {
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Resource
    JCAuditStatusMapper mapper;

    @Override
    public List<Map<String, Object>> getLocalBlackList(String vehicle, Date nowDate) {
        return mapper.getLocalBlackList(vehicle,nowDate);
    }

    @Override
    public List<Map<String, Object>> getLocalGreyList(String vehicle,Date nowDate) {
        return mapper.getLocalGreyList(vehicle,nowDate);
    }

    @Override
    public ResultMsg insertJCAppAttachment(String requestId, String type, JSONArray array) {
        ResultMsg result = new ResultMsg();
        try {
            mapper.insertJCAppAttachment(requestId, type, array);
            result.setMessage("补证成功");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("补证失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public ResultMsg insertJCAppViw(MultipartFile[] files,String jsonStr, LoginUser loginUser) {
        ResultMsg result = new ResultMsg();
        Map map1 = JSONObject.parseObject(jsonStr, Map.class);
        try {
            for(MultipartFile file:files){
                Map map = new HashMap();
                map.put("requestId", Long.parseLong(map1.get("requestId").toString()));
                map.put("type",Integer.parseInt(map1.get("type").toString()));
                map.put("fileType", getFileType(file));
                map.put("base64Path", getBase64String(file));
                map.put("fileLength", file.getSize());
                map.put("fileName", file.getOriginalFilename());
                map.put("userId", loginUser.getUserId());
                map.put("userName", loginUser.getUserName());
                map.put("deptId", loginUser.getDeptId());
                map.put("deptName", loginUser.getDeptName());
                mapper.insertJCAppViw(map);
            }
            result.setMessage("补证成功");
            result.setSuccess(true);
        } catch (Exception e) {
            //手动设置回滚
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            result.setMessage("补证失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<Map<String, Object>> getBasePathByRequestId(Long requestId) {
        List<Map<String, Object>> basePathByRequestId = mapper.getBasePathByRequestId(requestId);
        String detailinfo = "";
        if (basePathByRequestId.size() > 0) {
            for (Map map : basePathByRequestId) {
                Clob clob = (Clob) map.get("ATTACHMENT");
                if (clob != null) {
                    try {
                        detailinfo = clob.getSubString((long) 1, (int) clob.length());
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                map.put("ATTACHMENT", detailinfo);
            }
        }
        return basePathByRequestId;
    }

    @Override
    public List<Map<String, Object>> getJCBlacklistEvidenceDetails(String required) {
        return mapper.getJCBlacklistEvidenceDetails(required);
    }

    @Override
    public ResultMsg updateJCBlacklistEvidenceDetailsBySid(String requestId) {
        ResultMsg result = new ResultMsg();
        try {
            mapper.updateJCBlacklistEvidenceDetailsBySid(requestId);
            result.setMessage("补费成功");
            result.setSuccess(true);
        } catch (Exception e) {
            result.setMessage("补费失败");
            result.setSuccess(false);
        }
        return result;
    }

    @Override
    public List<EtctsEntrypassdata> getEtctsEntrypassdataInfo(Map<String, Object> conditions) {
        List<EtctsEntrypassdata> etctsEntrypassdataInfo = mapper.getEtctsEntrypassdataInfo(conditions);
        return etctsEntrypassdataInfo;
    }
}
