package com.example.admin_java.service;

import com.example.admin_java.entity.UserEntity;

import java.util.List;

/**
 *
 * User业务接口
 *
 * @Author: Think
 * @Date: 2018/5/27
 * @Time: 14:37
 */
public interface UserService {

    List<UserEntity> findAll();

    UserEntity addUser(UserEntity userEntity);

    UserEntity update(UserEntity userEntity);

    void delete(String account);

    UserEntity findByAccount(String account);

    UserEntity findByAccountAndPassword(String account, String password);

    UserEntity findByUid(int uid);

}
