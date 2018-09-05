package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.DayService;
import com.example.admin_java.utils.OKHttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 17:14
 */
@Service
public class DayServiceImpl extends BaseServiceImpl implements DayService {

    @Override
    public String getIpCity(String url) {
        String city = "";
        String response = OKHttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(response);
        JSONObject jsonObject1 = (JSONObject) jsonObject.get("data");
        city = jsonObject1.getString("city");
        return city;
    }

    @Override
    public Object getWeather(String url) {
        String response = OKHttpUtil.get(url);
        if (!StringUtils.isEmpty(response) && !"Request weather info with error: undefined method `city' for nil:NilClass".equals(response)) {
            JSONObject jsonObject = JSON.parseObject(response);
            if (null != jsonObject) {
                return jsonObject;
            }
        }
        return null;
    }
}
