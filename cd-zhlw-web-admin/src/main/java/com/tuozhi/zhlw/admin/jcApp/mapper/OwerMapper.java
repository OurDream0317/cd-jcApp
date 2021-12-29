package com.tuozhi.zhlw.admin.jcApp.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OwerMapper {
    List<Map<String,Object>> getJCAppBlackAddOrBlackRevoked(@Param("userId") Long userId,@Param("logicType") Integer logicType);
    List<Map<String,Object>> findEtcBlackList(@Param("etcParams") String etcParams);
}
