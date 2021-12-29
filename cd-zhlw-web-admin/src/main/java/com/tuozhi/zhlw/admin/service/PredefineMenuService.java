package com.tuozhi.zhlw.admin.service;

import com.tuozhi.zhlw.admin.entity.PredefineMenu;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/19 7:00
 **/

public interface PredefineMenuService extends  BaseMapperService<PredefineMenu>{
    PredefineMenu findAllByFunctionId(Long functionId);
    String getByDeptLoginIdOfAdmin();
    String getByDeptLoginId(Long deptId);
    @Transactional
    void deletePredefineMenu(Long functionId);
}
