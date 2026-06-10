package com.nuwa.attract.start.dispatch;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
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
@EnableDiscoveryClient
@EnableScheduling
@EnableFeignClients(basePackages = {"com.nuwa.client.attract"})
@SpringBootApplication(scanBasePackages = {"com.nuwa.attract.start.dispatch"},
        excludeName = {"com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure",
                "org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration"})
public class AttractDaemonStartApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");

        long startTime = System.currentTimeMillis();
        SpringApplication.run(AttractDaemonStartApp.class, args);
        long endTime = System.currentTimeMillis();

        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
