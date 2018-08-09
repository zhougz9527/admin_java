package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:21
 */
@Data
@Entity
@Table(name = "province")
public class ProvinceEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "province_code")
    private int provinceCode;

}
