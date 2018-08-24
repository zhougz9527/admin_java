package com.example.admin_java.controller;

import com.example.admin_java.entity.CityEntity;
import com.example.admin_java.repository.CityRepository;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Think
 * @Date: 2018/8/22
 * @Time: 17:54
 */
@Slf4j
@RestController
@RequestMapping("/test")
public class TestController extends BaseController {

    @Autowired
    CityRepository cityRepository;


    @GetMapping(path = "/test")
    public Result test() {
        int page = 2, size = 10;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<CityEntity> pages = cityRepository.findALL(pageable);
        return ResultUtil.success(pages.getContent());
    }

}
