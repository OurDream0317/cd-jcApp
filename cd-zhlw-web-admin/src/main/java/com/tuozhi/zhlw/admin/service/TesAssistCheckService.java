package com.tuozhi.zhlw.admin.service;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.admin.entity.TesSuspectDateEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseIssuerEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;

/**
 * 
 * @author liyuyan
 *
 */
public interface TesAssistCheckService{

	/**
	 * 查询所有协查车辆信息
	 * @param vehicleid
	 * @param sid
	 * @return
	 */
	ResultExtGrid findAll(String vehicleid, Long sid, QueryParams queryParams);

	/**
	 * 保存车辆协查、嫌疑的数据
	 * @param assistCheckEntity
	 * @param tesSuspectDateEntity
	 * @return
	 */
	ResultMsg save(TesAssistCheckEntity assistCheckEntity,TesSuspectDateEntity tesSuspectDateEntity);

	/**
	 * 删除
	 * @param sid
	 * @return
	 */
	boolean deleteUserByMark(Long sid);

	/**
	 * 查询发行方服务编号
	 * @return
	 */
	List<BaseIssuerEntity> findIssuerData();
}
