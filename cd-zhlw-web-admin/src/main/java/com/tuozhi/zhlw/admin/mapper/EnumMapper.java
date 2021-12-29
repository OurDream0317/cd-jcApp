package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysEnumEntity;
import com.tuozhi.zhlw.admin.entity.SysMenuEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/07 15:56
 **/
public interface EnumMapper extends MyMapper<SysEnumEntity> {

    /*@Results({
            @Result(column="id", property="id", jdbcType= JdbcType.NUMERIC, id=true),
            @Result(column = "id",property = "enumDetailsEntityList",
                    many = @Many(select = "com.tuozhi.zhlw.admin.mapper.EnumDetailMapper.findByEnumId"))
    })
    @Select("select * from SYS_ENUM ")*/
      // 需要使用 动态SQL 注解对动态不友好
    List<SysEnumEntity> findAllEnumEntity(@Param("realEnumName") String realEnumName);
}
