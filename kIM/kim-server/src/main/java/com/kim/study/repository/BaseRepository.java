package com.kim.study.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * jpa基础repository
 * @param <T>
 */

@NoRepositoryBean
public interface BaseRepository<T> extends
        JpaRepository<T,Long>,
        JpaSpecificationExecutor<T>,
        QuerydslPredicateExecutor<T> {
}