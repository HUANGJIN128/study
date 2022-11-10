package com.kim.study.common.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QStudentEntity is a Querydsl query type for StudentEntity
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QStudentEntity extends EntityPathBase<StudentEntity> {

    private static final long serialVersionUID = -1724460411L;

    public static final QStudentEntity studentEntity = new QStudentEntity("studentEntity");

    public final com.kim.study.common.base.QBaseBean _super = new com.kim.study.common.base.QBaseBean(this);

    public final StringPath address = createString("address");

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    //inherited
    public final StringPath createUser = _super.createUser;

    //inherited
    public final DateTimePath<java.util.Date> gmtCreate = _super.gmtCreate;

    //inherited
    public final DateTimePath<java.util.Date> gmtModify = _super.gmtModify;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QStudentEntity(String variable) {
        super(StudentEntity.class, forVariable(variable));
    }

    public QStudentEntity(Path<? extends StudentEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStudentEntity(PathMetadata metadata) {
        super(StudentEntity.class, metadata);
    }

}

