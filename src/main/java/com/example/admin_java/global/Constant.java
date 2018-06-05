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
     * redisKey
     */
    public static final String VERIFY_CODE = "_verifyCode";

}
