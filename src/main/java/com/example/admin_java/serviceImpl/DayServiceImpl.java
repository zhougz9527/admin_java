package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.service.DayService;
import com.example.admin_java.utils.OKHttpUtil;
import org.springframework.stereotype.Service;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 17:14
 */
@Service
public class DayServiceImpl implements DayService {

    @Override
    public String getIpCity(String url) {
        String city = "";
        String response = OKHttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
        city = jsonObject1.getString("city");
        return city;
    }
}
