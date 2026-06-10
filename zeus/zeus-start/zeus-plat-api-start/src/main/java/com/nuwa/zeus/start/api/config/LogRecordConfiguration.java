package com.nuwa.zeus.start.api.config;

import com.mzt.logapi.beans.Operator;
import com.mzt.logapi.service.IOperatorGetService;
import com.nuwa.zeus.start.api.config.shiro.ShiroKit;
import com.nuwa.zeus.start.api.config.shiro.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

/**
 * LogRecordConfiguration 日志配置
 *
 * @author hy
 * @date 2021/6/4 15:42
 * @since 1.0.0
 */
@Configuration
public class LogRecordConfiguration {

    @Bean
    public IOperatorGetService operatorGetService() {
        return () -> Optional.of(ShiroKit.getSubject())
                .map(a -> new Operator(((User) a.getPrincipal()).getUserId() + ""))
                .orElseThrow(() -> new IllegalArgumentException("user is null"));
    }
}
