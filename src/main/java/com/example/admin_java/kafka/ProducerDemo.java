package com.example.admin_java.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * @Author: Think
 * @Date: 2018/8/31
 * @Time: 10:23
 */
public class ProducerDemo {

    public static void main (String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("acks", "all");
        properties.put("retries", 0);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Producer<String, String> producer = null;

        try {
            producer = new KafkaProducer<String, String>(properties);
            for (int i = 0; i < 100; i++) {
                String msg = "java kafka demo 模拟消息 ----- " + i;
                producer.send(new ProducerRecord<String, String>("UNICOM1", msg));
                System.out.println("sent:" + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("kafka 异常处理: " + e.getMessage());
        } finally{
            producer.close();
        }

    }


}
