package com.tuozhi.zhlw.admin.mapper;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.TesEtcPassDataEntity;
import com.tuozhi.zhlw.admin.entity.TesSuspectDateEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseIssuerEntity;
import org.apache.ibatis.annotations.Param;

import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;

/**
 * 
 * @author liyuyan
 *
 */
public interface TesAssistCheckMapper{
	
	//查询
	List<TesAssistCheckEntity> findAll(@Param("vehicleid") String vehicleid, @Param("sid") Long sid);
	
	//新增
	int save(TesAssistCheckEntity assistCheckentity);

	/**
	 * 保存嫌疑数据
	 * @param tesSuspectDateEntity
	 * @return
	 */
	int addEvidenceData(TesSuspectDateEntity tesSuspectDateEntity);
	
	//删除
	int deleteByPlate(Long sid);

	/**
	 * 查询发行方服务编号
	 * @return
	 */
	List<BaseIssuerEntity> findIssuerData();
}
