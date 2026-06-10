package com.nuwa.discovery.start.api.controller.vo;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class CheckTicketResultVO {
    private Boolean status;
    private String msg;
    private Long prizeId;
}
