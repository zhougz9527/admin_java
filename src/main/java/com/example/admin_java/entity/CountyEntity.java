package com.example.admin_java.entity;

import lombok.Data;
import lombok.extern.apachecommons.CommonsLog;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:26
 */
@Data
@Entity
@Table(name = "county")
public class CountyEntity implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "city_id")
    private int cityId;
    @Column(name = "county_name")
    private String countyName;
    @Column(name = "weather_id")
    private String weatherId;

}
