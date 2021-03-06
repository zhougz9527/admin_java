package com.example.admin_java.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.entity.CityEntity;
import com.example.admin_java.entity.CountyEntity;
import com.example.admin_java.entity.ProvinceEntity;
import com.example.admin_java.entity.relation.ProvinceCityCountyEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.repository.CityRepository;
import com.example.admin_java.repository.CountyRepository;
import com.example.admin_java.repository.ProvinceRepository;
import com.example.admin_java.repository.relation.ProvinceCityCountyRepository;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.DayService;
import com.example.admin_java.utils.OKHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 22:59
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    DayService dayService;
    @Autowired
    ProvinceRepository provinceRepository;
    @Autowired
    CityRepository cityRepository;
    @Autowired
    CountyRepository countyRepository;
    @Autowired
    ProvinceCityCountyRepository provinceCityCountyRepository;


    @GetMapping("")
    public Result getIPWeather(HttpServletRequest httpServletRequest) {
//        String ip = httpServletRequest.getRemoteAddr();
        String ip = "36.24.186.191";
        String url = Constant.IP_URL + "?ip=" + ip;
        String response = OKHttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        String city = jsonObject1.getString("city");
        List<CountyEntity> countyEntities = countyRepository.findByCountyNameContaining(city);
        if (1 != countyEntities.size()) {
            return ResultUtil.error(10016);
        }
        String weatherId = countyEntities.get(0).getWeatherId();
        String weatherUrl = Constant.WEATHER_URL + "?cityid=" + weatherId + "&key=" + Constant.WEATHER_KEY;
        Object object = dayService.getWeather(weatherUrl);
        return ResultUtil.success(object);
    }

    @GetMapping("/getweather")
    public Result getWeather(@RequestParam(value = "weatherid", defaultValue = "") String weatherId) {
        String url = Constant.WEATHER_URL + "?cityid=" + weatherId + "&key=" + Constant.WEATHER_KEY;
        Object object = dayService.getWeather(url);
        return ResultUtil.success(object);
    }


    @GetMapping("/search")
    public Result search(@RequestParam(value = "name", defaultValue = "") String city) {
        if (StringUtils.isEmpty(city)) {
            return ResultUtil.succeedNoData();
        }
        List<CountyEntity> countyEntityList = countyRepository.findByCountyNameContaining(city);
        if (null == countyEntityList || 0 == countyEntityList.size()) {
            return ResultUtil.succeedNoData();
        }
        List<Map<String, String>> resultList = new ArrayList<>();
        for (CountyEntity countyEntity : countyEntityList) {
            Map<String, String> map = new HashMap<>();
            String weatherId = countyEntity.getWeatherId();
            String name = countyEntity.getCountyName();
            map.put("name", name);
            map.put("weatherId", weatherId);
            resultList.add(map);
        }
        return ResultUtil.success(resultList);
    }

    @GetMapping("/getcity")
    public Result getCity() {
        List<ProvinceCityCountyEntity> list = provinceCityCountyRepository.findAlls();
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (ProvinceCityCountyEntity entity : list) {
          Map<String, Object> tempMap = new HashMap<>();
          tempMap.put("provinceName", entity.getProvinceName());
          tempMap.put("cityName", entity.getCityName());
          tempMap.put("countyName", entity.getCountyName());
          tempMap.put("weatherId", entity.getWeatherId());
          mapList.add(tempMap);
        }
        return ResultUtil.success(mapList);
    }


}
