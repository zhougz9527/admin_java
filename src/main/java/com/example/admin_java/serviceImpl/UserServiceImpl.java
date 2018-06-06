package com.example.admin_java.serviceImpl;

import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.repository.UserRepository;
import com.example.admin_java.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/5/27
 * @Time: 15:35
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        log.info("新增用户: {}", userEntity.toString());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        log.info("更新用户: {}", userEntity.toString());
        return userRepository.save(userEntity);
    }

    @Override
    public void delete(String account) {
        UserEntity userEntity = userRepository.findByAccount(account);
        userRepository.delete(userEntity);
        log.info("删除的用户信息: account: {}", account);
    }

    @Override
    public UserEntity findByAccount(String account) {
        UserEntity userEntity = userRepository.findByAccount(account);
        log.info("获取用户账号: account: {}", account);
        return userEntity;
    }

    @Override
    public UserEntity findByAccountAndPassword(String account, String password) {
        UserEntity userEntity = userRepository.findByAccountAndPassword(account, password);
        log.info("获取获取用户账号: account: {}, password: {}", account, password);
        return userEntity;
    }

    @Override
    public UserEntity findByUid(int uid) {
        UserEntity userEntity = userRepository.findByUid(uid);
        log.info("获取获取用户账号: uid: {}", uid);
        return userEntity;
    }

}
