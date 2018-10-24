package com.example.admin_java.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.example.admin_java.utils.AliyunMessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: Think
 * @Date: 2018/5/24
 * @Time: 22:41
 */
public class BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    /**
     *
     * 生成随机数
     *
     * @param num
     * @return
     */
    public static String createRandomNum(int num) {
        String randomNumStr = "";
        for (int i = 0; i < num; i++) {
            int randomNum = (int)(Math.random() * 10);
            randomNumStr += randomNum;
        }
        return randomNumStr;
    }

    /**
     *
     * 发送手机短信验证码
     *
     * @return
     */
    public static boolean sendMobileCode(String mobile, String randomNum) throws ClientException {
        String jsonContent = "{\"code\":\"" + randomNum + "\"} ";

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("phoneNumber", mobile);
        paramMap.put("msgSign", "金木");
        paramMap.put("templateCode", "SMS_136391309");
        paramMap.put("jsonContent", jsonContent);
        SendSmsResponse sendSmsResponse = AliyunMessageUtil.sendSms(paramMap);
//        log.info("sendSmsResponse: {}", sendSmsResponse.toString());
        if (!(sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK"))) {
//            log.info("sendSmsResponse.getCode(): {}", sendSmsResponse.getCode().toString());
            return false;
        }
        return true;
    }

}
