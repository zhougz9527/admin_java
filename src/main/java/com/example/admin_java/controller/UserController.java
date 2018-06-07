package com.example.admin_java.controller;

import com.aliyuncs.exceptions.ClientException;
import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.entity.VerifyCodeEntity;
import com.example.admin_java.global.Constant;
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
        Map<String,String> map = new HashMap<>();
        map.put("myKey", "myValue");
        return ResultUtil.success(map);
    }

    /**
     *
     * 登录
     *
     * @param account
     * @param password
     * @return
     */
    @PostMapping(path = "/login")
    public Result login(@RequestParam(value = "account", defaultValue = "") String account,
                        @RequestParam(value = "password", defaultValue = "") String password) {
        log.info("account: {}, password: {}", account, password);
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(password)) {
            ResultUtil.error(10009);
        }
        String pwdMD5 = MD5Util.md5(password);
        UserEntity userEntity = userService.findByAccountAndPassword(account, pwdMD5);
        if (null == userEntity) {
            return ResultUtil.error(10012);
        }
        userEntity.setLastLogin(DateUtil.timestampToDate(System.currentTimeMillis()));
        userService.update(userEntity);
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 注册
     *
     * @param account
     * @param password
     * @param confirmPwd
     * @param verifyCode
     * @return
     */
    @PostMapping(path = "/register")
    public Result register(@RequestParam(value = "account", defaultValue = "") String account,
                           @RequestParam(value = "password", defaultValue = "") String password,
                           @RequestParam(value = "confirmPwd", defaultValue = "") String confirmPwd,
                           @RequestParam(value = "verifyCode", defaultValue = "") String verifyCode) {
        log.info("account: {}, password: {}", account, password);
        if (!(confirmPwd.equals(verifyCode) && RegexUtil.isPassword(password))) {
            ResultUtil.error(10003);
        }
        if (!(RegexUtil.isMobile(account) || RegexUtil.isEmail(account))) {
            ResultUtil.error(10004);
        }
        Object obj = redisService.get(account + Constant.VERIFY_CODE);
        String redisVerifyCode = null == obj ? "" : obj.toString();
        if (!verifyCode.equals(redisVerifyCode)) {
            ResultUtil.error(10008);
        }
        String md5Pwd = MD5Util.md5(password);
        UserEntity user = userService.findByAccount(account);
        if (null != user) {
            return ResultUtil.error(10006);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount(account);
        userEntity.setPassword(md5Pwd);
        userEntity.setAvatar("");
        userEntity.setNickname("");
        userService.addUser(userEntity);
        verifyCodeService.updateStatusByAccountAndCode(account, verifyCode);
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 发送验证码
     *
     * @param account
     * @param imageCode
     * @return
     * @throws ClientException
     */
    @PostMapping(path = "/sendVerifyCode")
    public Result sendVerifyCode(@RequestParam(value = "account", defaultValue = "") String account,
                                 @RequestParam(value = "imageCode", defaultValue = "") String imageCode) throws ClientException {
        if (!RegexUtil.isEmail(account) && !RegexUtil.isMobile(account)) {
            return ResultUtil.error(10004);
        }
        if (StringUtils.isEmpty(imageCode)) {
            return ResultUtil.error(10009);
        }
        Object obj = null;
        obj = redisService.get(account + Constant.IMAGE_CODE);
        String redisImageCode = null == obj ? "" : obj.toString();
        if (!((imageCode.toUpperCase()).equals(redisImageCode))) {
            return ResultUtil.error(10010);
        }
        // 删除redis中的图片验证码
        redisService.delete(account + Constant.IMAGE_CODE);
        obj = redisService.get(account + Constant.VERIFY_CODE);
        String redisVerifyCode = null == obj ? "" : obj.toString();
        long second = System.currentTimeMillis() + 300000;
        String date = DateUtil.timestampToDate(second);
        // redis中的验证码过期或者是验证码未发送
        if (StringUtils.isEmpty(redisVerifyCode)) {
            String currentTime = DateUtil.timestampToDate(System.currentTimeMillis());
            VerifyCodeEntity entity = verifyCodeService.findByAccountAndDate(account, currentTime);
            String verifyCode = "";
            if (null != entity) {// mysql中有未过期的验证码
                verifyCode = entity.getCode();
            } else {// mysql中的验证码已过期或者没有验证码
                verifyCode = createRandomNum(6);
            }
            if (RegexUtil.isMobile(account)) {
                // 发送短信
                boolean isSucceed = sendMobileCode(account, verifyCode);
                if (false == isSucceed) {
                    return ResultUtil.error(10007);
                }
            } else {
                mailService.sendSimpleMail(account, account + ", 你的安全代码", "你的安全代码是: " + verifyCode + ", 请打死也不要告诉他人, 安全代码5分钟内有效");
            }
            VerifyCodeEntity verifyCodeEntity = new VerifyCodeEntity();
            verifyCodeEntity.setAccount(account);
            verifyCodeEntity.setCode(verifyCode);
            verifyCodeEntity.setExpireTime(date);
            verifyCodeService.addVerifyCode(verifyCodeEntity);
            redisService.set(account + Constant.VERIFY_CODE, verifyCode, Constant.VERIFY_CODE_TIME_OUT);
        } else {// 验证码未过期
            if (RegexUtil.isMobile(account)) {
                // 发送短信
                boolean isSucceed = sendMobileCode(account, redisVerifyCode);
                if (false == isSucceed) {
                    return ResultUtil.error(10007);
                }
            } else {
                mailService.sendSimpleMail(account, account + ", 你的安全代码", "你的安全代码是: " + redisVerifyCode + ", 请打死也不要告诉他人, 安全代码5分钟内有效");
            }
            verifyCodeService.updateDateByAccountAndVerifyCode(date, account, redisVerifyCode);
        }
        return ResultUtil.succeedNoData();
    }

    /**
     *
     * 获取base64验证码图片
     *
     * @param account
     * @return
     */
    @GetMapping(path = "/getImageCode")
    public Result getImageCode(@RequestParam(value = "account", defaultValue = "") String account) {
        if (!RegexUtil.isMobile(account) && !RegexUtil.isEmail(account)) {
            return ResultUtil.error(10004);
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
            log.error("生成图形验证码出错, message: {}" + e.getMessage());
        }
        String base64Str = Base64Util.imageToBase64Str(file.getPath());
        if (!StringUtils.isEmpty(base64Str)) {
            ResultUtil.error(10011);
        }
        Map<String, String> base64Map = new HashMap<>();
        base64Map.put("base64Url", "data:image/png;base64," + base64Str);
        redisService.set(account + Constant.IMAGE_CODE, imageCode, Constant.VERIFY_CODE_TIME_OUT);
        return ResultUtil.success(base64Map);
    }

    /**
     *
     * 重置密码
     *
     * @param account
     * @param newPassword
     * @param verifyCode
     * @return
     */
    @PostMapping(path = "/resetPwd")
    public Result resetPwd(@RequestParam(value = "account", defaultValue = "") String account,
                           @RequestParam(value = "newPassword",defaultValue = "") String newPassword,
                           @RequestParam(value = "verifyCode", defaultValue = "") String verifyCode) {
        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(newPassword) || StringUtils.isEmpty(verifyCode)) {
            return ResultUtil.error(10009);
        }
        if (!RegexUtil.isPassword(newPassword)) {
            return ResultUtil.error(10003);
        }
        Object obj = redisService.get(account + Constant.VERIFY_CODE);
        String redisVerifyCode = null == obj ? "" : obj.toString();
        if (!verifyCode.equals(redisVerifyCode)) {
            ResultUtil.error(10008);
        }
        UserEntity userEntity = userService.findByAccount(account);
        userEntity.setLastLogin(DateUtil.timestampToDate(System.currentTimeMillis()));
        userEntity.setPassword(MD5Util.md5(newPassword));
        userService.update(userEntity);
        verifyCodeService.updateStatusByAccountAndCode(account, verifyCode);
        return ResultUtil.succeedNoData();
    }

}
