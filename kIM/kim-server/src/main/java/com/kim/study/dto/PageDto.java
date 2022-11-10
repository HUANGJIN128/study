package com.kim.study.dto;

import com.kim.study.common.dto.BasePageDto;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Description: 产品分页列表
 * Author:KIM
 * Date:2022-03-01
 * Time:16:35
 */
@Data
public class PageDto extends BasePageDto {
    private String name;
    private BigDecimal minpriace;
    private BigDecimal maxpriace;
}
