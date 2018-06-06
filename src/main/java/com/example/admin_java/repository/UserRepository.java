package com.example.admin_java.repository;

import com.example.admin_java.entity.UserEntity;
import org.apache.catalina.User;

/**
 *
 * 用户信息操作接口
 *
 * @Author: Think
 * @Date: 2018/5/24
 * @Time: 22:54
 */
public interface UserRepository extends BaseRepository<UserEntity, Integer> {

    UserEntity findByUid(int uid);

    UserEntity findByAccount(String account);

    UserEntity findByAccountAndPassword(String account, String password);
}
