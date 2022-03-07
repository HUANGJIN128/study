package com.kim.study.service;

import com.kim.study.entity.UserEntity;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:38
 * @Version 1.0
 */

public interface IUserService {


    UserEntity getUserByUserName(String name);

}
