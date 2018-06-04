package com.example.admin_java.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.entity.VerifyCodeEntity;
import com.example.admin_java.result.Result;
import com.example.admin_java.result.ResultUtil;
import com.example.admin_java.service.MailService;
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
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    VerifyCodeService verifyCodeService;

    @Autowired
    MailService mailService;

    /**
     *
     * 测试接口
     *
     * @return
     */
    @RequestMapping(path = "/test")
    public Result test() {

//        mailService.sendSimpleMail("blackbox_9527@163.com", "blackbox_9527@163.com,你的安全代码", "你的安全代码是: " + 123456 + ", 请打死也不要告诉他人, 安全代码5分钟内有效");
//
//        return ResultUtil.success(System.currentTimeMillis());
//        File dir = new File("F:/verifies");
//        int w = 200, h = 80;
//        for(int i = 0; i < 50; i++){
//            String verifyCode = ImageVerifyCodeUtil.generateVerifyCode(4);
//            File file = new File(dir, verifyCode + ".jpg");
//            try {
//                ImageVerifyCodeUtil.outputImage(w, h, file, verifyCode);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return ResultUtil.succeedNoData();
    }

    @PostMapping(path = "/login")
    public Result login(@RequestParam(value = "username", defaultValue = "") String username,
                        @RequestParam(value = "password", defaultValue = "") String password) {
        log.info("username: {}, password: {}", username, password);
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            ResultUtil.error(10009);
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
                           @RequestParam(value = "confirmPwd", defaultValue = "") String confirmPwd,
                           @RequestParam(value = "verifyCode", defaultValue = "") String verifyCode) {
        log.info("username: {}, password: {}", username, password);
        if (!(confirmPwd.equals(verifyCode) && RegexUtil.isPassword(password))) {
            ResultUtil.error(10003);
        }
        if (!(RegexUtil.isMobile(username) || RegexUtil.isEmail(username))) {
            ResultUtil.error(10004);
        }
        String redisValue = redisService.get(username);
        if (!verifyCode.equals(redisValue)) {
            ResultUtil.error(10008);
        }
        String md5Pwd = MD5Util.md5(password);
        UserEntity user = userService.findByUsername(username);
        if (null != user) {
            return ResultUtil.error(10006);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        userEntity.setPassword(md5Pwd);
        userService.addUser(userEntity);
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 发送验证码
     *
     * @return
     */
    @PostMapping(path = "/sendVerifyCode")
    public Result sendVerifyCode(@RequestParam(value = "username", defaultValue = "") String username,
                                 @RequestParam(value = "imageCode", defaultValue = "") String imageCode) throws ClientException {
        String verifyCode = createRandomNum(6);
        String redisImageCode = redisService.get(username);
        if (!imageCode.equals(redisImageCode)) {
            return ResultUtil.error(10010);
        }
        //TODO 删除redis中的key value
        if (RegexUtil.isMobile(username)) {
            boolean isSucceed = sendMobileCode(username, verifyCode);
            if (true == isSucceed) {
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
            mailService.sendSimpleMail(username, username + ", 你的安全代码", "你的安全代码是: " + verifyCode + ", 请打死也不要告诉他人, 安全代码5分钟内有效");
        } else {
            ResultUtil.error(10004);
        }
        redisService.set(username, verifyCode, 300);
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 获取base64形式的图片验证码
     *
     * @return
     */
    @GetMapping(path = "/getImageCode")
    public Result getImageCode(@RequestParam(value = "username", defaultValue = "") String username) {
        if (StringUtils.isEmpty(username)) {
            ResultUtil.error(10001);
        }
        String imageCode = ImageVerifyCodeUtil.generateVerifyCode(4);
        String rootFile = System.getProperty("user.dir") + File.separator + "image";
        File dir = new File(rootFile);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, System.currentTimeMillis() + "_" + imageCode + ".jpg");
        int w = 200, h = 80;
        try {
            ImageVerifyCodeUtil.outputImage(w, h, file, imageCode);
            log.info("imageCodeFile: {}", file.getPath());
        } catch (IOException e) {
            e.printStackTrace();
            log.error("生成图形验证码出错, message: {}" + e.toString());
        }
        String base64Str = Base64Util.imageToBase64Str(file.getPath());
        if (!StringUtils.isEmpty(base64Str)) {
            ResultUtil.error(10011);
        }
        Map<String, String> base64Map = new HashMap<>();
        base64Map.put("base64Url", "data:image/png;base64," + base64Str);
        redisService.set(username, imageCode);
        return ResultUtil.success(base64Map);
    }


    /**
     *
     * 查询所有的用户
     *
     * @return
     */
    @GetMapping(path = "/findAll")
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
