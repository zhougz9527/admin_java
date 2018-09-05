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
@Service
public class VerifyCodeServiceImpl extends BaseServiceImpl implements VerifyCodeService {

    @Autowired
    VerifyCodeRepository verifyCodeRepository;

    @Override
    public VerifyCodeEntity addVerifyCode(VerifyCodeEntity verifyCodeEntity) {
        logger.info("添加验证码: verifyCodeEntity: " + verifyCodeEntity.toString());
        return verifyCodeRepository.save(verifyCodeEntity);
    }

    @Override
    public VerifyCodeEntity findVerifyCodeByAccount(String account) {
        logger.info("获取验证码: account: " + account);
        return verifyCodeRepository.findByAccount(account);
    }

    @Override
    public void updateDateByAccountAndVerifyCode(String date, String account, String verifyCode) {
        logger.info("修改验证码过期时间: date: "+date+", account: "+account+", verifyCode: " + verifyCode);
        verifyCodeRepository.updateDateByAccountAndCode(date, account, verifyCode);
    }

    @Override
    public void updateStatusByAccountAndCode(String account, String verifyCode) {
        logger.info("修改验证码状态为已使用: account: "+account+", verifyCode: " + verifyCode);
        verifyCodeRepository.updateStatusByAccountAndCode(account, verifyCode);

    }

    @Override
    public VerifyCodeEntity findByAccountAndDate(String account, String date) {
        logger.info("查询未过期的验证码: account: "+account+", date: " + date);
        return verifyCodeRepository.findByAccountAndDate(account, date);
    }
}
