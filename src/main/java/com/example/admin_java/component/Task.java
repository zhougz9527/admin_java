package com.example.admin_java.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @Author: Think
 * @Date: 2018/6/13
 * @Time: 23:38
 */
@Slf4j
@Component
public class Task {

    private static Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception {
        log.info("doTaskOne ----> start");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("doTaskOne ----> end, 耗时: {} 毫秒", (end - start));
        return new AsyncResult<>("doTaskOne done");
    }

    @Async
    public Future<String> doTaskTwo() throws Exception {
        log.info("doTaskTwo ----> start");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("doTaskTwo ----> end, 耗时: {} 毫秒", (end - start));
        return new AsyncResult<>("doTaskTwo done");
    }

    @Async
    public Future<String> doTaskThree() throws Exception {
        log.info("doTaskThree ----> start");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("doTaskThree ----> end, 耗时: {} 毫秒", (end - start));
        return new AsyncResult<>("doTaskThree done");
    }

}
