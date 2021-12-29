package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.jc.entity.BlackListRequest;
import com.tuozhi.zhlw.common.bean.ResultMsg;





public interface EtcCardBlackListService {


	List<Map<String, Object>> findAll(Map<String, Object> selectMap);
	Long selectAllDataCount(Map<String, Object> selectMap);
	
	ResultMsg findEnumCode();
	
	PageInfo<List<Map<String, Object>>> findPicture(Map<String, Object> conditions,Map<String, Object> selectMap);
	
	
	ResultMsg<List<List<String>>> exportEtcCardApply(Map<String, Object> selectMap,String workflowdeptrole);

	

}
