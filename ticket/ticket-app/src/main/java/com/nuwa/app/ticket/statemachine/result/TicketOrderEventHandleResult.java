package com.nuwa.app.ticket.statemachine.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @author hy
 */
@Data
public class TicketOrderEventHandleResult implements Serializable {
    private String id;
}
