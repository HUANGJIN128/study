package com.kim.study.common.resultbody;

import com.kim.study.common.dto.BasePageDto;
import lombok.Data;

import java.util.List;

/**
 * Description: 分页返回对象
 * Author:KIM
 * Date:2022-03-01
 * Time:20:22
 */
@Data
public class ResultPage<T> extends BasePageDto {

    /**
     * 总数
     */
    private Long total;
    /**
     *
     */
    private List<T> data;

    public ResultPage(Long total, List<T> data) {
        this.total = total;
        this.data = data;
    }

}
