package com.tuozhi.zhlw.admin.jc.mapper;

import java.util.List;
import java.util.Map;

import com.tuozhi.zhlw.admin.jc.entity.TradeGantryEntity;

public interface TradeGantryInfoMapper {

	List<TradeGantryEntity> selectTradeGantry(Map<String, Object> conditions);

	List<TradeGantryEntity> selectTradeGantryList(Map<String, Object> conditions);

	List<Map<String, Object>> selectpassid(Map<String, Object> conditions);

}
