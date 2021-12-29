package com.tuozhi.zhlw.admin.service;

import org.springframework.data.domain.Example;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/02 15:44
 **/
public interface BaseService<T,Long> {
    void save(T m);

    void saveAll(List<T> m);

    List<T> findAll(Example example);

    T getOne(Long id);

    List<Object[]> findListBySQL(String sql);

    void deleteById(Long id);

    void delete(T entity);

    void deleteAll(List<T> entityList);
}
