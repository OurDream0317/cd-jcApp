package com.tuozhi.zhlw.admin.jc.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Select;

public interface RepairApplyMapper {
	
	
	@Select("select * from (select rownum as row_id,x.* from JC_REPAIR_APPLY x where rownum<=${pageno}*${pagesize}+1)"
			+ " where row_id>${pageno-1}*${pagesize} ")
	List<Map<String, Object>> selectRepairApply(Map<String, Object> conditions);
	
	@Select("SELECT count(*) FROM JC_REPAIR_APPLY")
	Integer selectRepairApplyQuan(Map<String, Object> conditions);

}


/**
 * 
 * @Update("Update  JC_REPAIR_APPLY set "
			+ "CAR_TYPE=#{CAR_TYPE},CAR_PLATE=#{CAR_PLATE},"
	 		+ "REPAIR_NAME=#{REPAIR_NAME},"
	 		+ "REPAIR_ID_CARD_NUM=#{REPAIR_ID_CARD_NUM},REPAIR_CONTACT_INFORMATION=#{REPAIR_CONTACT_INFORMATION},"
	 		+ "APPLY_NAME=#{APPLY_NAME},APPLY_ID_CARD_NUM=#{APPLY_ID_CARD_NUM},"
	 		+ "APPLY_CONTACT_INFORMATION=#{APPLY_CONTACT_INFORMATION}" where  REPAIR_APPLY_ID=#{REPAIR_APPLY_ID})
 * */
 