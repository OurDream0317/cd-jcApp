package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.PayBackResultEntity;
import com.tuozhi.zhlw.admin.entity.RepairFeeDataInfoEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseAgencyEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseSectionEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseTollStationEntity;
import com.tuozhi.zhlw.admin.pojo.LoginUser;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author liyuyan
 *
 */
public interface PayBackResultService {
	//新增
	ResultMsg save(PayBackResultEntity paybackentity, String platecolorId, String repairFeeDatas,Integer design);
	//查询
	ResultExtGrid findResultAll(PayBackResultEntity payBackResultEntity, QueryParams queryParams);

	/**
	 * 查询车辆补费数据信息
	 * @return
	 */
	List<RepairFeeDataInfoEntity> findRepairFeeData(Long sid);

	/**
	 * 查询收费路段\收费站的值
	 */
	List<BaseSectionEntity> findChargeRoad();

	List<Map<String,Object>> findRoadEnumOfFund( LoginUser loginUser);

	List<Map<String,Object>> findRoadEnumOfGrey( LoginUser loginUser);

	List<BaseTollStationEntity> findTollEnum(String sectionId);

	/**
	 * 查询经办单位编码
	 */
	List<BaseAgencyEntity> findBaseAgency();
	
	/**
	 * 导出查询
	 * */
	List<PayBackResultEntity> createExcelLocalPay(Map<String, Object> conditions);
}
