package com.example.admin_java.repository;

import com.example.admin_java.entity.CityEntity;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:30
 */
public interface CityRepository extends BaseRepository<CityEntity, Integer> {

    List<CityEntity> findByProvinceId(int id);
}
