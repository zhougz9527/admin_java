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
        log.info("添加验证码: verifyCodeEntity: {}", verifyCodeEntity.toString());
        return verifyCodeRepository.save(verifyCodeEntity);
    }

    @Override
    public VerifyCodeEntity findVerifyCodeByAccount(String account) {
        log.info("获取验证码: account: {}", account);
        return verifyCodeRepository.findByAccount(account);
    }

    @Override
    public void updateDateByAccountAndVerifyCode(String date, String account, String verifyCode) {
        log.info("修改验证码过期时间: date: {}, account: {}, verifyCode: {}", date, account, verifyCode);
        verifyCodeRepository.updateDateByAccountAndCode(date, account, verifyCode);
    }

    @Override
    public void updateStatusByAccountAndCode(String account, String verifyCode) {
        log.info("修改验证码状态为已使用: account: {}, verifyCode: {}", account, verifyCode);
        verifyCodeRepository.updateStatusByAccountAndCode(account, verifyCode);

    }

    @Override
    public VerifyCodeEntity findByAccountAndDate(String account, String date) {
        log.info("查询未过期的验证码: account: {}, date: {}", account, date);
        return verifyCodeRepository.findByAccountAndDate(account, date);
    }
}
