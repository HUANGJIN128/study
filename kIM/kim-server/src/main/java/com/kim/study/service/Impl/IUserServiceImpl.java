package com.kim.study.service.Impl;

import com.kim.study.entity.UserEntity;
import com.kim.study.repository.IUserReporsitory;
import com.kim.study.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    @Override
    public UserEntity getUserByUserName(String name) {
        UserEntity byName = iUserReporsitory.findByUserName(name);
        return byName;
    }
}
