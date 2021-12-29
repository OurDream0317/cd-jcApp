package com.tuozhi.zhlw.admin.service;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/06 22:42
 **/

public interface BaseMapperService<T> {

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
}
