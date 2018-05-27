package com.example.admin_java.serviceImpl;

import com.example.admin_java.entity.UserEntity;
import com.example.admin_java.repository.UserRepository;
import com.example.admin_java.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Think
 * @Date: 2018/5/27
 * @Time: 15:35
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    UserRepository userRepository;


    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        LOGGER.info("新增用户: " + userEntity.toString());
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity update(UserEntity userEntity) {
        LOGGER.info("更新用户: " + userEntity.toString());
        return userRepository.save(userEntity);
    }

    @Override
    public void delete(String mobile_num) {
        UserEntity userEntity = userRepository.findByMobilePhone(mobile_num);
        userRepository.delete(userEntity);
        LOGGER.info("删除的用户信息: " + userEntity.toString());
    }

    @Override
    public UserEntity findByMobile(String mobile) {
        LOGGER.info("获取用户手机号: " + mobile);
        UserEntity userEntity = userRepository.findByMobilePhone(mobile);
        return userEntity;
    }

    @Override
    public UserEntity findByUid(int uid) {
        LOGGER.info("获取uid: " + uid);
        UserEntity userEntity = userRepository.findByUid(uid);
        return userEntity;
    }

}
