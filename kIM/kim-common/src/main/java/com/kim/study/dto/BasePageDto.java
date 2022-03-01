package com.kim.study.dto;

import lombok.Data;

/**
 * @ClassName: BasePageDto
 * @Description: 分页参数基础类
 * @Author: KIM
 * @Date: 2022/2/25 17:32
 * @Version: 1.0
 */
@Data
public class BasePageDto {

    /**
     * 当前页
     */
    private Integer page;
    /**
     * 每页条数
     */
    private Integer rows;
}
