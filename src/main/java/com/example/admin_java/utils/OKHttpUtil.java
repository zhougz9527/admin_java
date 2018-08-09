package com.example.admin_java.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Think
 * @Date: 2018/7/25
 * @Time: 11:37
 */
@Slf4j
public class OKHttpUtil {

    /**
     * GET 请求
     * @param url
     * @return
     */
    public static String get (String url) {
        OkHttpClient okHttpClient = new OkHttpClient()
                .newBuilder()
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .sslSocketFactory(SSLSocketClient.getSSLSocketFactory())//配置
                .hostnameVerifier(SSLSocketClient.getHostnameVerifier())//配置
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();
        String responseBody = "";
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
            log.info("url: {}, okhttp execute exception:{}", url, e.getMessage());
        }
        if (null != response) {
            try {
                responseBody = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
                log.info("url: {}, okhttp response exception:{}", url, e.getMessage());
            }
        }
        return responseBody;
    }

}
