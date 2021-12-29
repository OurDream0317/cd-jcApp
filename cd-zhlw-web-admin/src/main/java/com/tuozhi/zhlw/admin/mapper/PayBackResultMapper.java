package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.PayBackResultEntity;
import com.tuozhi.zhlw.admin.entity.RepairFeeDataInfoEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseAgencyEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseSectionEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseTollStationEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author liyuyan
 *
 */
public interface PayBackResultMapper {

	/**
	 * 保存车辆补费结果
	 * @param paybackentity
	 * @return
	 */
	int save(PayBackResultEntity paybackentity);

	/**
	 * 保存补费数据
	 * @param repairFeeDataInfoEntity
	 * @return
	 */
	int saveRepairFeeInfo(RepairFeeDataInfoEntity repairFeeDataInfoEntity);

	/**
	 * 查询车辆补费结果信息
	 * @return
	 */
	List<PayBackResultEntity> findResultAll(PayBackResultEntity payBackResultEntity);

	int uptPepairFeePassId(Long sid);

	/**
	 * 查询车辆补费数据信息
	 * @return
	 */
	List<RepairFeeDataInfoEntity> findRepairFeeData(@Param("sid") Long sid);

	/**
	 * 查询经办路段\经办站枚举值
	 */
	List<BaseSectionEntity> findRoadEnum();
	List<Map<String,Object>> findRoadEnumOfFund(@Param("deptId") Long deptId);
	List<Map<String,Object>> findRoadEnumOfGrey(@Param("deptId") Long deptId);

	List<BaseTollStationEntity> findTollEnum(@Param("sectionId") String sectionId);

	/**
	 * 查询经办单位编码
	 */
	List<BaseAgencyEntity> findBaseAgency();
	/**
	 * 导出
	 * */
	List<PayBackResultEntity> createExcelLocalPay(Map<String, Object> conditions);
}
