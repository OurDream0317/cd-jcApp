package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuDetailsEntity;
import com.tuozhi.zhlw.admin.mapper.CustomMenuDetailMapper;
import com.tuozhi.zhlw.admin.service.SysCustomMenuDetailsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.List;

@Service
public class SysCustomMenuDetailsServiceImpl
        extends AbstractMapperServiceImpl<CustomMenuDetailMapper, SysCustomMenuDetailsEntity>
        implements SysCustomMenuDetailsService {

    @Resource
    CustomMenuDetailMapper customMenuDetailMapper ;

    @Override
    @Transactional
    public void saveBatchCustMenuDetails(List<SysCustomMenuDetailsEntity> sysCustomMenuDetailsEntityList) {
        for (SysCustomMenuDetailsEntity customDetails : sysCustomMenuDetailsEntityList) {
            super.saveNotNull(customDetails);
        }
    }

    @Override
    public void deleteDetailsByCustMenuId(String sysCustomMenuId) {
        this.customMenuDetailMapper.deleteByCustMenuId(Long.parseLong(sysCustomMenuId));
    }

    @Override
    public void deleteSingleDetailsByPairId(SysCustomMenuDetailsEntity sysCustomMenuDetailsEntity) {
        this.customMenuDetailMapper.deleteByMenuAndCustMenuId(sysCustomMenuDetailsEntity);
    }
}
