package com.example.admin_java.service;


/**
 * @Author: Think
 * @Date: 2018/6/2
 * @Time: 10:36
 */
public interface RedisService {

    boolean set(String key, Object value);

    boolean set(String key, Object value, long time);

    Object get(String key);

    boolean exists(String key);

    boolean delete(String key);

}
