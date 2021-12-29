package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.GtyBillingTransationEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface GtyBillingTransationInfoMapper {
    List<GtyBillingTransationEntity> getGtyBillingTransationInfo(Map<String, Object> conditions);
    Long selectAllDataCount(Map<String, Object> conditions);
    List<GtyBillingTransationEntity> getGtyBillingTransationInfoByPassId(@Param("passid") String passid);
    List<Map<String,Object>> newCreateExcel(Map<String, Object> conditions);
}
