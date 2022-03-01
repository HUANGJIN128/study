package com.kim.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kim.study.entity.ProductEntidy;

/**
 * @ClassName: IProductMapper
 * @Description: TODO
 * @Author: KIM
 * @Date: 2022/2/25 15:02
 * @Version: 1.0
 */
public interface IProductMapper extends BaseMapper<ProductEntidy> {

    String save(String str);


}
