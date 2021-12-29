package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

import java.util.List;

/**
 * @author linqi
 * @create 2019/09/26 10:23
 **/

public interface CustomMenuMapper extends MyMapper<SysCustomMenuEntity> {


    @Results({
            @Result(column="id", property="id", jdbcType= JdbcType.NUMERIC, id=true),
            @Result(column = "id",property = "detailList",
                    many = @Many(select = "com.tuozhi.zhlw.admin.mapper.CustomMenuDetailMapper.findByCustomMenuId"))
    })
    @Select("SELECT * FROM SYS_CUSTOM_MENU WHERE USER_ID = #{userId}")
    public List<SysCustomMenuEntity> findAllByUserId(Long userId);

    @Update("update SYS_CUSTOM_MENU " +
            "set CUSTOM_MENU_NAME = #{customMenuName,jdbcType=VARCHAR}" +
            ",UPDATE_TIME = #{updateTime,jdbcType=TIMESTAMP} " +
            "where ID = #{id,jdbcType=NUMERIC}")
    boolean updateCustMenuName(SysCustomMenuEntity sysCustomMenuEntity) ;


}
