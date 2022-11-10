package com.kim.study.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.kim.study.common.entity.ProductEntidy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @ClassName: IProductMapper
 * @Description: TODO
 * @Author: KIM
 * @Date: 2022/2/25 15:02
 * @Version: 1.0
 */

@Mapper
public interface IProductMapper extends BaseMapper<ProductEntidy> {

    String save(String str);

    @Select("SELECT ID FROM KIM_PRODUCT")
    List<String> getIdS();


}
