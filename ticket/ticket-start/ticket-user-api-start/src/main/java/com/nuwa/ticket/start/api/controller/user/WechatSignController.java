package com.nuwa.ticket.start.api.controller.user;

import cn.hutool.core.net.URLDecoder;
import cn.hutool.core.net.URLEncoder;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.ticket.start.api.controller.user.param.WeChatSignParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

/**
 * MemberController
 *
 * @author hy
 * @date 2021/4/30 13:09
 * @since 1.0.0
 */
@Api(tags = {"微信授权"})
@Slf4j
@RestController
@RequestMapping("/wechat")
public class WechatSignController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;


    @ApiOperation(value = "微信签名")
    @PostMapping(value = "/sign")
    public SingleResponse<WxJsapiSignature> sign(@RequestBody WeChatSignParam param) throws UnsupportedEncodingException, WxErrorException {
        MerchantAppBaseConf appConf = merchantAppBaseConfService.lambdaQuery()
                .eq(MerchantAppBaseConf::getOutAppId, param.getAppId())
                .last("limit 1")
                .one();
        WxMpInMemoryConfigStorage config = new WxMpInMemoryConfigStorage();
        config.setAppId(appConf.getOutAppId());
        config.setSecret(appConf.getSecret());
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(config);
        try {
            WxJsapiSignature wxJsapiSignature = wxMpService.createJsapiSignature(param.getUrl());
            return SingleResponse.of(wxJsapiSignature);
        } catch (Exception ex) {
            log.error("获取签名失败", ex);
        }
        return SingleResponse.buildFailure("9632", "获取签名失败");
    }
}
