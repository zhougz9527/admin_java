package com.example.admin_java.entity.relation;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @Author: Think
 * @Date: 2018/9/5
 * @Time: 11:11
 */
@Data
@Entity
@Table(name = "province")
public class ProvinceCityCountyEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private int id;
    @Column(name = "province_id")
    private int provinceId;
    @Column(name = "province_name")
    private String provinceName;
    @Column(name = "city_code")
    private int cityId;
    @Column(name = "city_name")
    private String cityName;
    @Column(name = "county_name")
    private String countyName;
    @Column(name = "weather_id")
    private String weatherId;

}
