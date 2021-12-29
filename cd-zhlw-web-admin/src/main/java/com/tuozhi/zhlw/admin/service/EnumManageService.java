package com.tuozhi.zhlw.admin.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.entity.SysEnumEntity;
import com.tuozhi.zhlw.admin.service.BaseMapperService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/07 16:05
 **/

public interface EnumManageService extends BaseMapperService<SysEnumEntity> {
    PageInfo<SysEnumEntity> findAllByPageInfo(QueryParams queryParams,String realEnumName);

    List<SysEnumDetailsEntity> findAllByEnumId(Long enumId);

    @Transactional
    void saveEnumAndDetails(SysEnumEntity enumEntity, String enumDetailJson);

    @Transactional
    void updateEnumAndDetails(SysEnumEntity enumEntity, String enumDetailJson);

    @Transactional
    void deleteEnumIdAndDetailsByEnumId(Long enumId);

    List<SysEnumDetailsEntity> findByEnumName(String enumName);

}
