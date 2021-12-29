package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysAppEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ma_zy
 * @Date: 2019/9/16 09:59
 * @Description:
 */
public interface AppMapper extends MyMapper<SysAppEntity> {

    List<SysAppEntity> findAllWithName(@Param("appName") String appName,@Param("appId") Long appId) ;

    @Update({"update SYS_APP set VALID_STATUS = 0 where ID = #{id}"})
    boolean deleteAppByMarkUpdate(String id);

    @Update({"update sys_app set " +
            "name = #{name}," +
            "sort = #{sort}," +
            "valid_status = #{validStatus}," +
            "code = #{code}," +
            "icon_cls = #{iconCls,jdbcType=VARCHAR}" +
            "where id = #{id}"})
    boolean updateAppInfoById(SysAppEntity appEntity) ;

    @Select("SELECT * FROM SYS_APP WHERE CODE = #{appCode}")
    SysAppEntity findByAppCode(String appCode);


    @Select("select count(1) from sys_app where name = #{appName,jdbcType=VARCHAR}")
    int queryRepeatAppName(String appName) ;

    @Select("select count(1) from sys_app where code = #{appCode,jdbcType=VARCHAR}")
    int queryRepeatAppCode(String appCode) ;

    @Select("select * from sys_app where valid_status = 1 and code != 'ZDYCD' ")
    List<SysAppEntity> listByAvailable();


}
