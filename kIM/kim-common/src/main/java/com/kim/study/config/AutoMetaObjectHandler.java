package com.kim.study.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @ClassName: AutoMetaObjectHandler
 * @Description: mybatis-plus的字段自动填充
 * @Author: KIM
 * @Date: 2022/2/25 16:44
 * @Version: 1.0
 */

@Component
@Slf4j
public class AutoMetaObjectHandler implements MetaObjectHandler {

    /**
     * 新增自动填充为当前时间
     * @param metaObject
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("新增，gmtCreate,gmtModify字段日期自动填充...");

        // 两个字段在 插入时填充
        this.setFieldValByName("gmtCreate", new Date(), metaObject);
        this.setFieldValByName("gmtModify", new Date(), metaObject);
    }

    /**
     * 修改自动填充为当前时间
     * @param metaObject
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        log.debug("修改，gmtModify日期自动填充更新...");
        // 在修改时填充
        this.setFieldValByName("gmtModify", new Date(), metaObject);
    }

}
