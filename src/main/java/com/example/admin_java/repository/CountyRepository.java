package com.example.admin_java.repository;

import com.example.admin_java.entity.CityEntity;
import com.example.admin_java.entity.CountyEntity;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:31
 */
public interface CountyRepository extends BaseRepository<CountyEntity, Integer> {

    List<CountyEntity> findByCityId(int id);

    List<CountyEntity> findByCountyNameContaining(String name);

}
