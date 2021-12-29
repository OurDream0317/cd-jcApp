package com.tuozhi.zhlw.admin.service;

import java.util.List;

import com.tuozhi.zhlw.admin.entity.AuditPreListDeviceEntity;
import com.tuozhi.zhlw.admin.entity.AuditPreblackListEntity;

/**
 * 
 * @author liyuyan
 *
 */
public interface AuditPreBlackListService {

	List<AuditPreblackListEntity> findAll(AuditPreblackListEntity auditPreblackListEntity);
	
	List<AuditPreListDeviceEntity> findBySid(Long sid);
}
