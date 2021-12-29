package com.tuozhi.zhlw.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.SysUserEntity;
import com.tuozhi.zhlw.admin.mapper.UserMapper;
import com.tuozhi.zhlw.admin.service.SysUserService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019-08-30 19:11
 **/
@Service
public class SysUserServiceImpl extends
        AbstractMapperServiceImpl<UserMapper, SysUserEntity> implements SysUserService {

    @Resource
    UserMapper userMapper;

    @Override
    public PageInfo<SysUserEntity> findAll(QueryParams pageParams,String userName) {
        PageHelper.startPage(pageParams.getPage(), pageParams.getLimit());
//        List<SysUserEntity> sysUserEntities = userMapper.findGridAll();
        List<SysUserEntity> sysUserEntities = userMapper.findAllByUserName(userName) ;
        return new PageInfo<>(sysUserEntities);
    }

	@Override
	public PageInfo<SysUserEntity> findAllAndDeptId(QueryParams pageParams, String userName,List<Long> deptIds) {
		PageHelper.startPage(pageParams.getPage(), pageParams.getLimit());
//      List<SysUserEntity> sysUserEntities = userMapper.findGridAll();
      List<SysUserEntity> sysUserEntities = userMapper.findAllByUserNameAndDeptId(userName,deptIds) ;
      return new PageInfo<>(sysUserEntities);
	}
	
    @Override
    public List<Map<String,Object>> findFunctionDataByByUserId(Long userId) {
        return userMapper.findFunctionDataByByUserId(userId);
    }

    @Override
    public int saveSysUserEntity(SysUserEntity sysUserEntity) {
        return saveNotNull(sysUserEntity) ;
    }

    @Override
    public boolean deleteUserByMark(String id) {
        return this.userMapper.deleteUserByUpdateMark(id) == 1 ;
    }

    @Override
    public boolean checkRepeatUserName(String userName) {
        return this.userMapper.queryRepeatUserName(userName) == 1;
    }

    @Override
    public boolean checkRepeatLoginName(String loginName) {
        return this.userMapper.queryRepeatLoginName(loginName) == 1;
    }

}
