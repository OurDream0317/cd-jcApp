package com.tuozhi.zhlw.common.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import tk.mybatis.spring.annotation.MapperScan;

import javax.sql.DataSource;

/**
 * @author linqi
 * @create 2019/09/27 21:06
 **/

//
//@Configuration
//@MapperScan(basePackages = "com.tuozhi.zhlw.admin.mapper.slave", sqlSessionTemplateRef  = "slaveSqlSessionTemplate")

public class SlaverDataSource {

    @Value("${custom.datasource.ds1.driver-class-name}")
    private String driverClassName;

    @Value("${custom.datasource.ds1.url}")
    private String url;

    @Value("${custom.datasource.ds1.username}")
    private String username;

    @Value("${custom.datasource.ds1.password}")
    private String password;


    @Bean(name = "ds1DataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(this.driverClassName);
        dataSource.setUrl(this.url);
        dataSource.setUsername(this.username);
        dataSource.setPassword(this.password);
        return dataSource;
    }

    @Bean(name = "slaveSqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("ds1DataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mapper/slave/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "slaveTransactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("ds1DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "slaverSqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("slaveSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
