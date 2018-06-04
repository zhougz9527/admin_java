package com.example.admin_java.utils;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: Think
 * @Date: 2018/6/4
 * @Time: 23:15
 */
@Slf4j
public class Base64Util {

    /**
     *
     * 图片转换成base64字符串
     *
     * @return
     */
    public static String imageToBase64Str(String imageFile) {
        InputStream in = null;
        byte[] data = null;
        //读取图片字节数组
        try {
            in = new FileInputStream(imageFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            log.error("图片验证码转成base64出错, message: {}", e.toString());
        }
        //对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64 = encoder.encode(data);
        String newBase64Str = base64.replaceAll("\r\n", "");
        return newBase64Str;//返回Base64编码过的字节数组字符串
    }
}
