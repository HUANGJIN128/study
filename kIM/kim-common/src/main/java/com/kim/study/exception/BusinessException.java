package com.kim.study.exception;

import lombok.Data;

/**
 * @ClassName: BusinessException
 * @Description: 自定义异常类(根据业务情况可以在系统中丢出异常)
 * @Author: KIM
 * @Date: 2022/2/25 16:08
 * @Version: 1.0
 */

@Data
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;


    private Integer code;
    private String msg;

    public BusinessException( Integer code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(Throwable cause, Integer code, String msg) {
        super(cause);
        this.code = code;
        this.msg = msg;
    }
}
