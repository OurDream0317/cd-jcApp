package com.tuozhi.zhlw.admin.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tuozhi.zhlw.admin.entity.AuditPreListDeviceEntity;
import com.tuozhi.zhlw.admin.entity.AuditPreblackListEntity;
import com.tuozhi.zhlw.admin.mapper.AuditPreBlackListMapper;
import com.tuozhi.zhlw.admin.service.AuditPreBlackListService;
import org.springframework.util.StringUtils;

@Service
public class AuditPreBlackListServiceImpl implements AuditPreBlackListService {

	@Resource
	AuditPreBlackListMapper auditpreblackMapper;

	/**
	 * 查询预追缴黑名单车辆信息
	 * 根据时间、车牌号、车牌颜色查询
	 * @param auditPreblackListEntity
	 * @return
	 */
	@Override
	public List<AuditPreblackListEntity> findAll(AuditPreblackListEntity auditPreblackListEntity) {
		if(StringUtils.isEmpty(auditPreblackListEntity.getPlateId())){
			auditPreblackListEntity.setPlateId(null);
		}
		if(StringUtils.isEmpty(auditPreblackListEntity.getPlateColor())){
			auditPreblackListEntity.setPlateColor(null);
		}
		if(auditPreblackListEntity.getPlateId()!= null && auditPreblackListEntity.getPlateColor() != null){
			auditPreblackListEntity.setVehicleId(auditPreblackListEntity.getPlateId()+"_"+auditPreblackListEntity.getPlateColor());
		}
		return auditpreblackMapper.findAll(auditPreblackListEntity);
	}

	@Override
	public List<AuditPreListDeviceEntity> findBySid(Long sid) {
		return auditpreblackMapper.findBySid(sid);
	}

}
