package com.tuozhi.zhlw.admin.jc.service;

import com.tuozhi.zhlw.admin.jc.entity.TradePassEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface ExitPassDataService {
    List<TradePassEntity> getTradePassByParams(Map<String, Object> conditions);
    long selectAllDataCount(Map<String, Object> conditions);
    void selectNewTradePassListExport(Map<String, Object> conditions, HttpServletResponse response) throws Exception;
    ResultMsg newCarCurrentVehicleAmount(String passId, String version, Integer realVehicleType, Integer ifRoundOff,Integer isFee);
    List<Map<String,Object>> splitAmountByProvince(String passId,String listId,Integer mediatype);
}
