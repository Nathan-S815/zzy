package com.nuwa.client.discovery.dto.domainevent;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 达人权益审核事件
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserPrizeAuditEvent extends BaseEvent {

    private Long userPrizeId;

    public UserPrizeAuditEvent(Long userPrizeId) {
        this.userPrizeId = userPrizeId;
    }
}
