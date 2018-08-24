package com.example.admin_java.repository;

import com.example.admin_java.entity.CityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:30
 */
public interface CityRepository extends BaseRepository<CityEntity, Integer> {

    List<CityEntity> findByProvinceId(int id);

    @Query("select city from CityEntity city")
    Page<CityEntity> findALL(Pageable pageable);
}
