package com.tuozhi.zhlw.admin.service.impl;

import com.tuozhi.zhlw.admin.dao.SysSqlManagerDao;
import com.tuozhi.zhlw.admin.entity.ColumnInfo;
import com.tuozhi.zhlw.admin.mapper.ColumnInfoMapper;
import com.tuozhi.zhlw.admin.service.ColumnInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author linqi
 * @create 2019/09/19 6:39
 **/
@Service
public class ColumnInfoServiceImpl implements ColumnInfoService {
    @Resource
    ColumnInfoMapper mapper;

    @Autowired
    SysSqlManagerDao sysSqlManagerDao;

    public void save(ColumnInfo columnInfo) {
        mapper.insertSelective(columnInfo);
    }

    @Override
    @Transactional
    public void insertSysColumnsInfo(List<Map<String, Object>> columnsList, Long functionId) {
        mapper.delSysColumnsInfo(functionId);

        for(Map<String, Object> map:columnsList) {
            ColumnInfo column=new ColumnInfo();
            column.setId(UUID.randomUUID().toString().replaceAll("-", ""));
            column.setColumnDataIndex(map.get("columnDataIndex").toString());
            column.setColumnText(map.get("columnText").toString());
            column.setNote(map.get("note").toString());
            column.setFunctionId(functionId);
            column.setOrderIndex(Integer.parseInt(map.get("orderIndex").toString()));
            column.setWidthType(Integer.parseInt(map.get("widthType").toString()));
            column.setWidthValue(Integer.parseInt(map.get("widthValue").toString()));
            column.setColumnIsHidden(Integer.parseInt(map.get("columnIsHidden").toString()));
            column.setIsFrozen(Integer.parseInt(map.get("isFrozen").toString()));
            column.setIsHeaderGroup(Integer.parseInt(map.get("IsHeaderGroup").toString()));
            if(null!=map.get("columnValue")){
                column.setHeaderGroupText(map.get("columnValue").toString());
            }
            mapper.insertSelective(column);
        }

    }

    @Override
    @Transactional
    public List<Map<String,Object>> findAllColumnsInfoByFunctionId(Long functionId) {
        System.out.println("==============开始查询====================");
        List<Map<String, Object>> allColumnsInfoByFunctionId = mapper.findAllColumnsInfoByFunctionId(functionId);
        allColumnsInfoByFunctionId = mapper.findAllColumnsInfoByFunctionId(functionId);
        System.out.println("==============结束查询==================== list count " + allColumnsInfoByFunctionId.size());
        return allColumnsInfoByFunctionId;
    }


    @Override
    public void delSysColumnsInfo(Long functionId) {
        mapper.delSysColumnsInfo(functionId);
    }

    @Override
    public List<Map<String, Object>>  findAllColumnsInfoById(String id,String tableName, String dataSource, String SQLStatement,
                                                             String primaryKey) throws SQLException {

        String sql=SQLStatement.replace(";","")+" and "+primaryKey+"="+id;

        return sysSqlManagerDao.getSysSqlData( sql, null);
    }

    @Override
    public List findByFunctionIdAndText(Long functionId, String text) {
        return mapper.findByFunctionIdAndText(functionId,text);
    }

}
