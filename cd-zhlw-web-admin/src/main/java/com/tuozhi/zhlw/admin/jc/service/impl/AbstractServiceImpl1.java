package com.tuozhi.zhlw.admin.jc.service.impl;

import com.tuozhi.zhlw.admin.jc.common.MyMapper1;
import com.tuozhi.zhlw.admin.jc.service.BaseService1;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;


/**
 * @author linqi
 * @create 2019/09/05 11:52
 **/



 public abstract class AbstractServiceImpl1<M extends MyMapper1<T>, T >implements BaseService1<T> {

    @Autowired
    M mapper;

    @Override
    public int save(T entity) {
        return mapper.insert(entity);
    }

    @Override
    public int save(List<T> list) {
        return mapper.insertList(list);
    }

    //主键必须自增
    @Override
    public int saveBatch(List list) {
        return mapper.insertList(list);
    }

    //NULL 值不保存
    @Override
    public int saveNotNull(T entity) {
        return mapper.insertSelective(entity);
    }

    //根据主键字段进行删除
    @Override
    public int deleteByKey(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    //根据主键字符串删除，例如：1，2，3
    @Override
    public int deleteByIds(String ids) {
        return mapper.deleteByIds(ids);
    }

    @Override
    public int deleteByIds(Object[] ids) {
        return mapper.deleteByIds(StringUtils.join(ids, ","));
    }

    @Override
    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    //根据主键 更新属性不为空的
    @Override
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
	
	
	  @Override public int queryTotal(Map<String,Object> map) { return
	  mapper.queryTotal(map); }
	  
	  //需要各个mapper自己实现
	  
	  @Override public List<T> queryList(Map<String,Object> map) { return
	  mapper.queryList(map); }
	 
	 

    //需要各个mapper自己实现
    public List<T> selectAll() {
        return mapper.selectAll();
    }
}
