package com.nuwa.infrastructure.ticket.config;

import com.alibaba.cola.boot.SpringBootstrap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for COLA framework
 * @author hy
 */
@Configuration
@ComponentScan(value = {"com.alibaba.cola","com.nuwa"})
public class ColaConfig {

    @Bean(initMethod = "init")
    public SpringBootstrap bootstrap() {
        SpringBootstrap bootstrap = new SpringBootstrap();
        return bootstrap;
    }
}