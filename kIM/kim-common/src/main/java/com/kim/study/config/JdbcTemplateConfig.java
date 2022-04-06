package com.kim.study.config;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.boot.context.properties.source.ConfigurationPropertyNameAliases;
import org.springframework.boot.context.properties.source.MapConfigurationPropertySource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @ClassName JdbcTemplateConfig
 * @Description JdbcTemplate配置类
 * @Author KIM
 * @Date 2022/4/6 10:38
 * @Version 1.0
 */
@Configuration
public class JdbcTemplateConfig {

    /**
     * 别名
     */
    private final static ConfigurationPropertyNameAliases aliases = new ConfigurationPropertyNameAliases();

    Class<? extends DataSource> dataSourceClazz = null;

    @Bean("firstJdbc")
    @Primary
    public JdbcTemplate getJdbcTemplate(){
        Properties properties=new Properties();
        //基本参数
        properties.put("driver-class-name","com.mysql.jdbc.Driver");
        properties.put("url", "jdbc:mysql://127.0.0.1:3336/study?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&useSSL=false&nullCatalogMeansCurrent=true");
        properties.put("username", "root");
        properties.put("password", "merit1q98.c0m");
        // 连接校验
        properties.put("time-between-eviction-runs-millis", "5000");
        properties.put("min-evictable-idle-time-millis", "300000");
        properties.put("validation-query", "SELECT 1");
        properties.put("test-while-idle", "true");
        properties.put("test-on-borrow", "false");
        properties.put("test-on-return", "false");
        MapConfigurationPropertySource mapConfigurationPropertySource=new MapConfigurationPropertySource(properties);
        Binder binder = new Binder(mapConfigurationPropertySource.withAliases(aliases));
        // 将参数绑定到对象
        DataSource dataSource = binder.bind(ConfigurationPropertyName.EMPTY, Bindable.of(dataSourceClazz)).get();
        return new JdbcTemplate(dataSource);
    }

    @Bean("second")
    public JdbcTemplate getJdbcTemplateE(){
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3336/cloud?characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&");
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("root");
        dataSource.setPassword("merit1q98.c0m");
        JdbcTemplate jdbcTemplate=new JdbcTemplate(dataSource);
        return new JdbcTemplate(dataSource);
    }

}
