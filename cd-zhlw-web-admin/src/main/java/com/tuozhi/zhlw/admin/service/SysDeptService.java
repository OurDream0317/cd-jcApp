package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.SysDeptEntity;
import com.tuozhi.zhlw.common.bean.ResultMsg;

import java.util.List;
import java.util.Map;

/**
 * @author ma_zy
 * @Date: 2019/9/11 18:00
 * @Description:
 */
public interface SysDeptService extends BaseMapperService<SysDeptEntity>{
    List<SysDeptEntity> findAllDeptList();

    List createDeptTree(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf, String allowSelectPrivilegeID);
    
    
    
    /**
     * 校验deptWork是否存在
     *
     * @param deptWork
     * @return
     */
    ResultMsg verifyExist(String deptWork);
    List DeptTreeCheckedByList(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf, String allowSelectPrivilegeID,String[] arr);
    
    List DeptTreeChecked(Long parentId, List<SysDeptEntity> deptList, boolean isContainsSelf, String allowSelectPrivilegeID);
    
    List<SysDeptEntity> getAllChildIdsById(Long id);
    
}
