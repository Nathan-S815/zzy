package com.nuwa.discovery.start.api.event;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * BindMobileSendCodeEvent 绑定手机号发送短信事件
 *
 * @author hy
 * @date 2021/5/2 15:02
 * @since 1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BindMobileSendCodeEvent extends BaseEvent {
    /**
     * smsId
     */
    private Long smsId;

    public BindMobileSendCodeEvent(Long smsId) {
        this.smsId = smsId;
    }
}
