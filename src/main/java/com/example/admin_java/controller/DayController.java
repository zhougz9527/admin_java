package com.example.admin_java.controller;

import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.repository.ImageRepository;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.DayService;
import com.example.admin_java.utils.OKHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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


    @GetMapping("/article")
    public Result article (@RequestParam(value = "random", defaultValue = "false") Boolean random) {
        String url = Constant.ARTICLE_URL;
        if (true == random) {
            url += "random";
        }
        String response = OKHttpUtil.get(url);
        Document document = Jsoup.parse(response);
        String title = document.title();
        String author = "";
        String header = "";
        if (!StringUtils.isEmpty(title)) {
            String[] strings = title.split("\\|");
            String string = strings[0];
            String[] titles = string.split(" ");
            author = titles[1];
            header = titles[0];
        }
        Elements elements = document.getElementsByClass("article_text");
        String content = "";
        for (Element element : elements) {
            content = element.text();
        }
        Map<String, String> result = new HashMap<>();
        result.put("author", author);
        result.put("header", header);
        result.put("content", content);
        return ResultUtil.success(result);
    }

}
