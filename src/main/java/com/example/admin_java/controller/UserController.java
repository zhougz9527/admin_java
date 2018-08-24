package com.example.admin_java.controller;

import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Result logout(@RequestAttribute UserEntity userEntity) {
        String account = userEntity.getAccount();
        boolean isSuccess = redisService.delete(account);
        Map<String, Boolean> resultMap = new HashMap<>();
        resultMap.put("isLogout", isSuccess);
        return ResultUtil.success(resultMap);
    }

}
