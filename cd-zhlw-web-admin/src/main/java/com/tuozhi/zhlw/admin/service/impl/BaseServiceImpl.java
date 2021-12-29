package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.dao.BaseDao;
import com.tuozhi.zhlw.admin.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/02 15:44
 **/


public class BaseServiceImpl<T,Long extends Serializable> implements BaseService<T,Long> {

    @Autowired
    BaseDao<T, Long> baseDao;

    @Override
    public void save(T entity) {
        baseDao.save(entity);
    }

    @Override
    public void saveAll(List<T> entity) {
        baseDao.saveAll(entity);
    }

    @Override
    public List<T> findAll(Example example) {
        return baseDao.findAll(example);
    }

    @Override
    public T getOne(Long id) {
        return (T) baseDao.getOne(id);
    }

    @Override
    public List<Object[]> findListBySQL(String sql){
        return baseDao.findListBySQL(sql);
    }

   @Override
   public void deleteById(Long id){
        baseDao.deleteById(id);
   }

    @Override
    public void delete(T entity){
        baseDao.delete(entity);
    }

    @Override
    public void deleteAll(List<T> entityList){
        baseDao.deleteAll(entityList);
    }

}