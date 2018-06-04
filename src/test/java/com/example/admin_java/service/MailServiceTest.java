package com.example.admin_java.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Think
 * @Date: 2018/6/4
 * @Time: 22:04
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServiceTest {

    @Autowired
    private MailService mailService;

    @Test
    public void testMailService() throws Exception {
        mailService.sendSimpleMail("blackbox_9527@163.com", "这是一封简单的邮件", "---------- hello world ---------");
    }

}
