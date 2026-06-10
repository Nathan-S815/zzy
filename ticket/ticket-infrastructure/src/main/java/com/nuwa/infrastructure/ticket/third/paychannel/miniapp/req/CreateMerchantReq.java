package com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.util.SignUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ApiModel(value = "创建-商户")
@Data
@Slf4j
public class CreateMerchantReq {

    private RequestHead head;

    private CreateMerchantReq.Body body;

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

        /**
         * 商户名称
         */
        @ApiModelProperty("商户名称")
        private String name;

        /**
         * 商户密钥
         */
        @ApiModelProperty("商户密钥")
        private String secretKey;

        @ApiModelProperty("支付方式 alipay_mini alipay_mini_template weixin_mini")
        private String payType;

        @ApiModelProperty("支付渠道小程序appId")
        private String channelAppId;

        @ApiModelProperty("支付渠道商户id")
        private String channelMchId;

        /**
         * 支付密钥
         */
        @ApiModelProperty("支付密钥")
        private String channelSecretKey;

        @ApiModelProperty("渠道公钥")
        private String channelPubkey;

        @ApiModelProperty("商户私钥")
        private String prikey;

        @ApiModelProperty("商户公钥")
        private String pubkey;

        private Map<String, String> extData;
    }
}
