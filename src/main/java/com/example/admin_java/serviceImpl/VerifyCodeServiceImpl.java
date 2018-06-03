package com.example.admin_java.serviceImpl;

import com.example.admin_java.entity.VerifyCodeEntity;
import com.example.admin_java.repository.VerifyCodeRepository;
import com.example.admin_java.service.VerifyCodeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: Think
 * @Date: 2018/6/3
 * @Time: 22:53
 */
@Slf4j
@Service
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    VerifyCodeRepository verifyCodeRepository;

    @Override
    public VerifyCodeEntity addVerifyCode(VerifyCodeEntity verifyCodeEntity) {
        return verifyCodeRepository.save(verifyCodeEntity);
    }

    @Override
    public VerifyCodeEntity findVerifyCodeByAccount(String account) {
        return verifyCodeRepository.findByAccount(account);
    }
}
