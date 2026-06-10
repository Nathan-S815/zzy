package com.nuwa.infrastructure.ticket.third.paychannel.douyin.resp;

import lombok.Data;

/**
 * @author hy
 */
@Data
public class SettleResp {
    private String err_no;
    private String err_tips;

    /**
     * 平台生成分账单号
     */
    private String settle_no;
}
