package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuDetailsEntity;

import java.util.List;

public interface SysCustomMenuDetailsService extends BaseMapperService<SysCustomMenuDetailsEntity>{

    void saveBatchCustMenuDetails(List<SysCustomMenuDetailsEntity> sysCustomMenuDetailsEntityList) ;

    void deleteDetailsByCustMenuId(String sysCustomMenuId) ;

    void deleteSingleDetailsByPairId(SysCustomMenuDetailsEntity sysCustomMenuDetailsEntity) ;

}
