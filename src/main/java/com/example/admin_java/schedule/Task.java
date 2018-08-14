package com.example.admin_java.schedule;

import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.repository.ImageRepository;
import com.example.admin_java.service.ImageService;
import com.example.admin_java.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 * 定时任务实现类
 *
 * @Author: Think
 * @Date: 2018/6/7
 * @Time: 23:58
 */
@Slf4j
@Component
public class Task {

    @Autowired
    ImageRepository imageRepository;


    @Scheduled(cron = "0 0 0 * * *")
    public void updateImageStatus() {
        List<ImageEntity> imageEntityList = imageRepository.findFirstByUsedAndTypeOrderByIdAsc(0, 0);
        log.info("start updateImageStatus ...");
        for (ImageEntity imageEntity : imageEntityList) {
            imageEntity.setUsed(1);
            imageRepository.save(imageEntity);
        }
        log.info("completed updateImageStatus ...");
    }


}
