package com.kim.study.common.base;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QBaseBean is a Querydsl query type for BaseBean
 */
@Generated("com.querydsl.codegen.SupertypeSerializer")
public class QBaseBean extends EntityPathBase<BaseBean> {

    private static final long serialVersionUID = 1173827980L;

    public static final QBaseBean baseBean = new QBaseBean("baseBean");

    public final StringPath createUser = createString("createUser");

    public final DateTimePath<java.util.Date> gmtCreate = createDateTime("gmtCreate", java.util.Date.class);

    public final DateTimePath<java.util.Date> gmtModify = createDateTime("gmtModify", java.util.Date.class);

    public QBaseBean(String variable) {
        super(BaseBean.class, forVariable(variable));
    }

    public QBaseBean(Path<? extends BaseBean> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseBean(PathMetadata metadata) {
        super(BaseBean.class, metadata);
    }

}

