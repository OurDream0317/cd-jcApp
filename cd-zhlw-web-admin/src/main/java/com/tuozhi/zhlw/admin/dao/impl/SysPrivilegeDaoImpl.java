package com.tuozhi.zhlw.admin.dao.impl;

import com.tuozhi.zhlw.admin.dao.BaseDaoImpl;
import com.tuozhi.zhlw.admin.dao.SysPrivilegeDao;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.admin.entity.SysRolesMenuEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/17 15:02
 **/
@Repository
public class SysPrivilegeDaoImpl implements SysPrivilegeDao {


    @Override
    public List<Map<String,Object>> getPrivilege(String funcDataPrivis){
        String sql = "select PRIVILEGEID,PRIVILEGENAME from sys_privilege where 1=1 ";
        if(StringUtils.isNotBlank(funcDataPrivis)){
            sql+=" and PRIVILEGEID >= "+funcDataPrivis;
        }
        return null;
    }
}
