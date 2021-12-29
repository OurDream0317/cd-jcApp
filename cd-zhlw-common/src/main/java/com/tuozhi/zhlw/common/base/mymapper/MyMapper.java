package com.tuozhi.zhlw.common.base.mymapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author linqi
 * @create 2019/09/04 19:07
 **/


public interface MyMapper <T> extends Mapper<T>, MySqlMapper<T> {
}
