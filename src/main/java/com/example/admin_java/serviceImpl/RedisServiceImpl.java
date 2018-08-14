package com.example.admin_java.serviceImpl;

import com.alibaba.fastjson.support.spring.GenericFastJsonRedisSerializer;
import com.example.admin_java.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Think
 * @Date: 2018/6/2
 * @Time: 11:55
 */
@Slf4j
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private boolean flag = false;


    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        GenericFastJsonRedisSerializer fastJsonRedisSerializer = new GenericFastJsonRedisSerializer();
        redisTemplate.setKeySerializer(fastJsonRedisSerializer);//单独设置keySerializer
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);//单独设置valueSerializer
        this.redisTemplate = redisTemplate;
    }


    @Override
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("存入redis失败: {}", e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        try {
            redisTemplate.opsForValue().set(key, value);
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("存入redis失败: {}", e.getMessage());
        }
        return flag;
    }

    @Override
    public Object get(String key) {
        Object object = redisTemplate.opsForValue().get(key);
        return object;
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public boolean delete(String key) {
        if (exists(key)) {
            return redisTemplate.delete(key);
        }
        return false;
    }

}
