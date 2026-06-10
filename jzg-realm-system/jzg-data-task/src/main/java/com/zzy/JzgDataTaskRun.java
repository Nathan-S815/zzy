package com.zzy;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.scheduling.annotation.EnableScheduling;


@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.zzy")
@ComponentScan(basePackages = {"com.zzy"},excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = com.zzy.core.config.SwaggerConfiguration .class))
@MapperScan(basePackages = {"com.zzy.db.dao"})
public class JzgDataTaskRun {


    public static void main(String[] args) {
        SpringApplication.run(JzgDataTaskRun.class, args);
    }




}
