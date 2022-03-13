package com.kim.study.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kim.study.config.BloomConfig;
import com.kim.study.dto.PageDto;
import com.kim.study.entity.ProductEntidy;
import com.kim.study.exception.BusinessException;
import com.kim.study.mapper.IProductMapper;
import com.kim.study.menu.AppHttpCodeEnum;
import com.kim.study.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * @ClassName: IProductServiceImpl
 * @Description: TODO
 * @Author: KIM
 * @Date: 2022/2/25 15:01
 * @Version: 1.0
 */
@Service
@Slf4j
public class IProductServiceImpl extends ServiceImpl<IProductMapper,ProductEntidy> implements IProductService {


    @Autowired
    private IProductMapper productMapper;


    /**
     * 保存商品
     * @param
     * @return
     */
    @Override
    public void savePro() {

        String[] nameArr={"雨伞","手机","空调","电视","蓝牙音响","微波炉","手表","电脑"};
        String[] brandArr={"小米","华为","苹果","联想"};
        for (int i = 0; i < 20; i++) {
            ProductEntidy productEntidy=new ProductEntidy();
            Integer v = new Random().nextInt(10000);
            BigDecimal priace=new BigDecimal(String.valueOf(v));
            productEntidy.setPriace(priace);
            productEntidy.setName(nameArr[new Random().nextInt(nameArr.length-1)]);
            productEntidy.setBrand(brandArr[new Random().nextInt(brandArr.length-1)]);
            productMapper.insert(productEntidy);

        }
    }

    @Override
    public IPage<ProductEntidy> pageQuery(PageDto productPageDto) {
        QueryWrapper<ProductEntidy> query=new QueryWrapper<>();
        if(StringUtils.isNotEmpty(productPageDto.getName())){
            query.eq("NAME",productPageDto.getName());
        }
        if(ObjectUtils.isNotEmpty(productPageDto.getMinpriace())){
            query.ge("PRIACE",productPageDto.getMinpriace());
        }
        if(ObjectUtils.isNotEmpty(productPageDto.getMaxpriace())){
            query.le("PRIACE",productPageDto.getMaxpriace());
        }
        IPage<ProductEntidy> page=new Page<>(productPageDto.getPage(),productPageDto.getRows());
        return productMapper.selectPage(page, query);
    }

    @Override
    public List<String> getIdS() {
        List<String> idS = productMapper.getIdS();
        return idS;
    }



    @Override
    public ProductEntidy getOne(String id) {
        boolean b = BloomConfig.bloomFilter.mightContain(id);
        if (!b){
            log.error("该数据不存在");
            throw new BusinessException(AppHttpCodeEnum.DATA_NOT_EXIST.getCode(),AppHttpCodeEnum.DATA_NOT_EXIST.getErrorMessage());
        }
        ProductEntidy productEntidy = productMapper.selectById(id);
        return productEntidy;
    }
}
