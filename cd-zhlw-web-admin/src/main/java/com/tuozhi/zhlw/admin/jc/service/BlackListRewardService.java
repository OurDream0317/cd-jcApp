package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;


public interface BlackListRewardService {

	List<Map<String,Object>> getBlackListRewardDataByCondition(Map<String, Object> condition);
	Long getBlackListRewardTotalCountByCondition(Map<String, Object> condition);
	String exportBlackListRewardDataExcel(List<Map<String, Object>> fundsRequestsData);
}
