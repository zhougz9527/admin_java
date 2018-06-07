package com.example.admin_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class AdminJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminJavaApplication.class, args);
	}
}
