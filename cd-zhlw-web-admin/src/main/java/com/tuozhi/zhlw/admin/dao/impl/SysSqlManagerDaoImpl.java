package com.tuozhi.zhlw.admin.dao.impl;

import com.tuozhi.zhlw.admin.dao.SysSqlManagerDao;
import com.tuozhi.zhlw.admin.service.SysCustomMenuService;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Session;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.SessionFactoryUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;


@Repository
public class SysSqlManagerDaoImpl implements SysSqlManagerDao {


    @Autowired
    //@Qualifier("entityManagerPrimary")
    private EntityManager entityManager;
//
//	@Autowired
//	@Qualifier("entityManagerPrimary")
//	private EntityManager entityManagerPrimary;

//	@Resource(name = "entityManagerDs2")
//	private EntityManager entityManagerDs2;


    @Override
    public List<Object> getColumns(String sql, String[] arr) throws SQLException {

        String targetSql = genSelectSqlWithDataSource(sql);
        String[] columnNames = getColumnNames(targetSql, arr);
        return Arrays.asList(columnNames);
    }

    public String[] getColumnNames(String sql, String[] arr) throws SQLException {
        Connection con = SessionFactoryUtils.getDataSource(GetSession().getSessionFactory()).getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                pstm.setString(i + 1, arr[i]);
            }
        }
        pstm.setQueryTimeout(60000);
        ResultSet rs = pstm.executeQuery();
        ResultSetMetaData metaData = rs.getMetaData();
        int count = metaData.getColumnCount();
        String[] name = new String[count];
        for (int i = 0; i < count; i++) {
            name[i] = metaData.getColumnName(i + 1);
        }
        return name;
    }

    private Session GetSession() {
        return entityManager.unwrap(Session.class);
    }

    @Override
    public List<Map<String, Object>> getSysSqlData(String sql, String... params) {
        params = null;
        String targetSql = genSelectSqlWithDataSource(sql);
        Query query = entityManager.createNativeQuery(targetSql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        if (params != null && params.length > 0) {
            for (int i = 1; i < params.length + 1; i++) {
                query.setParameter(i, params[i - 1]);
            }
        }
        return query.getResultList();
    }


    @Override
    public List<Map<String, Object>> getSysSqlData1(String sql, String primaryKey, String... params) {
        String str = StringUtils.join(params, ",");
        sql = sql.replace("1=1","1=1 and " + primaryKey + " in (" + str + ")");
        String targetSql = genSelectSqlWithDataSource(sql);
        Query query = entityManager.createNativeQuery(targetSql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();
    }

    /**
     * 查询数据库表中字段的数据类型
     *
     * @param sql
     * @return
     * @throws SQLException
     */
    @Override
    public Map selectColumn(String sql) throws SQLException {
        Map map = new HashMap();
        Connection con = SessionFactoryUtils.getDataSource(GetSession().getSessionFactory()).getConnection();
        PreparedStatement pstm = con.prepareStatement(sql);
        List TypeList = new ArrayList();
        List NameList = new ArrayList();

        ResultSet resultSet = pstm.executeQuery();
        while (resultSet.next()) {
            String column_name = resultSet.getString("column_name");
            String data_type = resultSet.getString("data_type");
            NameList.add(column_name);
            TypeList.add(data_type);
        }

        map.put("TypeList", TypeList);
        map.put("NameList", NameList);
        return map;
    }

    @Override
    public void editAndSavePage(String sql, String[] arr) throws SQLException {
        String targetSql = genUpdateOrSaveSqlWithDataSource(sql);
        Connection con = SessionFactoryUtils.getDataSource(GetSession().getSessionFactory()).getConnection();
        PreparedStatement pstm = con.prepareStatement(targetSql);
        for (int i = 0; i < arr.length; i++) {
            pstm.setString(i + 1, arr[i]);
        }
        pstm.execute();
    }

    @Override
    public List<Map> findPredefineSQLStore(String sql) {
        Query query = entityManager.createNativeQuery(sql);
        query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        return query.getResultList();

    }

    @Override
    public void editAndDelPage(String sql) throws SQLException {
        String targetSql = genUpdateOrSaveSqlWithDataSource(sql);
        Connection con = SessionFactoryUtils.getDataSource(GetSession().getSessionFactory()).getConnection();
        PreparedStatement pstm = con.prepareStatement(targetSql);
        pstm.execute();
    }

    private String genSelectSqlWithDataSource(String sql) {
        StringBuilder sb = new StringBuilder(sql);
        String sb1 = sb.toString();
        return sb1;


    }

    private String genUpdateOrSaveSqlWithDataSource(String sql) {
        /*int updateIndex;*/
        StringBuilder sb = new StringBuilder(sql.toUpperCase());
	/*	if (sb.toString().contains("UPDATE")) {
			updateIndex = sb.indexOf("UPDATE ") + 7;
		} else {
			updateIndex = sb.indexOf("INSERT INTO ") + 12;
		}

		sb.insert(updateIndex," "+dataSource+".");*/
        return sb.toString();
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void dataTrans() {
        long startTotalTime = System.currentTimeMillis();
        Map picMap = new HashMap();
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select count(*) as count from BASIC_BASICINFODOWN");

        int totalRows = ((BigDecimal) maps.get(0).get("count")).intValue();
        int pageSize = 1000;
        int totalPage = (totalRows + pageSize - 1) / pageSize;

        String columns = "SID, TYPE, PROVINCEID, ID, NAME, HEX, DOWNID";
        System.out.println("总共"+totalRows+"条");
        for (int page = 0; page < totalPage; page++) {
            long start = System.currentTimeMillis();
            int endRow = (page + 1) * pageSize;
            int startRow = (page * pageSize + 1);

            String sql = "SELECT " + columns + " FROM \n" +
                    "(SELECT TEMP.*,ROWNUM RN FROM (SELECT * FROM BASIC_BASICINFODOWN ) TEMP WHERE ROWNUM <= " + endRow + ")" +
                    "WHERE RN >=" + startRow;

            List<Map<String, Object>> resultList = jdbcTemplate.queryForList(sql);

            System.out.println("第"+(page + 1)+"页，"+resultList.size()+"条");

            }


        }


    public static void main(String[] sss) {


            for (int i = 0; i < 10; i++) {
                try {
                    if (i == 2) {
                        throw new NullPointerException();
                    }
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    continue;
                }
                System.out.println(i);
            }

    }

    @Override
    public long getCount(String sql, String... params) {
        StringBuilder sb = new StringBuilder("select count(*) count from (");
        sb.append(sql).append(" )");
        List<Map<String, Object>> resultList = getSysSqlData(sb.toString(), params);
        if (CollectionUtils.isEmpty(resultList)) {
            return 0L;
        }
        return ((BigDecimal) resultList.get(0).get("COUNT")).longValue();
    }


}
