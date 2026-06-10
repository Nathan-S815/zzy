package com.nuwa.zeus.start.notify;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 异步通知启动器
 *
 * @author hy
 * @date 2021/4/23 15:58
 * @since 1.0.0
 */
@Slf4j
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.nuwa"})
@SpringBootApplication(scanBasePackages = {"com.nuwa.client.zeus.api", "com.nuwa.zeus.start.notify"})
public class NotifyStartApplication {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");
        long startTime = System.currentTimeMillis();

        SpringApplication.run(NotifyStartApplication.class, args);

        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
