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
public class LoginSucceededEvent extends BaseEvent {

    private Long userId;

    private String userName;

    private String ip;

    private String browser;

    public LoginSucceededEvent(Long userId, String userName, String ip, String browser) {
        this.userId = userId;
        this.userName = userName;
        this.ip = ip;
        this.browser = browser;
    }
}
