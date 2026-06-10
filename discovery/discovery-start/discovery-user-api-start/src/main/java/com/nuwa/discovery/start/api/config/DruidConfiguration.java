package com.nuwa.discovery.start.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * 数据源配置
 *
 * @author hy
 * @date 2020/10/13 19:37
 * @since 1.0.0
 */
@Configuration
public class DruidConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);
    private static final String DB_PREFIX = "spring.datasource";
}
