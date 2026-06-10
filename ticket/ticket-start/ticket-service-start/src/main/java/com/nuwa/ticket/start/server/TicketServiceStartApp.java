package com.nuwa.ticket.start.server;



import com.nuwa.infrastructure.ticket.delayqueue.listener.ApplicationStartup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executor;

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
@SpringBootApplication(scanBasePackages = {"com.nuwa", "com.alibaba.cola"})
public class TicketServiceStartApp extends AsyncConfigurerSupport implements WebMvcConfigurer  {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");
        long startTime = System.currentTimeMillis();
        SpringApplication application = new SpringApplication(TicketServiceStartApp.class);
        application.run(args);
        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }

    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(5);
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.initialize();
        return executor;
    }
}
