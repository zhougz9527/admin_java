package com.example.admin_java.serviceImpl;

import com.example.admin_java.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
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
    RedisTemplate redisTemplate;

    @Override
    public boolean set(String key, Object value) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            flag = true;
            log.info("写入redis成功: key: {}, value: {}", key, value);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入redis失败: key: {}, value: {}, message: {}", key, value, e.getMessage());
        }
        return flag;
    }

    @Override
    public boolean set(String key, Object value, long time) {
        boolean flag = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, time, TimeUnit.SECONDS);
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("写入redis失败: key: {}, value: {}, message: {}", key, value, e.getMessage());
        }
        return flag;
    }

    @Override
    public Object get(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return operations.get(key);
    }

    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public void delete(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    @Override
    public long incLong(String key, long step) {
        return 0;
    }

    @Override
    public String getHash(String hKey, String key) {
        return null;
    }

    @Override
    public void setHash(String hKey, String key, String value) {

    }

    @Override
    public Set<String> getHashByHKey(String hKey) {
        return null;
    }

    @Override
    public long deleteHash(String hKey, String key) {
        return 0;
    }

    @Override
    public String getListPopLeft(String key) {
        return null;
    }

    @Override
    public String getListPopRight(String key) {
        return null;
    }

    @Override
    public void setLeftPush(String key, String value) {

    }

    @Override
    public void setRightPush(String key, String value) {

    }

    @Override
    public long getListSize(String key) {
        return 0;
    }

    @Override
    public long removeList(String key, long count, String value) {
        return 0;
    }

    @Override
    public long setSet(String key, String value) {
        return 0;
    }

    @Override
    public long deleteSet(String key, String value) {
        return 0;
    }

    @Override
    public String getSet(String key) {
        return null;
    }

    @Override
    public long getSetSize(String key) {
        return 0;
    }
}
