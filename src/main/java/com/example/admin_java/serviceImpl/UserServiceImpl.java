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
    public void delete(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        userRepository.delete(userEntity);
        log.info("删除的用户信息: {}", userEntity.toString());
    }

    @Override
    public UserEntity findByUsername(String username) {
        log.info("获取用户账号: {}", username);
        UserEntity userEntity = userRepository.findByUsername(username);
        return userEntity;
    }

    @Override
    public UserEntity findByUid(int uid) {
        log.info("获取uid: {}", uid);
        UserEntity userEntity = userRepository.findByUid(uid);
        return userEntity;
    }

}
