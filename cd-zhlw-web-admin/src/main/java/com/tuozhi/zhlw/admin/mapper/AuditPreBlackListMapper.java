package com.tuozhi.zhlw.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tuozhi.zhlw.admin.entity.AuditPreListDeviceEntity;
import com.tuozhi.zhlw.admin.entity.AuditPreblackListEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;

/**
 * 
 * @author liyuyan
 *
 */
public interface AuditPreBlackListMapper {
	
	//查询
	List<AuditPreblackListEntity> findAll(AuditPreblackListEntity auditPreblackListEntity);
		
	List<AuditPreListDeviceEntity> findBySid(@Param("sid") Long sid);
	
}
