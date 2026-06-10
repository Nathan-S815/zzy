package com.nuwa.client.discovery.dto.domainevent;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务报名事件
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserTaskApplyEvent extends BaseEvent {

    private Long taskId;

    private Long taskApplyId;

    public UserTaskApplyEvent(Long taskId,Long taskApplyId) {
        this.taskId = taskId;
        this.taskApplyId = taskApplyId;
    }
}
