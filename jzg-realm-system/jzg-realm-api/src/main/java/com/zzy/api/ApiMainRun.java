package com.zzy.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication(scanBasePackages = "com.zzy")
@EnableScheduling
public class ApiMainRun {

    public static void main(String[] args) {
        SpringApplication.run(ApiMainRun.class, args);
    }

}
