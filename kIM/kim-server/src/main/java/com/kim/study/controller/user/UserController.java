package com.kim.study.controller.user;

import com.kim.study.entity.UserEntity;
import com.kim.study.menu.AppHttpCodeEnum;
import com.kim.study.resultbody.ResultBody;
import com.kim.study.service.user.IUserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @ClassName UserController
 * @Description TODO
 * @Author KIM
 * @Date 2022/3/2 15:41
 * @Version 1.0
 */

@RestController
@RequestMapping("/kim/user")
@Slf4j
public class UserController {

    @Autowired
    private IUserService userService;




    @GetMapping("/query-user-by-name")
    @ApiOperation(value = "根据用户名获取用户信息接口")
    public ResultBody queryPage( String name){


        UserEntity user=null;
        try {
            user = userService.getUserByUserName(name);
        } catch (Exception e) {
            log.error("根据用户名获取用户信息接口:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult(user);
    }


    @PostMapping("updateLode")
    @ApiOperation(value = "gcexcel上传文件")
    public ResultBody updateLode( MultipartFile multipartFile){

        try {
            userService.updateLode(multipartFile);
        } catch (Exception e) {
            log.error("gcexcel上传文件:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult();
    }



}
