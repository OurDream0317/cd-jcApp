package com.tuozhi.zhlw.admin.mapper;

import com.tuozhi.zhlw.admin.entity.SysCustomMenuDetailsEntity;
import com.tuozhi.zhlw.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;

/**
 * @author linqi
 * @create 2019/09/26 10:25
 **/
public interface CustomMenuDetailMapper extends MyMapper<SysCustomMenuDetailsEntity> {

    @Select("SELECT " +
            "       t.id as id, " +
            "       t.custom_menu_id as custom_menu_id, " +
            "       t.menu_id as menu_id, " +
            "       t.create_time as create_time " +
            "FROM sys_custom_menu_details t INNER JOIN sys_menu m" +
            "                             ON t.menu_id = m.id" +
            "                               INNER JOIN sys_app a" +
            "                             ON m.app_id = a.id and a.valid_status = 1 " +
            "WHERE t.custom_menu_id = #{menuId,jdbcType=NUMERIC} ")
    SysCustomMenuDetailsEntity findByCustomMenuId(Long menuId);


    @Delete("delete from SYS_CUSTOM_MENU_DETAILS where CUSTOM_MENU_ID = #{sysCustomMenuId,jdbcType=NUMERIC}")
    void deleteByCustMenuId(Long sysCustomMenuId) ;

    @Delete("delete from SYS_CUSTOM_MENU_DETAILS " +
            "where CUSTOM_MENU_ID = #{customMenuId,jdbcType=NUMERIC} " +
            "and MENU_ID = #{menuId,jdbcType=NUMERIC}")
    void deleteByMenuAndCustMenuId(SysCustomMenuDetailsEntity sysCustomMenuDetailsEntity) ;


    @Delete("delete from SYS_CUSTOM_MENU_DETAILS where MENU_ID IN" +
            "(select id from sys_menu where function_code = #{functionCode,jdbcType=NUMERIC})")
    void deleteByMenuIdWithCode(String functionCode) ;

}
