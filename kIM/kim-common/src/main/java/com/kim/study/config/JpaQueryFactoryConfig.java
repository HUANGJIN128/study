package com.kim.study.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
/**
 * @ClassName: AutoMetaObjectHandler
 * @Description: querydsl配置类
 * @Author: KIM
 * @Date: 2022/2/25 16:44
 * @Version: 1.0
 */
@Component
public class JpaQueryFactoryConfig {

    @Autowired
    public static EntityManager entityManager;

    @Autowired
    public static JPAQueryFactory jpaQueryFactory;

    @Bean
    @Autowired
    public static JPAQueryFactory getJpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }
}