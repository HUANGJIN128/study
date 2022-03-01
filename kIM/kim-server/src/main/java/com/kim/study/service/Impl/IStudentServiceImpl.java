package com.kim.study.service.Impl;

import com.kim.study.dto.PageDto;
import com.kim.study.entity.QStudentEntity;
import com.kim.study.entity.StudentEntity;
import com.kim.study.repository.IStudentRepository;
import com.kim.study.resultbody.ResultPage;
import com.kim.study.service.IStudentService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Description:
 * Author:KIM
 * Date:2022-03-01
 * Time:17:54
 */
@Service
public class IStudentServiceImpl implements IStudentService {
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void saveStudent() {
        String[] nameARR={"赵","钱","孙","李","周","吴","郑","王"};
        String[] addARR={"西安","渭南","咸阳","汉中","宝鸡","榆林","延安","安康"};

        for (int i = 0; i < 20; i++) {
            StudentEntity studentEntity=new StudentEntity();
            studentEntity.setName(nameARR[new Random().nextInt(nameARR.length-1)]+nameARR[new Random().nextInt(nameARR.length-1)]);
            studentEntity.setAddress(addARR[new Random().nextInt(addARR.length-1)]);
            studentEntity.setAge(new Random().nextInt(100));
            studentEntity.setGmtCreate(new Date());
            studentRepository.save(studentEntity);
        }
    }

    @Override
    public ResultPage queryPage(PageDto pageDto) {
        QStudentEntity qStudentEntity = QStudentEntity.studentEntity;
        List<String> addList=new ArrayList<>();
        addList.add("西安");
        addList.add("渭南");
        Predicate  predicate = qStudentEntity.address.in(addList);
        if(StringUtils.isNotEmpty(pageDto.getName())){
            predicate=ExpressionUtils.and(predicate,qStudentEntity.name.eq(pageDto.getName()));
        }
        Pageable pageable= PageRequest.of(pageDto.getPage()-1,pageDto.getRows());
        JPAQuery<StudentEntity> query = jpaQueryFactory.selectFrom(qStudentEntity)
                .where(predicate);
        long total = query.fetchCount();
        List<StudentEntity> fetch = query.limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .orderBy(qStudentEntity.age.desc())
                .fetch();
        return new ResultPage(total,fetch);
    }
}
