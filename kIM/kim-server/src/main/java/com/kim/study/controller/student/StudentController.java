package com.kim.study.controller.student;

import com.kim.study.common.entity.ProductEntidy;
import com.kim.study.common.menu.AppHttpCodeEnum;
import com.kim.study.common.resultbody.ResultBody;
import com.kim.study.common.resultbody.ResultPage;
import com.kim.study.dto.PageDto;
import com.kim.study.service.student.IStudentService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description: JPA案例
 * Author:KIM
 * Date:2022-03-01
 * Time:17:53
 */
@RestController
@RequestMapping("/kim/student")
@Slf4j
public class StudentController {

    @Autowired
    private IStudentService studentService;

    @PostMapping("/save")
    @ApiOperation(value = "JPA保存接口")
    public ResultBody saveProduct(){
        ProductEntidy result=null;
        try {
            studentService.saveStudent();
        } catch (Exception e) {
            log.error("保存数据异常:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult("新增成功");
    }


    @PostMapping("/queryPage")
    @ApiOperation(value = "JPA分页查询接口")
    public ResultBody queryPage(@RequestBody PageDto pageDto){
        ResultPage resultPage=null;
        try {
             resultPage = studentService.queryPage(pageDto);
        } catch (Exception e) {
            log.error("JPA分页接口异常:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult(resultPage);
    }
}
