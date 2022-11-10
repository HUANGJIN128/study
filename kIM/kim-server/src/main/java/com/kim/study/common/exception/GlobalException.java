package com.kim.study.common.exception;

import com.kim.study.common.menu.AppHttpCodeEnum;
import com.kim.study.common.resultbody.ResultBody;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @ClassName: ProductEntidy
 * @Description: 全局异常处理,根据不同异常处理不同情况
 * @Author: KIM
 * @Date: 2022/2/23 18:08
 * @Version: 1.0
 */
@ControllerAdvice
@Slf4j
public class GlobalException {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(Exception e){
        log.error("error exception:{}",e.getMessage());
        return  ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),AppHttpCodeEnum.SERVER_ERROR.getErrorMessage());
    }

    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResultBody businessExceptionHandler(BusinessException e){
        log.error("error exception:{}",e.getMessage());
        return  ResultBody.errorResult(e.getCode(),e.getMsg());
    }
}