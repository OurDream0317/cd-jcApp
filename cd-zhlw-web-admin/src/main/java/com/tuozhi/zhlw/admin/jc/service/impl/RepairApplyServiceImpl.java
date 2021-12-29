package com.tuozhi.zhlw.admin.jc.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.tuozhi.zhlw.admin.jc.mapper.RepairApplyMapper;
import com.tuozhi.zhlw.admin.jc.service.RepairApplyService;


@Service
public class RepairApplyServiceImpl  implements RepairApplyService{

	@Resource
	RepairApplyMapper mapper;
	@Override
	public List<Map<String, Object>> selectRepairApply(Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		return mapper.selectRepairApply(conditions);
	}

	@Override
	public Integer selectRepairApplyQuan(Map<String, Object> conditions) {
		// TODO Auto-generated method stub
		return mapper.selectRepairApplyQuan(conditions);
	}

}
