package com.example.admin_java.kafka;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.sql.Time;
import java.util.Arrays;
import java.util.Properties;

/**
 * @Author: Think
 * @Date: 2018/8/31
 * @Time: 10:56
 */
public class ConsumerDemo {

    public static void main (String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("group.id", "group-1");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<String, String>(properties);
        kafkaConsumer.subscribe(Arrays.asList("UNICOM1"));
        while (true) {
            ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                try {
                    Thread.sleep(1000);
                    System.out.println("offest = " + record.offset() + ", value = " + record.value());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
