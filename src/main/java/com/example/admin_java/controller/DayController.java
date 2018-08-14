package com.example.admin_java.controller;

import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.repository.ImageRepository;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.DayService;
import com.example.admin_java.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Think
 * @Date: 2018/8/9
 * @Time: 10:06
 */
@Slf4j
@RestController
@RequestMapping("/day")
public class DayController extends BaseController {

    @Autowired
    ImageRepository imageRepository;
    @Autowired
    DayService dayService;


    @GetMapping("/meizi")
    public Result meizi () {
        List<ImageEntity> imageEntityList = imageRepository.findFirstByUsedAndTypeOrderByIdAsc(0, 0);
        List<Map<String, String>> meiziList = new ArrayList<>();
        for (ImageEntity imageEntity : imageEntityList) {
            Map<String, String> meiziMap = new HashMap<>();
            meiziMap.put("url", imageEntity.getImageUrl());
            meiziList.add(meiziMap);
        }
        return ResultUtil.success(meiziList);
    }

}
