package com.tuozhi.zhlw.admin.mapper;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.*;
import org.apache.ibatis.annotations.Param;

import com.tuozhi.zhlw.common.mapper.MyMapper;

/**
 * 
 * @author liyuyan
 *
 */
public interface TesEtcPassDataMapper extends MyMapper<TesEtcPassDataEntity> {

	/**
	 * 出口ETC
	 * @param sid
	 * @return
	 */
	List<TesEtcPassDataEntity> findPassDataBySid(@Param("sid") Long sid);

	/**
	 * 出口刷卡
	 * @param sid
	 * @return
	 */
	List<TesEtcPassDataEntity> findSlotCardBySid(@Param("sid") Long sid);

	/**
	 * 出口其它
	 * @param sid
	 * @return
	 */
	List<TesEtcPassDataEntity> findTotherDataBySid(@Param("sid") Long sid);

	/**
	 * 入口通行
	 * @param sid
	 * @return
	 */
	List<TesEtcPassDataEntity> findEntryDataBySid(@Param("sid") Long sid);

	/**
	 * 查看嫌疑数据
	 * @param sid
	 * @return
	 */
	List<TesEtcPassDataEntity> findBySid(@Param("sid") Long sid);
}
