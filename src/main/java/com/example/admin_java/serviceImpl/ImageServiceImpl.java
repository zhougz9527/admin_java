package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.repository.ImageRepository;
import com.example.admin_java.service.ImageService;
import com.example.admin_java.utils.OKHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/7/25
 * @Time: 14:15
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {

    @Autowired
    ImageRepository imageRepository;

    @Override
    public void saveImage(String url) {
        log.info("meizi url: {}", url);
        String rsp = OKHttpUtil.get(url);
        JSONObject jsonObject = JSON.parseObject(rsp);
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            String imageUrl = jsonObject1.getString("url");
            log.info("image url: {}", imageUrl);
            ImageEntity entity = imageRepository.findByImageUrl(imageUrl);
            if (null == entity) {
                ImageEntity imageEntity = new ImageEntity();
                imageEntity.setImageUrl(imageUrl);
                imageEntity.setTitle("");
                imageRepository.save(imageEntity);
            }
        }
        log.info("insert completed ... ");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("Thread sleep exception: {}", e.getMessage());
        }
    }

}
