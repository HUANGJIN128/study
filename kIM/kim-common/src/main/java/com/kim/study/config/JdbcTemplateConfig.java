package com.kim.study.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * @ClassName JdbcTemplateConfig
 * @Description JdbcTemplate配置类
 * @Author KIM
 * @Date 2022/4/6 10:38
 * @Version 1.0
 */
@Configuration
public class JdbcTemplateConfig {

    @Bean
    public DataSource getDataSource(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3336/cloud?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("merit1q98.c0m");
        return dataSource;
    }


    @Bean()
    public JdbcTemplate getJdbcTemplateE(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }

    @Bean()
    public NamedParameterJdbcTemplate getNamedJdbcTemplate(DataSource dataSource){
        return new NamedParameterJdbcTemplate(dataSource);
    }





}
