package com.example.admin_java.repository;

import com.example.admin_java.entity.VerifyCodeEntity;

/**
 * @Author: Think
 * @Date: 2018/6/3
 * @Time: 22:52
 */
public interface VerifyCodeRepository extends BaseRepository<VerifyCodeEntity, Integer> {
    VerifyCodeEntity findByAccount(String account);
}
