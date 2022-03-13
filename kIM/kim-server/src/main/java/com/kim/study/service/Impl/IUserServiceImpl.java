package com.kim.study.service.Impl;

import com.kim.study.entity.UserEntity;
import com.kim.study.repository.IUserReporsitory;
import com.kim.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executor;

/**
 * @ClassName IUserServiceImpl
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:40
 * @Version 1.0
 */
@Service
public class IUserServiceImpl implements IUserService {

    @Autowired
    private IUserReporsitory iUserReporsitory;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private Executor executor;


    @Override
    public UserEntity getUserByUserName(String name) {
        UserEntity byName = iUserReporsitory.findByCode(name);
        String code = byName.getCode();
        redisTemplate.opsForValue().set(code,"hahahahahahha");
        String o = (String) redisTemplate.opsForValue().get(byName.getCode());
        //异步测试代码,同时可以写一个方法,上面加@Async("taskExecutor")注解实现异步操作
        //executor.execute(new test());
        System.out.println(o);
        return byName;
    }
}
