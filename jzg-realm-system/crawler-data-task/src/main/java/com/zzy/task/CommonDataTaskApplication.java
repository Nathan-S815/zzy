package com.zzy.task;

import com.zzy.core.config.SwaggerConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"com.zzy"}, excludeFilters =
@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.zzy.core.config.SwaggerConfiguration .class))
@MapperScan(basePackages = {"com.zzy.task.common.db.dao"})
public class CommonDataTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonDataTaskApplication.class, args);
    }

}
