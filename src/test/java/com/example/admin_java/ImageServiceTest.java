package com.example.admin_java;

import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.service.ImageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/7/25
 * @Time: 14:40
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImageServiceTest {

    @Autowired
    ImageService imageService;

    @Test
    public void test() {
        imageService.saveImage();
//        List<ImageEntity> list = imageService.findByUsedAndType();
//        System.out.println(list);
    }

}
