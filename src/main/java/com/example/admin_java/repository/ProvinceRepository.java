package com.example.admin_java.repository;

import com.example.admin_java.entity.ProvinceEntity;
import com.example.admin_java.entity.relation.ProvinceCityCountyEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 23:29
 */
public interface ProvinceRepository extends BaseRepository<ProvinceEntity, Integer> {

    ProvinceEntity findByProvinceName(String name);

    List<ProvinceEntity> findByProvinceNameContaining(String name);
//SELECT province_name, city_name, county_name, weather_id FROM (`province` p LEFT JOIN `city` c ON p.province_code = c.province_id) LEFT JOIN `county` cc ON cc.city_id = c.city_code;
//    @Query(value = "select province_id, province_name, city_code, city_name, county_name, weather_id from (ProvinceEntity p left join CityEntity c on p.province_code = c.province_id) left join CountyEntity cc on cc.city_id = c.city_code", nativeQuery = true)
//    List<ProvinceCityCountyEntity> findAlls();

}
