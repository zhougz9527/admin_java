package com.example.admin_java.schedule;

import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Component
public class Task {

    private static Logger logger = LoggerFactory.getLogger(Task.class);

    @Autowired
    ImageRepository imageRepository;


    @Scheduled(cron = "0 0 0 * * *")
    public void updateImageStatus() {
        List<ImageEntity> imageEntityList = imageRepository.findFirstByUsedAndTypeOrderByIdAsc(0, 0);
        logger.info("start updateImageStatus ...");
        for (ImageEntity imageEntity : imageEntityList) {
            imageEntity.setUsed(1);
            imageRepository.save(imageEntity);
        }
        logger.info("completed updateImageStatus ...");
    }


}
