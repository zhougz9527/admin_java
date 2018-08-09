package com.example.admin_java.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:24
 */
@Data
@Entity
@Table(name = "city")
public class CityEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "province_id")
    private int provinceId;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "city_code")
    private int cityCode;

}
