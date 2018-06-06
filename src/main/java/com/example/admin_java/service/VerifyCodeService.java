package com.example.admin_java.service;

import com.example.admin_java.entity.VerifyCodeEntity;

/**
 * @Author: Think
 * @Date: 2018/6/3
 * @Time: 22:55
 */
public interface VerifyCodeService {

    VerifyCodeEntity addVerifyCode(VerifyCodeEntity verifyCodeEntity);

    VerifyCodeEntity findVerifyCodeByAccount(String account);

    void updateDateByAccountAndVerifyCode(String date, String account, String verifyCode);

    void updateStatusByAccountAndCode(String account, String verifyCode);

    VerifyCodeEntity findByAccountAndDate(String account, String date);
}
