package com.zzy.datawarehouse.display;


import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Spring Boot Starter
 * <p>
 * COLA framework initialization is configured
 *
 * @author hy
 */
@Slf4j
@SpringBootApplication
@EnableSwagger2
@MapperScan("com.zzy.datawarehouse.display.mapper")
public class DatawarehouseStartApp implements WebMvcConfigurer {

    public static void main(String[] args) {
        log.info("Begin to start Spring Boot Application");
        long startTime = System.currentTimeMillis();
        SpringApplication.run(DatawarehouseStartApp.class, args);
        long endTime = System.currentTimeMillis();
        log.info("End starting Spring Boot Application, Time used: " + (endTime - startTime));
    }
}
