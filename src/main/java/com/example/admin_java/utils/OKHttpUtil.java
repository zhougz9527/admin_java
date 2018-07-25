package com.example.admin_java.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        String responseBody = "";
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            response = okHttpClient.newCall(request).execute();
            int status = response.code();
            if (response.isSuccessful()) {
                return response.body().string();
            }
        } catch (Exception e) {
            log.error("okhttp get error, message: {}", e.getMessage());
        } finally {
            if (response != null) {
                response.close();
            }
        }
        return responseBody;
    }

}
