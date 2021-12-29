package com.tuozhi.zhlw.admin.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/18 22:41
 **/

public interface SysSqlManagerDao {
    List<Object> getColumns( String sql, String[] arr) throws SQLException;

    List<Map<String,Object>> getSysSqlData( String sql,String... params);
    List<Map<String,Object>> getSysSqlData1( String sql,String primaryKey,String... params);
    Map selectColumn(String sql) throws SQLException;

    void editAndSavePage(String sql, String[] arr) throws SQLException;
    List<Map> findPredefineSQLStore(String sql) throws SQLException;
    void editAndDelPage(String sql) throws SQLException;

    void dataTrans();

    long getCount(String sql, String... params);
}

