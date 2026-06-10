package com.nuwa.infrastructure.discovery.common.event;

import cn.hutool.json.JSONUtil;
import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * SpringEventPublisher
 *
 * @author hy
 * @date 2021/5/2 15:13
 * @since 1.0.0
 */
@Slf4j
@Component
public class DomainEventPublisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.publisher = publisher;
    }

    @Async
    public void publishEvent(BaseEvent event) {
        try {
            log.info("publishEvent:{}[{}]", event.getClass().getSimpleName(), JSONUtil.toJsonStr(event));
            publisher.publishEvent(event);
        } catch (Exception ex) {
            log.error("publishEvent[{}] error", event.getClass().getSimpleName(), ex);
        }
    }
}
