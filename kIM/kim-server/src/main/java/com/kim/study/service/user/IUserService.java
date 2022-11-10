package com.kim.study.service.user;

import com.kim.study.common.entity.UserEntity;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:38
 * @Version 1.0
 */

public interface IUserService<T> {


    UserEntity getUserByUserName(String name);


}
