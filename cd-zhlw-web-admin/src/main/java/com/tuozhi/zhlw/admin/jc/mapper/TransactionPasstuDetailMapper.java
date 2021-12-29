package com.tuozhi.zhlw.admin.jc.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface TransactionPasstuDetailMapper {
    List<Map<String, Object>> getTransactionPasstuDetailInfo(Map<String, Object> conditions);
    Long selectAllDataCount(Map<String, Object> conditions);
    List<Map<String,Object>> getDataByPassid(@Param("passid") String passid);
}
