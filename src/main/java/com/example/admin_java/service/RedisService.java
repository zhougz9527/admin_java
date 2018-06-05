package com.example.admin_java.service;

import java.util.Set;

/**
 * @Author: Think
 * @Date: 2018/6/2
 * @Time: 10:36
 */
public interface RedisService {

    /**
     * 设置key value
     *
     * @param key
     * @param value
     */
    public boolean set(String key, Object value);

    /**
     * 设置key value 以及过期时间
     *
     * @param key
     * @param value
     * @param time
     */
    public boolean set(String key, Object value, long time);

    /**
     * 获取value
     *
     * @param key
     * @return
     */
    public Object get(String key);

    /**
     *
     * 是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(String key);

    /**
     *
     * 删除指定key的value
     *
     * @param key
     */
    public void delete(String key);

    /**
     * 增长器
     *
     * @param key
     * @param step
     * @return
     */
    public long incLong(String key, long step);

    /**
     * hash 获取
     *
     * @param hKey
     * @param key
     * @return
     */
    public String getHash(String hKey, String key);

    /**
     * hash 设置
     *
     * @param hKey
     * @param key
     * @param value
     */
    public void setHash(String hKey, String key, String value);

    /**
     * 获取所有的hash
     *
     * @param hKey
     * @return
     */
    public Set<String> getHashByHKey(String hKey);

    /**
     * 删除某个hash
     *
     * @param hKey
     * @param key
     * @return
     */
    public long deleteHash(String hKey, String key);

    /**
     * 左出栈
     *
     * @param key
     * @return
     */
    public String getListPopLeft(String key);

    /**
     * 右出栈
     *
     * @param key
     * @return
     */
    public String getListPopRight(String key);

    /**
     * 左入栈
     *
     * @param key
     * @param value
     */
    public void setLeftPush(String key, String value);

    /**
     * 右入栈
     *
     * @param key
     * @param value
     */
    public void setRightPush(String key, String value);

    /**
     * 获取list长度
     *
     * @param key
     * @return
     */
    public long getListSize(String key);

    /**
     * 删除栈的值
     * count> 0：删除等于从左到右移动的值的第一个元素
     * count< 0：删除等于从右到左移动的值的第一个元素
     * count = 0：删除等于value的所有元素
     *
     * @param key
     * @param count
     * @param value
     * @return
     */
    public long removeList(String key, long count, String value);

    /**
     * 存储set
     *
     * @param key
     * @param value
     * @return
     */
    public long setSet(String key, String value);

    /**
     * 删除set
     *
     * @param key
     * @param value
     * @return
     */
    public long deleteSet(String key, String value);

    /**
     * 获取set
     *
     * @param key
     * @return
     */
    public String getSet(String key);

    /**
     * 获取set长度
     *
     * @param key
     * @return
     */
    public long getSetSize(String key);
}
