package com.example.admin_java;

import com.example.admin_java.entity.CityEntity;
import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.repository.CityRepository;
import com.example.admin_java.schedule.Task;
import com.example.admin_java.service.ImageService;
import com.example.admin_java.service.WebSocketService;
import com.example.admin_java.utils.JWTUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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
    @Autowired
    CityRepository cityRepository;

    @Test
    public void test() {
        String username = JWTUtil.getUsername("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1MzUwMTc1MDYsInVzZXJuYW1lIjoiMTY0OTU2MzMzNkBxcS5jb20ifQ.gd26sU3fogMA1sESw1NPq42exDl1gnRUIuDYSwf7fOM");
        System.out.println(username);
    }

    @Test
    public void test1() {
        int page = 1, size = 10;
        Sort sort = new Sort(Sort.Direction.ASC, "id");
        Pageable pageable = new PageRequest(page, size, sort);
        Page<CityEntity> pages = cityRepository.findALL(pageable);
        System.out.println(pages);
    }

}
