package com.nuwa.client.ticket.api.order.param;

import lombok.Data;

import java.util.Date;

/**
 * 抖音结算回调参数
 *
 * @author hy
 */
@Data
public class DouyinSettleNotifyParam {
    private String orderNo;
    private String status;
    private Date settledAt;
}
