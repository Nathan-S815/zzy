package com.nuwa.client.zeus.dto.domainevent.sms;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PersistentSmsCodeSucceededEvent 持久化短信记录成功事件
 *
 * @author hy
 * @date 2021/5/2 15:02
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersistentSmsCodeSucceededEvent extends BaseEvent {
    /**
     * smsId
     */
    private Long smsId;

    public PersistentSmsCodeSucceededEvent(Long smsId) {
        this.smsId = smsId;
    }
}
