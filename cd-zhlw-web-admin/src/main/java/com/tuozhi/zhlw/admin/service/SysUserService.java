package com.tuozhi.zhlw.admin.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.common.bean.QueryParams;

import java.util.List;
import java.util.Map;

/**
 * @author
 * @create 2019-08-30 19:11
 **/

public interface SysUserService extends BaseMapperService<SysUserEntity>{

    PageInfo<SysUserEntity> findAll(QueryParams pageParams,String userName);
    
    PageInfo<SysUserEntity> findAllAndDeptId(QueryParams pageParams,String userName,List<Long> deptIds);

    List<Map<String,Object>> findFunctionDataByByUserId(Long userId);

    int saveSysUserEntity(SysUserEntity sysUserEntity) ;

    boolean deleteUserByMark(String id) ;

    boolean checkRepeatUserName(String userName) ;
    boolean checkRepeatLoginName(String loginName) ;

}
