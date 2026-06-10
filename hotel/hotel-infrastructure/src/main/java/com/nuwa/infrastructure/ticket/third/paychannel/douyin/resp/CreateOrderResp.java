package com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class CreateOrderResp {
    private String err_no;
    private String err_tips;

    private Data data;

    @lombok.Data
    public static class Data{
        private String order_id;
        private String order_token;
    }
}
