package com.kim.study.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @ClassName: BaseBean
 * @Description: TODO
 * @Author: KIM
 * @Date: 2022/2/25 15:36
 * @Version: 1.0
 */
@Data
//父类中的属性可以映射到子类中
@MappedSuperclass
public class BaseBean implements Serializable {

    private  static  final long serialVersionUId = -6195406170840437008L;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT,value = "GMT_CREATE")
    @Column(name = "GMT_CREATE")
    private Date gmtCreate;
    /**
     * 修改时间
     */
    @TableField(value = "GMT_MODITY",fill = FieldFill.INSERT_UPDATE)
    @Column(name = "GMT_MODITY")
    private Date gmtModify;
    /**
     * 创建人
     */
    @TableField("CREATE_USER")
    @Column(name = "CREATE_USER")
    private String createUser;

}
