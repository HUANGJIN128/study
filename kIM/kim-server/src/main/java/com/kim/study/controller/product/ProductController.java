package com.kim.study.controller.product;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kim.study.common.entity.ProductEntidy;
import com.kim.study.common.menu.AppHttpCodeEnum;
import com.kim.study.common.resultbody.ResultBody;
import com.kim.study.dto.PageDto;
import com.kim.study.service.product.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @ClassName: ProductController
 * @Description: 商品控制器(mybatisplus案例)
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


    @PostMapping("/pageQuery")
    public ResultBody pageQuery(@RequestBody PageDto params){
        IPage<ProductEntidy> page=null;
        try {
            page = iProductService.pageQuery(params);
        } catch (Exception e) {
            log.error("保存数据异常:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult(page);
    }
    @PostMapping("/getOne")
    public ResultBody getOne(@RequestParam String id){
        ProductEntidy result=null;
        try {
            result =  iProductService.getOne(id);
        } catch (Exception e) {
            log.error("获取数据失败:"+e.getMessage());
            return ResultBody.errorResult(AppHttpCodeEnum.SERVER_ERROR.getCode(),e.getMessage());
        }
        return ResultBody.okResult(result);
    }



}
