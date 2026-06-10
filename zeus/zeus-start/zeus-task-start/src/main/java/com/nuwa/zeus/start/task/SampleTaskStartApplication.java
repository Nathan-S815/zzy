package com.nuwa.zeus.start.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 定时任务启动器
 *
 * @author hy
 * @date 2021/4/23 15:32
 * @since 1.0.0
 */
@Slf4j
@EnableDiscoveryClient
@EnableScheduling
@SpringBootApplication(scanBasePackages = {"com.nuwa.client.zeus.api","com.alibaba.cola"})
public class SampleTaskStartApplication {

    public static void main(String[] args) {
        log.info("Begin to start  SampleTaskStartApplication");
        long startTime = System.currentTimeMillis();

        SpringApplication.run(SampleTaskStartApplication.class, args);

        long endTime = System.currentTimeMillis();
        log.info("End starting ClientProviderStartApplication, Time used: " + (endTime - startTime));
    }
}
