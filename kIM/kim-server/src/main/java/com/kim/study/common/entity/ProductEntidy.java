package com.kim.study.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.kim.study.common.base.BaseBean;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @ClassName: ProductEntidy
 * @Description: 产品表
 * @Author: KIM
 * @Date: 2022/2/23 18:08
 * @Version: 1.0
 */
@Data
@TableName("kim_product")
public class ProductEntidy extends BaseBean {

    /**
     * 主键自增
     */
    @TableId(value = "ID",type = IdType.UUID)
    private String id;

    /**
     * 价格
     */
    @TableField(value = "PRIACE",exist = true)
    private BigDecimal priace;

    /**
     * 名称
     */
    @TableField("NAME")
    private String name;

    /**
     * 品牌
     */
    @TableField("BRAND")
    private String brand;
}
