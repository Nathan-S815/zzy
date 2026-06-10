package com.nuwa.client.discovery.dto.domainevent;

import com.nuwa.framework.cola.starter.event.BaseEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 任务上新事件
 *
 * @author hy
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class NewTaskOnLineEvent extends BaseEvent {

    private Long taskId;

    public NewTaskOnLineEvent(Long taskId) {
        this.taskId = taskId;
    }
}
