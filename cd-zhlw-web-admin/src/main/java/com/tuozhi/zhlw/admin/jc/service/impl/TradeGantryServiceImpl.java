package com.tuozhi.zhlw.admin.jc.service.impl;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.TradeGantryEntity;
import com.tuozhi.zhlw.admin.jc.mapper.TradeGantryInfoMapper;
import com.tuozhi.zhlw.admin.jc.service.TradeGantryService;
import com.tuozhi.zhlw.common.bean.QueryParams;



@Service
public class TradeGantryServiceImpl implements TradeGantryService{
	@Autowired
	TradeGantryInfoMapper mapper;
	@Override
	public PageInfo<TradeGantryEntity> selectTradeGantry(Map<String, Object> conditions, QueryParams queryParams) {
		// TODO Auto-generated method stub
		
		PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
		List<TradeGantryEntity> list =mapper.selectTradeGantry(conditions);
		return new PageInfo<TradeGantryEntity>(list);
	}
	@Override
	public List<TradeGantryEntity> selectTradeGantryList(Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		List<TradeGantryEntity> list =mapper.selectTradeGantryList(conditions);
		return list;
	}
	@Override
	public List<Map<String, Object>> selectpassid(Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		return mapper.selectpassid(conditions);
	}
	
	

}
