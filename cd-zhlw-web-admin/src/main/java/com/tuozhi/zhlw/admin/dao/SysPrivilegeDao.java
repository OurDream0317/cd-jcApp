package com.tuozhi.zhlw.admin.dao;

import com.tuozhi.zhlw.admin.entity.SysMenuEntity;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 15:02
 **/

public interface SysPrivilegeDao {

    List<Map<String,Object>> getPrivilege(String funcDataPrivis);
}
