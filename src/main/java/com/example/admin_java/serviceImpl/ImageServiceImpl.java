package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.admin_java.entity.ImageEntity;
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

    /**
     * 保存图片到数据库
     */
    @Override
    public void saveImage() {
        String url = "http://gank.io/api/data/福利/10/51";
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
    }

    /**
     * 获取未使用的第一张图片
     * @param used
     * @param type
     * @return
     */
    @Override
    public List<ImageEntity> findFirstByUsedAndTypeOrderByIdAsc(int used, int type) {
        return imageRepository.findFirstByUsedAndTypeOrderByIdAsc(used, type);
    }
}
