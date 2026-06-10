package com.nuwa.discovery.start.api;


import com.mzt.logapi.starter.annotation.EnableLogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot Starter
 * <p>
 * COLA framework initialization is configured
 *
 * @author hy
 */
@Slf4j
@EnableAsync
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.nuwa"})
@EnableScheduling
@EnableLogRecord(tenant = "com.nuwa.discovery.start.api.controller")
@SpringBootApplication(scanBasePackages = {"com.nuwa", "com.alibaba.cola"})
public class MchApiStartApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");

        long startTime = System.currentTimeMillis();

        SpringApplication.run(MchApiStartApplication.class, args);

        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
