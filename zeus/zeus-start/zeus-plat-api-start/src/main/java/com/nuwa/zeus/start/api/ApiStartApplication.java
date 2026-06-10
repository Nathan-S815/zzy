package com.nuwa.zeus.start.api;


import com.mzt.logapi.starter.annotation.EnableLogRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot Starter
 * <p>
 * COLA framework initialization is configured
 *
 * @author Frank Zhang
 */
@Slf4j
@EnableScheduling
@EnableAsync
@EnableLogRecord(tenant = "com.nuwa.zeus.start.api.controller")
@SpringBootApplication(scanBasePackages = {
        "com.nuwa.zeus.start.api",
        "com.nuwa.infrastructure.zeus",
        "com.nuwa.app.zeus.command",
        "com.nuwa.app.zeus.service",
         "com.alibaba.cola"})
public class ApiStartApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");

        long startTime = System.currentTimeMillis();

        SpringApplication.run(ApiStartApplication.class, args);

        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
