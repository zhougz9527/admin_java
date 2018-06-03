package com.example.admin_java.serviceImpl;

import com.example.admin_java.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author: Think
 * @Date: 2018/6/2
 * @Time: 11:55
 */
@Service
public class RedisServiceImpl implements RedisService {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Resource(name = "stringRedisTemplate")
    ValueOperations<String, String> redisValue;

    @Resource(name = "stringRedisTemplate")
    SetOperations<String, String> redisSet;

    @Resource(name = "stringRedisTemplate")
    HashOperations<String, String, String> redisHash;

    @Resource(name = "stringRedisTemplate")
    ListOperations<String, String> redisList;


    /**
     *
     * 设置key，value
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {
        redisValue.set(key, value);
    }

    /**
     *
     * 设置key，value，过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    @Override
    public void set(String key, String value, long time) {
        redisValue.set(key, value, time);
    }

    /**
     *
     * 获取value
     *
     * @param key
     * @return
     */
    @Override
    public String get(String key) {
        return redisValue.get(key);
    }

    /**
     *
     * 增长器
     *
     * @param key
     * @param step
     * @return
     */
    @Override
    public long incLong(String key, long step) {
        return redisValue.increment(key ,step);
    }

    /**
     *
     * 获取hash
     *
     * @param hKey
     * @param key
     * @return
     */
    @Override
    public String getHash(String hKey, String key) {
        return redisHash.get(hKey, key);
    }

    /**
     *
     * 设置hash
     *
     * @param hKey
     * @param key
     * @param value
     */
    @Override
    public void setHash(String hKey, String key, String value) {
        redisHash.put(hKey, key, value);
    }

    /**
     *
     * 获取全部的hash
     *
     * @param hKey
     * @return
     */
    @Override
    public Set<String> getHashByHKey(String hKey) {
        return redisHash.keys(hKey);
    }

    /***
     *
     * 删除hash
     *
     * @param hKey
     * @param key
     * @return
     */
    @Override
    public long deleteHash(String hKey, String key) {
        return redisHash.delete(hKey, key);
    }

    /**
     *
     * 左出栈
     *
     * @param key
     * @return
     */
    @Override
    public String getListPopLeft(String key) {
        return redisList.leftPop(key);
    }

    /**
     *
     * 右出栈
     *
     * @param key
     * @return
     */
    @Override
    public String getListPopRight(String key) {
        return redisList.rightPop(key);
    }

    /**
     *
     * 左入栈
     *
     * @param key
     * @param value
     */
    @Override
    public void setLeftPush(String key, String value) {
        redisList.leftPush(key, value);
    }

    /**
     *
     * 右入栈
     *
     * @param key
     * @param value
     */
    @Override
    public void setRightPush(String key, String value) {
        redisList.rightPush(key, value);
    }

    /**
     *
     * 获取List的长度
     *
     * @param key
     * @return
     */
    @Override
    public long getListSize(String key) {
        return redisList.size(key);
    }

    /**
     *
     * 删除栈中的值
     * count> 0：删除等于从左到右移动的值的第一个元素
     * count< 0：删除等于从右到左移动的值的第一个元素
     * count = 0：删除等于value的所有元素
     * @param key
     * @param count
     * @param value
     * @return
     */
    @Override
    public long removeList(String key, long count, String value) {
        return redisList.remove(key, count, value);
    }

    /**
     *
     * 储存set
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long setSet(String key, String value) {
        return redisSet.add(key, value);
    }

    /**
     *
     * 删除set
     *
     * @param key
     * @param value
     * @return
     */
    @Override
    public long deleteSet(String key, String value) {
        return redisSet.remove(key, value);
    }

    /**
     *
     * 获取set
     *
     * @param key
     * @return
     */
    @Override
    public String getSet(String key) {
        return redisSet.pop(key);
    }

    /**
     *
     * 获取set长度
     *
     * @param key
     * @return
     */
    @Override
    public long getSetSize(String key) {
        return redisSet.size(key);
    }
}
