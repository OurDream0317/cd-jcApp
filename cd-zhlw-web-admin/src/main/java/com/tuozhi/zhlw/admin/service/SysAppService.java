package com.tuozhi.zhlw.admin.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysAppEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/03 21:55
 **/

public interface SysAppService extends BaseService<SysAppEntity,Long> {

    List<SysAppEntity> findAllCondition(Long userId);
    
    List<SysAppEntity> findAllCondition(String roleId);
    List<SysAppEntity> findAllConditionHis(String roleId);
    List<SysAppEntity> findAllCondition();
//    List<SysAppEntity> findAllCondition(Long userId,boolean enableValidStatus);

    PageInfo<SysAppEntity> findAllByPageHelper(QueryParams queryParams, String appName,Long appId) ;

    List<SysAppEntity> findAll();

    int saveSysAppEntity(SysAppEntity appEntity) ;

    boolean deleteAppByMark(String toString);

    boolean updateInfoById(SysAppEntity appEntity) ;


    void addCustomMenuTree(Map treeMap, Long userId);

    boolean checkRepeatAppName(String appName);

    boolean checkRepeatAppCode(String appCode);

    List<SysAppEntity> listByAvailable();
}
