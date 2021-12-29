package com.tuozhi.zhlw.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.entity.SysEnumEntity;
import com.tuozhi.zhlw.admin.mapper.EnumDetailMapper;
import com.tuozhi.zhlw.admin.mapper.EnumMapper;
import com.tuozhi.zhlw.admin.service.EnumManageService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.springframework.beans.BeanUtils;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.*;

/**
 * @author linqi
 * @create 2019/09/07 15:54
 **/
@Service
@Slf4j
public class EnumManageServiceImpl
        extends
        AbstractMapperServiceImpl<EnumMapper, SysEnumEntity> implements EnumManageService {

    @Resource
    EnumMapper enumMapper;

    @Resource
    EnumDetailMapper enumDetailMapper;

    @Override
    public PageInfo<SysEnumEntity> findAllByPageInfo(QueryParams queryParams,String realEnumName) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        List<SysEnumEntity> enumList = enumMapper.findAllEnumEntity(realEnumName);
        for (SysEnumEntity enumEntity : enumList) {
            enumEntity.setDetails(getDetails(enumEntity.getEnumDetailsEntityList()));
        }

        return new PageInfo<SysEnumEntity>(enumList);
    }

    private String getDetails(List<SysEnumDetailsEntity> detailsEntities) {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (SysEnumDetailsEntity detail : detailsEntities) {
            sb.append(detail.getEnumValue()).append(":").append(detail.getEnumName());
            if (i + 1 < detailsEntities.size()) {
                sb.append(",");
            }
            i++;
        }
        return sb.toString();
    }

    @Override
    public List<SysEnumDetailsEntity> findAllByEnumId(Long enumId) {
        return enumDetailMapper.findByEnumId(enumId);
    }

    @Override
    @Transactional
    public void saveEnumAndDetails(SysEnumEntity enumEntity, String enumDetailJson) {
        saveNotNull(enumEntity);
        List<SysEnumDetailsEntity> enumDetailsEntityList = JSON.parseArray(enumDetailJson, SysEnumDetailsEntity.class);
        for (SysEnumDetailsEntity sysEnumDetailsEntity : enumDetailsEntityList) {
            sysEnumDetailsEntity.setEnumId(enumEntity.getId());
            enumDetailMapper.insertSelective(sysEnumDetailsEntity);
        }
    }

    @Override
    @Transactional
    public void updateEnumAndDetails(SysEnumEntity enumEntity, String enumDetailJson) {
        updateNotNull(enumEntity);
        List<SysEnumDetailsEntity> newEnumDetailsEntityList = JSON.parseArray(enumDetailJson, SysEnumDetailsEntity.class);
        List<SysEnumDetailsEntity> oldEnumDetailsList = enumDetailMapper.findByEnumId(enumEntity.getId());

        Map<Long,SysEnumDetailsEntity> oldMap = new HashMap<>();
        for (SysEnumDetailsEntity old : oldEnumDetailsList) {
            oldMap.put(old.getId(),old);
        }

        List<SysEnumDetailsEntity> updateList = new ArrayList<>();
        List<SysEnumDetailsEntity> addList = new ArrayList<>();

        for (SysEnumDetailsEntity sourceObj : newEnumDetailsEntityList) {
            sourceObj.setId(sourceObj.getEnumDetailsId());
            if (oldMap.get(sourceObj.getId()) != null) {
                SysEnumDetailsEntity targetObj = oldMap.get(sourceObj.getId());
                BeanUtils.copyProperties(sourceObj, targetObj);
                updateList.add(targetObj);
                oldMap.remove(sourceObj.getId());
            } else {
                sourceObj.setEnumId(enumEntity.getId());
                addList.add(sourceObj);
            }
        }
        Collection<SysEnumDetailsEntity> deleteObjs = oldMap.values();


        for (SysEnumDetailsEntity updateDetails : updateList) {
            enumDetailMapper.updateByPrimaryKeySelective(updateDetails);
        }

        for (SysEnumDetailsEntity addDetails : addList) {
            enumDetailMapper.insertSelective(addDetails);
        }
        for (SysEnumDetailsEntity deleteDetails : deleteObjs) {
            enumDetailMapper.delete(deleteDetails);
        }

    }


    @Override
    @Transactional
    public void deleteEnumIdAndDetailsByEnumId(Long enumId) {
        enumDetailMapper.deleteByIds(enumId+"");
        deleteByKey(enumId);
    }

    @Override
    public List<SysEnumDetailsEntity> findByEnumName(String enumName) {
        return enumDetailMapper.findByEnumName(enumName);
    }


}
