package com.kim.study.common.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Description:
 * @Author:KIM
 * Date:2022-03-02
 * Time:14:27
 */
@Entity
@Data
@Table(name = "kim_user")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@ApiModel(value = "用户实体,用于登录")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID" )
    @ApiModelProperty(value = "用户主键id")
    private String id;

    @ApiModelProperty(value = "用户姓名")
    @Column(name="USER_NAME",length = 35)
    private String userName;

    @ApiModelProperty(value = "年纪")
    @Column(name = "AGE")
    private Integer age;

    @ApiModelProperty(value = "用户地址")
    @Column(name = "address",length = 25)
    private String address;

    @ApiModelProperty(value = "出生日期")
    @Column(name = "BIRTHDAY",length = 25)
    private Date birthday;

    @ApiModelProperty(value = "编码")
    @Column(name = "CODE",length = 25)
    private String code;

    @ApiModelProperty(value = "密码")
    @Column(name = "PASSWORD",length = 25)
    private String password;

}
