package com.example.admin_java.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 *
 * AOP类
 *
 * @Author: Think
 * @Date: 2018/6/8
 * @Time: 0:37
 */
@Component
@Aspect
public class WebLogAspect {

    private static Logger logger = LoggerFactory.getLogger(WebLogAspect.class);

    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(public * com.example.admin_java.controller..*.*(..))")
    public void webLog() {

    }

    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {

        startTime.set(System.currentTimeMillis());

        //接受请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //获取请求的参数
        String paramString = "";
        Enumeration<String> enumeration = request.getParameterNames();
        while (enumeration.hasMoreElements()) {
            String paramName = enumeration.nextElement();
            paramString += paramName + ": " +request.getParameter(paramName) + " ";
        }

        //记录请求内容
        logger.info("IP: " + request.getRemoteAddr());
        logger.info("PORT: " + request.getRemotePort());
        logger.info("URL: " + request.getRequestURL().toString());
        logger.info("HTTP_METHOD: " + request.getMethod());
        logger.info("CLASS_METHOD: " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        logger.info("ARGS: " + paramString);
        logger.info("CONTENT_TYPE: " + request.getContentType());
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) throws Throwable{
        //处理完请求，返回内容
        logger.info("RESPONSE: " + JSON.toJSONString(result));
        logger.info("SPEND TIME: " + (System.currentTimeMillis() - startTime.get()));
    }

}
