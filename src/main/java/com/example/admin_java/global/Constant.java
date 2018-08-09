package com.example.admin_java.global;

/**
 * @Author: Think
 * @Date: 2018/6/2
 * @Time: 12:14
 */
public class Constant {

    public static final long REDIS_TIME_OUT = 3600;
    /**
     * 验证码超时时间
     */
    public static final long VERIFY_CODE_TIME_OUT = 300;

    /**
     * redisKey
     */
    public static final String IMAGE_CODE = "_imageCode";

    /**
     * 天气信息的key
     */
    public static final String WEATHER_KEY = "bc0418b57b2d4918819d3974ac1285d9";

    /**
     * redisKey
     */
    public static final String VERIFY_CODE = "_verifyCode";

    /**
     * meizi url
     */
    public static final String BELLE_URL = "http://gank.io/api/data/福利";

    /**
     * ip所属城市的url
     */
    public static final String IP_URL = "http://ip.taobao.com/service/getIpInfo.php";

    /**
     * 获取省份的url
     */
    public static final String PROVINCES_URL = "http://guolin.tech/api/china";

    /**
     * 获取天气信息的url
     */
    public static final String WEATHER_URL = "http://guolin.tech/api/weather";

}
