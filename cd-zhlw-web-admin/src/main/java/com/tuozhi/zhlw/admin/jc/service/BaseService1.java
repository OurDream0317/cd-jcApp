package com.tuozhi.zhlw.admin.jc.service;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/05 11:52
 **/
//mybitis
public interface BaseService1<T> {
    int save(T entity);

    int save(List<T> list);

    int saveBatch(List<T> list);

    int saveNotNull(T entity);

    int deleteByKey(Object key);

    int deleteByIds(String ids);

    int deleteByIds(Object[] ids);

    int updateAll(T entity);

    int updateNotNull(T entity);

    T selectByKey(Object key);

    List<T> selectByIds(String ids);

    List<T> selectByIds(Object[] ids);

    List<T> selectByExample(Object example);

    List<T> selectAll();


    //查询条件
    List<T> queryList(Map<String, Object> map);

    //查询列表总数
    int queryTotal(Map<String, Object> map);
}
