package com.tuozhi.zhlw.admin.jc.common;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/04 19:07
 **/


public interface MyMapper1 <T> extends
        Mapper<T>,
        MySqlMapper<T>,
        IdsMapper<T> {
	
	  List<T> queryList(Map<String, Object> map);
	  
	  int queryTotal(Map<String, Object> map);
	 
	
}






