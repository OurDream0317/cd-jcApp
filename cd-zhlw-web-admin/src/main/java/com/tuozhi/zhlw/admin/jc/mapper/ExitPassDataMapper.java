package com.tuozhi.zhlw.admin.jc.mapper;

import com.tuozhi.zhlw.admin.jc.entity.JCNewTradePassDate;
import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface ExitPassDataMapper {
    List<TradePassEntity> getTradePassDataByParams(Map<String, Object> conditions);
    long selectAllDataCount(Map<String, Object> conditions);
    List<Map<String,Object>> selectNewTradePassListExport(Map<String, Object> conditions);
    Integer getNewDataByPassId(@Param("passId") String passId);
    void updateDataByPassId(JCNewTradePassDate jcnewtradepassdate);
    void insertTransactionPasstuDetail(@Param("passId") String passId);//@Param("transactionPasstuDetailInfoList") List<Map<String, Object>> transactionPasstuDetailInfoList
    Map<String,Object> selectMinFee(Map<String, Object> params);
    Map getMultiprovince(@Param("passId") String passId);
    Double getFeeSP(@Param("passId") String passId);
    List<Map<String,Object>> splitAmountByProvinceOfETC(@Param("passId") String passId);
    List<Map<String,Object>> splitAmountByProvinceOfCPC(@Param("listId") String listId);
}
