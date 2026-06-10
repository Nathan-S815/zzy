package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp;

import cn.hutool.json.JSONObject;
import lombok.Data;

/**
 * WxMiniOrderCreateResp
 *
 * @author hy
 * @date 2020/10/29 16:40
 * @since 1.0.0
 */
@Data
public class MiniAppOrderCreateResp {
    /**
     * {"msg":"成功","code":"0000","data":{"amount":100,"mchId":2008111325,"statusDesc":"支付中","platOrderNo":"1000000000603","merOrderNo":"89995533255","payParams":{"timeStamp":1603959332,"package":"prepay_id=wx2916153231762082dc08ca510a31710000","paySign":"56E2B4D5EEA00DDACF671959036AC536","signType":"MD5","nonceStr":"EurJMtHbWayjwJXt"},"channelDesc":"原生微信小程序","status":"PAY_PENDING"},"success":true,"sign":"4F21578F0515FADC9128328174B07ADD"}
     */
    private Long amount;
    private String mchId;
    private String statusDesc;
    private String platOrderNo;
    private String merOrderNo;
    private JSONObject payParams;
    private String channelDesc;
    private String sign;
}
