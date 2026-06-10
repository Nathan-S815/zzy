package com.nuwa.app.ticket.statemachine;

import com.cmt.statemachine.builder.StateMachineBuilder;

/**
 * EventProcessor
 *
 * @author hy
 * @date 2021/4/21 11:25
 * @since 1.0.0
 */
public interface EventProcessor<S, E> {
    /**
     * 事件初始化
     *
     * @param builder StateMachineBuilder
     */
    public void initEvent(StateMachineBuilder<S, E> builder);
}
