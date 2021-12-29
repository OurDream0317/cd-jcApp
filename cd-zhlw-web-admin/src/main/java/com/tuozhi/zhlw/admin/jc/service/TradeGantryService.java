package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.TradeGantryEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;

public interface TradeGantryService {

	PageInfo<TradeGantryEntity> selectTradeGantry(Map<String, Object> conditions, QueryParams queryParams);

	List<TradeGantryEntity> selectTradeGantryList(Map<String, Object> conditions);

	List<Map<String, Object>> selectpassid(Map<String, Object> conditions);



}
