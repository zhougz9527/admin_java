package com.example.admin_java.repository.relation;

import com.example.admin_java.entity.relation.ProvinceCityCountyEntity;
import com.example.admin_java.repository.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/9/5
 * @Time: 12:09
 */
public interface ProvinceCityCountyRepository extends BaseRepository<ProvinceCityCountyEntity, Integer> {

    //SELECT province_name, city_name, county_name, weather_id FROM (`province` p LEFT JOIN `city` c ON p.province_code = c.province_id) LEFT JOIN `county` cc ON cc.city_id = c.city_code;
    @Query(value = "select cc.id, province_id, province_name, city_code, city_name, county_name, weather_id from (province p left join city c on p.province_code = c.province_id) left join county cc on cc.city_id = c.city_code", nativeQuery = true)
    List<ProvinceCityCountyEntity> findAlls();

}
