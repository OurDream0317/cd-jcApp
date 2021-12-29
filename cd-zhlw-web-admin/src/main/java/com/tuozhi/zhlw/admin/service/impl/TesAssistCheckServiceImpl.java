package com.tuozhi.zhlw.admin.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.TesAssistCheckEntity;
import com.tuozhi.zhlw.admin.entity.TesSuspectDateEntity;
import com.tuozhi.zhlw.admin.jc.entity.BaseIssuerEntity;
import com.tuozhi.zhlw.admin.mapper.TesAssistCheckMapper;
import com.tuozhi.zhlw.admin.service.TesAssistCheckService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import com.tuozhi.zhlw.common.bean.ResultExtGrid;
import com.tuozhi.zhlw.common.bean.ResultMsg;
import com.tuozhi.zhlw.common.enums.ResultMsgEnum;
import com.tuozhi.zhlw.common.utils.ResultExtGridUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TesAssistCheckServiceImpl implements TesAssistCheckService {

	@Resource
	TesAssistCheckMapper assistCheckMapper;

	/**
	 * 保存协查的车辆数据、查询的嫌疑数据
	 * @param entity
	 * @param tesSuspectDateEntity
	 * @return
	 */
	@Transactional
	@Override
	public ResultMsg save(TesAssistCheckEntity entity, TesSuspectDateEntity tesSuspectDateEntity) {
		ResultMsg result = new ResultMsg();
		//给字段赋值
		entity.setMessageid("0");
		entity.setCreatedept("tuozhi");
		entity.setVehicleid(entity.getPlateId()+"_"+entity.getPlateColor());
		entity.setRequestId(entity.getSid());
		int row = assistCheckMapper.save(entity);
		if(row > 0){
			tesSuspectDateEntity.setSid(entity.getSid());
			JSONArray jsonArray = JSONArray.parseArray(tesSuspectDateEntity.getEvidenceArr());
			for(int i = 0;i < jsonArray.size(); i++){
				JSONObject obj = jsonArray.getJSONObject(i);
				tesSuspectDateEntity.setPassId(obj.get("passId").toString());
				tesSuspectDateEntity.setMediaType(obj.get("mediaType").toString());
				tesSuspectDateEntity.setObuId(obj.get("obuId").toString());
				tesSuspectDateEntity.setCardId(obj.get("cardId").toString());
				tesSuspectDateEntity.setEscapeType(obj.get("eludemoneytypeTime").toString());
				tesSuspectDateEntity.setEnTime(obj.getDate("enTime"));
				tesSuspectDateEntity.setEnlaneId(obj.get("entolllaneId").toString());
				tesSuspectDateEntity.setExTime(obj.getDate("exTime"));
				tesSuspectDateEntity.setExlaneId(obj.get("extolllaneId").toString());
				assistCheckMapper.addEvidenceData(tesSuspectDateEntity);
				result.setSuccess(true);
				result.setMessage("保存成功！");
			}
		}else {
			result.setSuccess(false);
			result.setMessage("保存失败！");
		}
		return result;
	}
	
	public ResultExtGrid findAll(String vehicleid, Long sid, QueryParams queryParams) {
		//设置分页
		PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
		List<TesAssistCheckEntity> assistList = assistCheckMapper.findAll(vehicleid, sid);
		PageInfo pageInfo = new PageInfo<>(assistList);
		return ResultExtGridUtil.isSuccess(ResultMsgEnum.SUCCESS, pageInfo.getList(), pageInfo.getTotal());
	}

	@Override
	public boolean deleteUserByMark(Long sid) {
		return assistCheckMapper.deleteByPlate(sid) == 1;
	}

	/**
	 * 查询发行方服务机构编码枚举
	 * @return
	 */
	@Override
	public List<BaseIssuerEntity> findIssuerData(){
		return assistCheckMapper.findIssuerData();
	}
}
