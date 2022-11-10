package com.kim.study.common.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.kim.study.common.base.BaseBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "kim_student")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "fieldHandler"})
@ApiModel(value = "学生类")
public class StudentEntity extends BaseBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id" )
    @ApiModelProperty(value = "用户主键id")
    private Long id;

    @ApiModelProperty(value = "用户姓名")
    @Column(name="name",length = 35)
    private String name;

    @ApiModelProperty(value = "用户姓名")
    @Column(name = "age")
    private Integer age;

    @ApiModelProperty(value = "用户地址")
    @Column(name = "address",length = 25)
    private String address;

}
