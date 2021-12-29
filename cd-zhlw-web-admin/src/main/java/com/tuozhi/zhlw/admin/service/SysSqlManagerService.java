package com.tuozhi.zhlw.admin.service;

import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.entity.PredefineMenu;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/18 17:43
 **/

public interface SysSqlManagerService {
    String getBySid(String sid);

    List getSysSqlColumnsData(String sql, String[] arr) throws Exception;

    List getSysSqlManagerListByFunctionId(Long functionId);

    @Transactional
    void savePredefineMenuAll(PredefineMenu menu, List<Map<String, Object>> paramList,
                              List<Map<String, Object>> columnsList,String flag);

    PageInfo<Map<String, Object>> getSysSqlData(QueryParams queryParams, String sql, String... params);
    List<Map<String,Object>> getSysSqlData1( String sql,String primaryKey,String... params);
    List<Map<String,Object>> getSysSqlAllData(String sql, String... params);

    List<Map<String, Object>> getSysSqlFuncList(Long functionId);

    Map selectColumn(String tableName, String dataBase) throws SQLException;

    void editAndSavePage( String sql, String[] arr) throws SQLException;
    void editAndDelPage(String sql) throws SQLException;

    void dataTrans();
    long getSqlCount(String sql, String... params);
}
