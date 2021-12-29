package com.tuozhi.zhlw.admin.jcApp.service.impl;

import com.tuozhi.zhlw.admin.jcApp.entity.JCAppLabelTypeEntity;
import com.tuozhi.zhlw.admin.jcApp.mapper.JCAppETCCardMapper;
import com.tuozhi.zhlw.admin.jcApp.service.JCAppETCCardService;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class JCAppETCCardServiceImpl implements JCAppETCCardService {
    @Resource
    JCAppETCCardMapper mapper;
    @Override
    public int addJCAppETCCard( Map map) {
        return mapper.addJCAppETCCard(map);
    }

    @Override
    public List<Map<String, Object>> getJCAppETCCard(Long userId) {
        return mapper.getJCAppETCCard(userId);
    }

    @Override
    public int addJCAppLabelType(Map<String, Object> map) {
        return mapper.addJCAppLabelType(map);
    }

    @Override
    public List<JCAppLabelTypeEntity> getJCAppLabelType(Long userId) {
        return mapper.getJCAppLabelType(userId);
    }

    @Override
    public List<Map<String, Object>> getAllUser() {
        return mapper.getAllUser();
    }

    @Override
    public List<Map<String, Object>> getMessageDetial(Long sendUserId, Long acceptUserId) {
        return mapper.getMessageDetial(sendUserId,acceptUserId);
    }

    @Override
    public List<Map<String, Object>> getSendorAcceptMessageByUserId( Long userId) {
        return mapper.getSendorAcceptMessageByUserId(userId);
    }

    @Override
    public Integer insertSendMessage(Long sendUserId,Long acceptUserId,String message) {
        return mapper.insertSendMessage(sendUserId,acceptUserId,message);
    }

    @Override
    public List<Map<String, Object>> getStationByProvincename(String provincename,String stationname) {
        return mapper.getStationByProvincename(provincename,stationname);
    }


    @Override
    public ResultMsg getNewVersionFilePath() {
        ResultMsg result = new ResultMsg();
        Map<String, Object> newVersionFilePath = mapper.getNewVersionFilePath();
        result.setSuccess(true);
        result.setData(newVersionFilePath);
        return result;
    }
}
