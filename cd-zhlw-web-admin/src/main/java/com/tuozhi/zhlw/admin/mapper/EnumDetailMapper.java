package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysEnumDetailsEntity;
import com.tuozhi.zhlw.admin.entity.SysEnumEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Many;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.JdbcType;

import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/11 10:52
 **/

public interface EnumDetailMapper extends MyMapper<SysEnumDetailsEntity> {

    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.NUMERIC, id=true),
            @Result(column = "enum_id", property = "enumId",jdbcType = JdbcType.NUMERIC)
    })
    @Select("select d.*,id as enumDetailsId from SYS_ENUM_DETAILS d WHERE ENUM_ID = #{enumId} ")
    List<SysEnumDetailsEntity> findByEnumId(Long enumId);

    @Select("select s.*,s.id as enumDetailsId from sys_enum e inner join SYS_ENUM_DETAILS s " +
            "on e.ID = s.ENUM_ID WHERE e.ENUM_NAME = #{enumName} ORDER BY s.ID ASC")
    List<SysEnumDetailsEntity> findByEnumName(String enumName);

    @Select("select t.requestId as ENUM_VALUE,t.cartype as ENUM_VNAME from tjsso.JC_INVESTIGATION_REQUEST  t")
    List<Map> findData();






}
