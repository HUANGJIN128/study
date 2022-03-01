package com.kim.study.resultbody;


import com.kim.study.base.BaseBean;
import com.kim.study.container.StrContainer;


/**
 * @ClassName: ProductEntidy
 * @Description: 通用的返回结果类
 * @Author: KIM
 * @Date: 2022/2/23 18:08
 * @Version: 1.0
 */
public class ResultBody<T> {

    private Integer code;

    private String msg;

    private T data;

    public ResultBody() {
        this.code = 200;
    }

    public ResultBody(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResultBody(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResultBody(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultBody errorResult(int code, String msg) {
        ResultBody result = new ResultBody();
        return result.error(code, msg);
    }

    public static ResultBody okResult() {
        ResultBody result = new ResultBody();
        return result.ok(result.getCode(), null, StrContainer.success);
    }

    public static ResultBody okResult(Object data) {
        ResultBody result = new ResultBody();
        return result.ok(result.getCode(), data, StrContainer.success);
    }

    public ResultBody<?> error(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }

    public ResultBody<?> ok(Integer code, T data) {
        this.code = code;
        this.data = data;
        return this;
    }

    public ResultBody<?> ok(Integer code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        return this;
    }

    public  ResultBody<?> ok(T data) {
        this.data = data;
        return this;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
