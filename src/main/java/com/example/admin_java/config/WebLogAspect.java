package com.example.admin_java.config;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
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
@Slf4j
@Component
@Aspect
public class WebLogAspect {

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
        log.info("IP: {}", request.getRemoteAddr());
        log.info("PORT: {}", request.getRemotePort());
        log.info("URL: {}", request.getRequestURL().toString());
        log.info("HTTP_METHOD: {}", request.getMethod());
        log.info("CLASS_METHOD: {}", joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("ARGS: {}", paramString);
        log.info("CONTENT_TYPE: {}", request.getContentType());
    }

    @AfterReturning(returning = "result", pointcut = "webLog()")
    public void doAfterReturning(Object result) throws Throwable{
        //处理完请求，返回内容
        Gson gson = new Gson();
        log.info("RESPONSE: {}", gson.toJson(result));
        log.info("SPEND TIME: {}", (System.currentTimeMillis() - startTime.get()));
    }

}
