package com.kim.study.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kim.study.dto.ProductPageDto;
import com.kim.study.entity.ProductEntidy;

/**
 * @ClassName: IProductService
 * @Description: TODO
 * @Author: KIM
 * @Date: 2022/2/25 15:00
 * @Version: 1.0
 */

public interface IProductService extends IService<ProductEntidy> {

    void savePro();

    IPage<ProductEntidy> pageQuery(ProductPageDto productPageDto);
}
