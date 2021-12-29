package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.service.BaseMapperService;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/06 22:39
 **/
public abstract class AbstractMapperServiceImpl <M extends MyMapper<T>, T >implements BaseMapperService<T> {

    @Autowired
    M mapper;

    @Override
    @Transactional
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    @Transactional
    public int save(List<T> list) {
        return mapper.insertList(list);
    }

    //
    @Override
    @Transactional
    public int saveBatch(List list) {
        return mapper.insertList(list);
    }

    //NULL 值不保存
    @Override
    @Transactional
    public int saveNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    //根据主键字段进行删除
    @Override
    @Transactional
    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    //根据主键字符串删除，例如：1，2，3
    @Override
    @Transactional
    public int deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    @Transactional
    public int deleteByIds(Object[] ids) {
        return mapper.deleteByIds(StringUtils.join(ids, ","));
    }

    @Override
    @Transactional
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    //根据主键 更新属性不为空的
    @Override
    @Transactional
    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }


    //根据主键字段查询，方法参数必须包含完整的主键属性，查询条件使用等号
    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    @Override
    public List<T> selectByIds(String ids) {
        return  mapper.selectByIds(ids);
    }

    @Override
    public List<T> selectByIds(Object[] ids) {
        return mapper.selectByIds(StringUtils.join(ids, ","));
    }

    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    @Override
    public List<T> selectAll() {
        return mapper.selectAll();
    }

}
