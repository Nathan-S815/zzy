package com.nuwa.infrastructure.ticket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

/**
 * SpringUtil
 *
 * @author hy
 * @date 2020/11/6 14:53
 * @since 1.0.0
 */
@Slf4j
@Component
public class SpringContextKit implements ApplicationContextAware {
    private static ApplicationContext context;

    public SpringContextKit() {
    }

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextKit.context = context;
    }

    public static <T> T getBean(Class<T> clazz) {
        return clazz == null ? null : context.getBean(clazz);
    }

    public static <T> T getBean(String beanId) {
        return beanId == null ? null : (T) context.getBean(beanId);
    }

    public static <T> T getBean(String beanName, Class<T> clazz) {
        if (null != beanName && !"".equals(beanName.trim())) {
            return clazz == null ? null : context.getBean(beanName, clazz);
        } else {
            return null;
        }
    }

    public static ApplicationContext getContext() {
        return context == null ? null : context;
    }

    public static void publishEvent(ApplicationEvent event) {
        if (context != null) {
            try {
                context.publishEvent(event);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
        }
    }
}
