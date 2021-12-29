package com.tuozhi.zhlw.admin.jcApp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tuozhi.zhlw.admin.jc.entity.BlackListAttachment;
import com.tuozhi.zhlw.admin.jc.mapper.BlackListAttachmentMapper;
import com.tuozhi.zhlw.admin.jcApp.mapper.OwerMapper;
import com.tuozhi.zhlw.admin.jcApp.service.OwerService;
import com.tuozhi.zhlw.common.utils.RestfulHttpClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class OwerServiceImpl implements OwerService {
    @Value("${jcAppBlackListRequestAttachmentListUrl}")
    private String url;
    @Resource
    private OwerMapper owerMapper;
    @Resource
    private BlackListAttachmentMapper blackListAttachmentMapper;
    @Override
    public List<Map<String, Object>> getJCAppBlackAddOrBlackRevoked(Long userId,Integer logicType) {
        return owerMapper.getJCAppBlackAddOrBlackRevoked(userId,logicType);
    }

    //查询稽查APP黑名单，追缴名单的附件
    @Override
    public List<BlackListAttachment> getBlackListAddOrblackOrdersRevokedAttachment(Long requestId, String isApp) throws Exception {
        List<BlackListAttachment> list=new ArrayList();
        List<BlackListAttachment> merge=new ArrayList<>();
        if(!"1".equals(isApp)){//如果shuju为稽查App
            String s = RestfulHttpClient.sendHttpRequest(url + "?requestId=" + requestId);
            Map map = new ObjectMapper().readValue(s, Map.class);
            if("true".equals(map.get("success").toString())){
                list = (List) map.get("data");
            }
        }else{
             list = blackListAttachmentMapper.getAppDataByRequestId(requestId);
        }
        List<BlackListAttachment> bzAppDataByRequestId = blackListAttachmentMapper.getBZAppDataByRequestId(null, requestId);
        merge.addAll(list);
        merge.addAll(bzAppDataByRequestId);
        return merge;
    }

    @Override
    public List<Map<String,Object>> findEtcBlackList(String etcParams) {
       return owerMapper.findEtcBlackList(etcParams);
    }
}
