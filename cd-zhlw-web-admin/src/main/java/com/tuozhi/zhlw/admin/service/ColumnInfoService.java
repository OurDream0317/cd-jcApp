package com.tuozhi.zhlw.admin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * @author linqi
 * @create 2019/09/19 6:39
 **/
public interface ColumnInfoService {
    void insertSysColumnsInfo(List<Map<String, Object>> columnsList, Long functionId);
    List<Map<String,Object>> findAllColumnsInfoByFunctionId(Long functionId);
    void delSysColumnsInfo(Long functionId);
    List<Map<String, Object>>  findAllColumnsInfoById(String id,String tableName, String dataSource, String SQLStatement
    ,String primaryKey) throws SQLException;
    List findByFunctionIdAndText(Long functionId,String text);
}
