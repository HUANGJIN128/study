package com.kim.study.controller;

import com.kim.study.entity.ProductEntidy;
import com.kim.study.menu.AppHttpCodeEnum;
import com.kim.study.resultbody.ResultBody;
import com.kim.study.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName: ProductController
 * @Description: 商品控制器
 * @Author: KIM
 * @Date: 2022/2/25 14:59
 * @Version: 1.0
 */


@RestController
@RequestMapping("/kim/product")
@Slf4j
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @PostMapping("/save")
    public ResultBody saveProduct(){
        ProductEntidy result=null;
        try {
            iProductService.savePro();
        } catch (Exception e) {
            log.error("保存数据异常:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult("新增成功");
    }
}
