package com.example.admin_java.serviceImpl;

import com.example.admin_java.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: Think
 * @Date: 2018/6/4
 * @Time: 21:58
 */
@Service
public class MailServiceImpl extends BaseServiceImpl implements MailService {


    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     *
     * 发送送文本邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendSimpleMail(String to, String subject, String content) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);//邮件发送者
        message.setTo(to);//邮件接受者
        message.setSubject(subject);//邮件主题
        message.setText(content);//邮件主体
        try {
            javaMailSender.send(message);
            logger.info("简单邮件已经发送");
        } catch (MailException e) {
            logger.error("发送简单邮件发生异常！", e);
        }
    }

    /**
     *
     * 发送html邮件
     *
     * @param to
     * @param subject
     * @param content
     */
    @Override
    public void sendHtmlMail(String to, String subject, String content) {

        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            //true 表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);

            javaMailSender.send(message);
            logger.info("html邮件发送成功");
        } catch (MessagingException e) {
            logger.error("html邮件发送失败", e);
        }
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {

    }

    @Override
    public void sendInlineResourceMail(String to, String subject, String content, String rscPath, String rscId) {

    }
}
