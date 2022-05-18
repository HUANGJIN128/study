package com.kim.study.config;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.kim.study.service.product.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @ClassName BloomConfig
 * @Description 布隆过滤器,可以解决redis穿透,但是需要把所有的key预热到布隆过滤器中
 * @Author KIM
 * @Date 2022/3/13 13:44
 * @Version 1.0
 */
@Configuration
public class BloomConfig implements ApplicationRunner {

    @Autowired
    private IProductService productService;


    public static BloomFilter<String> bloomFilter=BloomFilter.create(Funnels.stringFunnel(Charset.forName("UTF-8")),100);
    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> idS = productService.getIdS();
        for (String id : idS) {
            bloomFilter.put(id);
        }
    }
}
