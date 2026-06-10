package com.nuwa.client.discovery.dto.domainevent;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 达人权益认领事件
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@ToString
@Data
public class UserPrizeSubmitEvent extends BaseEvent {

    private Long userPrizeId;

    public UserPrizeSubmitEvent(Long userPrizeId) {
        this.userPrizeId = userPrizeId;
    }
}
