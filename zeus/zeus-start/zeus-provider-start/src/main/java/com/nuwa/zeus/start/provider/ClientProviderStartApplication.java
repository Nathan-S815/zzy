package com.nuwa.zeus.start.provider;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author hy
 */
@Slf4j
@EnableFeignClients(basePackages = {"com.nuwa.app.zeus.client.feign"})
@SpringBootApplication(scanBasePackages = {
        "com.nuwa.zeus.start.provider",
        "com.nuwa.app.zeus.event",
        "com.nuwa.infrastructure.zeus",
        "com.nuwa.app.zeus.command",
        "com.nuwa.app.zeus.service",
        "com.alibaba.cola"})
public class ClientProviderStartApplication{

    public static void main(String[] args) {
        log.info("Begin to start  ClientProviderStartApplication");
        long startTime = System.currentTimeMillis();
        SpringApplication.run(ClientProviderStartApplication.class, args);
        long endTime = System.currentTimeMillis();
        log.info("End starting ClientProviderStartApplication, Time used: "+ (endTime - startTime) );
    }
}
