package com.kim.study.service;

import com.kim.study.entity.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @ClassName IUserService
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 14:38
 * @Version 1.0
 */

public interface IUserService<T> {


    UserEntity getUserByUserName(String name);

    void updateLode(MultipartFile multipartFile) throws IOException;

}
