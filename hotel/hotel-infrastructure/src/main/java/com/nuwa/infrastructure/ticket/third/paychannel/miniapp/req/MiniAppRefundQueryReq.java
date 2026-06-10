package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.util.SignUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Objects;

/**
 * MiniAppRefundQueryReq 退款查询
 *
 * @author hy
 * @date 2020/10/29 13:51
 * @since 1.0.0
 */
@Slf4j
@Data
public class MiniAppRefundQueryReq {

    private RequestHead head;

    private Body body;

    public String buildSign(String secretKey) {
        HashMap<String, String> needSignParams = new HashMap<String, String>();
        JSONObject jsonObject = JSONUtil.parseObj(this.getBody());
        jsonObject.forEach((k, v) -> {
            if (!Objects.isNull(v)) {
                needSignParams.put(k, v.toString());
            }
        });
        needSignParams.put("charset", head.getCharset());
        needSignParams.put("format", head.getFormat());
        needSignParams.put("sign_type", head.getSign_type());
        needSignParams.put("timestamp", head.getTimestamp());
        needSignParams.put("version", head.getVersion());
        needSignParams.put("mchId", head.getMchId());
        try {
            return SignUtils.sign(needSignParams, null, secretKey);
        } catch (Exception ex) {
            log.error("获取签名出错", ex
            );
        }
        return null;
    }

    @Data
    public static class Body {

        @NotNull(message = "商户号不能为空")
        private Integer mchId;


        @NotNull(message = "商户退款订单号不能为空")
        private String mchOrderNo;

        @NotNull(message = "平台退款订单号不能为空")
        private String platOrderNo;
    }
}
