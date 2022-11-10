package com.kim.study;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.kim.study.common.entity.ProductEntidy;
import com.kim.study.dto.PageDto;
import com.kim.study.service.product.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @ClassName TestApplication
 * @Description
 * @Author HuangJ
 * @Date 2022/11/10 13:53
 * @Version 1.0
 */
@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class TestApplication {

    @Autowired
    private IProductService iProductService;

    @Test
    public void test(){
        PageDto pageDto=new PageDto();
        pageDto.setPage(1);
        pageDto.setRows(10);
        IPage<ProductEntidy> productEntidyIPage = iProductService.pageQuery(pageDto);
        log.info(""+productEntidyIPage.getRecords());
    }
}
