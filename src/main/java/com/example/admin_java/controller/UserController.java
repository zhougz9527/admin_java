package com.example.admin_java.controller;

import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.UserService;
import com.example.admin_java.utils.MD5Util;
import com.example.admin_java.utils.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/5/24
 * @Time: 22:41
 */
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    UserService userService;

    /**
     *
     * 添加用户
     *
     * @param username
     * @param avatar
     * @param password
     * @param nickname
     * @return
     */
    @PostMapping(path = "/addUser")
    public Result addUser(@RequestParam(value = "username", defaultValue = "") String username,
                          @RequestParam(value = "avatar", defaultValue = "") String avatar,
                          @RequestParam(value = "password", defaultValue = "") String password,
                          @RequestParam(value = "nickname", defaultValue = "") String nickname) {
        if (!RegexUtil.isMobile(username)) {
            return ResultUtil.error(10004);
        }
        if (!(password.length() >= 6 && password.length() <= 12)) {
            return ResultUtil.error(10003);
        }
        if (!RegexUtil.isNickname(nickname)) {
            return ResultUtil.error(10005);
        }
        String md5Pwd = MD5Util.md5(password);
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setMobilePhone(username);
        userEntity.setPassword(md5Pwd);
        userEntity.setAvatar(avatar);
        userEntity.setNickname(nickname);
        userService.addUser(userEntity);
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 查询所有的用户
     *
     * @return
     */
    @GetMapping(path = "findAll")
    public Result findAll() {
        List<UserEntity> entityList = userService.findAll();
        return ResultUtil.success(entityList);
    }

    /**
     *
     * 重置密码
     *
     * @param password
     * @return
     */
    @PostMapping(path = "/resetPwd")
    public Result resetPwd(@RequestParam(value = "password", defaultValue = "") String password,
                           @RequestParam(value = "username", defaultValue = "") String username) {
        if (!(password.length() >= 6 && password.length() <=12)) {
            return ResultUtil.error(10003);
        }
        String md5Pwd = MD5Util.md5(password);
        UserEntity userEntity = userService.findByMobile(username);
        userEntity.setPassword(md5Pwd);
        userService.update(userEntity);
        return ResultUtil.succeedNoData();
    }

}
