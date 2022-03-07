package com.kim.study.repository;

import com.kim.study.entity.UserEntity;
import org.springframework.stereotype.Component;

/**
 * @ClassName IUserReporsitory
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:41
 * @Version 1.0
 */
@Component
public interface IUserReporsitory extends BaseRepository<UserEntity>{

    /**
     * 通过用户名查询用户信息
     * @param name
     * @return
     */
    UserEntity findByUserName(String name);

}
