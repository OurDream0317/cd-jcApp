package com.tuozhi.zhlw.admin.dao;

import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/02 15:42
 **/

@NoRepositoryBean
public interface BaseDao<T,Long extends Serializable> extends JpaRepository<T,Long>{


    List<Object[]> findListBySQL(String sql);

    /**
     * 保存实体
     *
     * @param entity 实体id
     */
    public void saveEntity(Object entity);

    /**
     * 更新实体
     *
     * @param entity 实体id
     */
    public void update(Object entity);

    /**
     * 删除实体
     *
     * @param entityClass 实体类
     * @param entityid    实体id
     */
    public <T> void delete(Class<T> entityClass, Object entityid);

    /**
     * 删除实体
     *
     * @param entityClass 实体类
     * @param entityids   实体id数组
     */
    public <T> void delete(Class<T> entityClass, Object[] entityids);

    /**
     * 获取实体
     *
     * @param <T>
     * @param entityClass 实体类
     * @param entityId   实体id
     * @return
     */
    public <T> T find(Class<T> entityClass, Object entityId);


}
