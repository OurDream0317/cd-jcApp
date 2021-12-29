 package com.tuozhi.zhlw.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.tuozhi.zhlw.admin.dao.BaseRepositoryFactoryBean;

import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)

@EnableWebMvc
@EnableJpaRepositories(basePackages = {"com.tuozhi.zhlw.*.dao"},
        repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@MapperScan(basePackages = {"com.tuozhi.zhlw.admin.mapper", "com.tuozhi.zhlw.admin.jc.mapper",
        "com.tuozhi.zhlw.admin.base.mapper","com.tuozhi.zhlw.admin.report.mapper","com.tuozhi.zhlw.admin.tjportrefund.mapper",
        "com.tuozhi.zhlw.admin.tjportrefund.mapper", "com.tuozhi.zhlw.admin.jcApp.mapper"})
@ComponentScan(basePackages = "com.tuozhi.zhlw")
@EnableAsync
@EnableScheduling
@ServletComponentScan
public class MyApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyApplication.class, args);
    }

    @Bean
    public InternalResourceViewResolver setupViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/jsp/");
        resolver.setSuffix(".jsp");
        return resolver;

    }
}
