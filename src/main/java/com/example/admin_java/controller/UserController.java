package com.example.admin_java.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.entity.VerifyCodeEntity;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.RedisService;
import com.example.admin_java.service.UserService;
import com.example.admin_java.service.VerifyCodeService;
import com.example.admin_java.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static java.lang.System.currentTimeMillis;

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
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    VerifyCodeService verifyCodeService;

    /**
     *
     * 测试接口
     *
     * @return
     */
    @RequestMapping(path = "/test")
    public Result test() {
        File dir = new File("F:/verifies");
        int w = 200, h = 80;
        for(int i = 0; i < 50; i++){
            String verifyCode = ImageVerifyCodeUtil.generateVerifyCode(4);
            File file = new File(dir, verifyCode + ".jpg");
            try {
                ImageVerifyCodeUtil.outputImage(w, h, file, verifyCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 注册
     *
     * @return
     */
    @PostMapping(path = "/register")
    public Result register(@RequestParam(value = "username", defaultValue = "") String username,
                           @RequestParam(value = "password", defaultValue = "") String password,
                           @RequestParam(value = "verifyCode", defaultValue = "") String verifyCode) {
        if (RegexUtil.isMobile(username)) {

        } else if (RegexUtil.isEmail(username)) {

        } else {
            ResultUtil.error(10004);
        }

        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 发送验证码
     *
     * @return
     */
    @PostMapping(path = "/sendVerifyCode")
    public Result sendVerifyCode(@RequestParam(value = "username", defaultValue = "") String username) throws ClientException {
        String verifyCode = "";
        if (RegexUtil.isMobile(username)) {
             verifyCode = sendMobileCode(username);
            if (!StringUtils.isEmpty(verifyCode)) {
                redisService.set(username, verifyCode,300);
                long second = System.currentTimeMillis() + 300000;
                log.info("second: {}", second);
                String date = DateUtil.timestampToDate(second);
                VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity();
                verifyCodeEntity.setAccount(username);
                verifyCodeEntity.setCode(verifyCode);
                log.info("date: {}", date);
                verifyCodeEntity.setExpireTime(date);
                verifyCodeService.addVerifyCode(verifyCodeEntity);
            } else {
                return ResultUtil.error(10007);
            }
        } else if (RegexUtil.isEmail(username)) {

        } else {
            ResultUtil.error(10004);
        }

        return ResultUtil.succeedNoData();
    }

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
        log.info("username:{}, password:{}", username, password);
        String md5Pwd = MD5Util.md5(password);
        UserEntity user = userService.findByUsername(username);
        if (null != user) {
            return ResultUtil.error(10006);
        }
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
        UserEntity userEntity = userService.findByUsername(username);
        userEntity.setPassword(md5Pwd);
        userService.update(userEntity);
        return ResultUtil.succeedNoData();
    }

}
