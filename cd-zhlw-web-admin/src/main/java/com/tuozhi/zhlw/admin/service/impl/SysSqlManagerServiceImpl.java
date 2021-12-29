package com.tuozhi.zhlw.admin.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tuozhi.zhlw.admin.dao.SysSqlManagerDao;
import com.tuozhi.zhlw.admin.entity.PredefineMenu;
import com.tuozhi.zhlw.admin.entity.SysSqlManager;
import com.tuozhi.zhlw.admin.mapper.PredefineMenuMapper;
import com.tuozhi.zhlw.admin.mapper.SqlManagerMapper;
import com.tuozhi.zhlw.admin.service.ColumnInfoService;
import com.tuozhi.zhlw.admin.service.SysSqlManagerService;
import com.tuozhi.zhlw.common.bean.QueryParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service("sysSqlManagerService")
public class SysSqlManagerServiceImpl implements SysSqlManagerService {

    @Autowired
    SysSqlManagerDao dao;

    @Resource
    SqlManagerMapper mapper;

    @Resource
    PredefineMenuMapper predefineMenuMapper;

    @Autowired
    SysSqlManagerService sysSqlManagerservice;
    @Autowired
    ColumnInfoService sysColumnInfoService;

    @Override
    public String getBySid(String sid) {
        return mapper.getBySid(sid);
    }

    /**
     * 执行SQL语句结果集
     */
    @Override
    public List getSysSqlColumnsData(String sql, String[] arr) throws Exception {

        return dao.getColumns(sql, arr);
    }

    @Override
    public List getSysSqlManagerListByFunctionId(Long functionId) {
        return mapper.getSysSqlManagerList(functionId);
    }

    @Override
    @Transactional
    public void savePredefineMenuAll(PredefineMenu menu, List<Map<String, Object>> paramList,
                                     List<Map<String, Object>> columnsList, String flag) {
        if (flag.equals("update")) {
            predefineMenuMapper.deleteByFunctionId(menu.getFunctionId());
            predefineMenuMapper.insertSelective(menu);
        } else {
            predefineMenuMapper.insertSelective(menu);
        }
        if (!paramList.isEmpty()) {
            insertSysSqlData(paramList, menu.getFunctionId());
            if(!columnsList.isEmpty()) {
                sysColumnInfoService.insertSysColumnsInfo(columnsList, menu.getFunctionId());
            }
        }
    }


    public void insertSysSqlData(List<Map<String, Object>> paramList, Long functionId) {
        mapper.deleteByFunctionId(functionId);

        for (Map<String, Object> map : paramList) {
            SysSqlManager sqlManager = new SysSqlManager();
            sqlManager.setFunctionId(functionId);
            sqlManager.setSid(UUID.randomUUID().toString().replaceAll("-", ""));
            sqlManager.setParamId(Integer.parseInt(map.get("paramId").toString()));
            sqlManager.setParamName(map.get("ParamName").toString());

            sqlManager.setIgnoreWhenNull("0".equals(map.get("ignoreWhenNull").toString())
                    || "false".equals(map.get("ignoreWhenNull").toString()) ? 0 : 1);
            sqlManager.setParamExample(map.get("ParamExample").toString());
            sqlManager.setParamScript(map.get("paramScript").toString());
            sqlManager.setParamScriptSource(map.get("paramScriptResource").toString());
            sqlManager.setParamType(map.get("ParamDataType").toString());
            sqlManager.setRequired(
                    "0".equals(map.get("required").toString()) || "false".equals(map.get("required").toString()) ? 0
                            : 1);
            sqlManager.setParamForLinkChild(map.get("paramIdForLinkChild").toString() == null ? "-1"
                    : map.get("paramIdForLinkChild").toString());
            mapper.insertSelective(sqlManager);
        }
    }

    @Override
    public PageInfo<Map<String, Object>> getSysSqlData(QueryParams queryParams, String sql, String... params) {
        PageHelper.startPage(queryParams.getPage(), queryParams.getLimit());
        if (queryParams.getLimit() == 0) {
            List<Map<String, Object>> list= dao.getSysSqlData(sql, params);
            return new PageInfo<Map<String, Object>>(list);
        }
        StringBuilder sb = new StringBuilder();

        sb.append("SELECT * FROM   " +
                "(  " +
                "SELECT temp.*, ROWNUM RN " +
                "FROM (");
        sb.append(sql);
        sb.append(") temp WHERE ROWNUM <= ").append(queryParams.getLimit()*queryParams.getPage() ).append(" ) WHERE RN >  ");
        sb.append(queryParams.getLimit()*(queryParams.getPage()-1));
           List<Map<String, Object>> list= dao.getSysSqlData(sb.toString(), params);
        return  new PageInfo<Map<String, Object>>(list);
    }

    @Override
    public List<Map<String, Object>> getSysSqlData1(String sql, String primaryKey, String... params) {
        return dao.getSysSqlData1(sql,primaryKey, params);
    }

    @Override
    public List<Map<String, Object>> getSysSqlAllData(String sql, String... params) {
        return dao.getSysSqlData(sql, params);
    }

    @Override
    public List<Map<String, Object>> getSysSqlFuncList(Long functionId) {
        String detailinfo = "";
        List<Map<String, Object>> list = mapper.getSysSqlFuncList(functionId);
        for (Map map : list) {
            Clob clob = (Clob) map.get("SQLSCRIPT");
            if (clob != null) {
                try {
                    detailinfo = clob.getSubString((long) 1, (int) clob.length());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            map.put("SQLSCRIPT", detailinfo);
        }
        return list;
    }


    @Override
    public Map selectColumn(String tableName, String dataBase) throws SQLException {
        tableName = tableName.toUpperCase();
        String sql = null;
        if (null == dataBase) {
            sql = "SELECT column_name,data_type FROM dba_tab_columns WHERE table_name ='" + tableName + "'";
        } else {
            sql = "SELECT column_name,data_type FROM dba_tab_columns WHERE table_name ='" + tableName + "'and owner='" + dataBase + "'";
        }
        return dao.selectColumn(sql);
    }

    @Override
    public void editAndSavePage(String sql, String[] arr) throws SQLException {
        dao.editAndSavePage(sql, arr);
    }
    @Override
    public  void editAndDelPage(String sql) throws SQLException {
        dao.editAndDelPage(sql);
    }

    @Override
    public void dataTrans() {
        dao.dataTrans();
    }

    @Override
    public long getSqlCount(String sql, String... params) {
        return dao.getCount(sql,params);
    }

}
