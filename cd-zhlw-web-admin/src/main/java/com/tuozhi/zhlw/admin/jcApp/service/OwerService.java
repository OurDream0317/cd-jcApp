package com.tuozhi.zhlw.admin.jcApp.service;

import java.util.List;
import java.util.Map;

public interface OwerService {
    List<Map<String,Object>> getJCAppBlackAddOrBlackRevoked(Long userId,Integer logicType);
    List getBlackListAddOrblackOrdersRevokedAttachment(Long requestId,String isApp) throws Exception;
    List<Map<String,Object>> findEtcBlackList(String etcParams);
}
