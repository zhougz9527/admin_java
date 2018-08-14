package com.example.admin_java.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.example.admin_java.entity.ImageEntity;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.entity.VerifyCodeEntity;
import com.example.admin_java.global.Constant;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.*;
import com.example.admin_java.utils.*;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Think
 * @Date: 2018/5/24
 * @Time: 22:41
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    RedisService redisService;

    /**
     * 登出
     * @return
     */
    @GetMapping(path = "/logout")
    public Result logout(HttpServletRequest httpServletRequest) {
        String token = httpServletRequest.getHeader(Constant.HEADER_KEY);
        boolean isSuccess = redisService.delete(token);
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isLogout", isSuccess);
        return ResultUtil.success(resultMap);
    }

}
