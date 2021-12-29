package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;

public interface RepairApplyService {

	List<Map<String, Object>> selectRepairApply(Map<String, Object> conditions);

	Integer selectRepairApplyQuan(Map<String, Object> conditions);

}
