package com.nuwa.ticket.start.api.controller.payconfig;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.payconfig.qry.PayConfigPageQry;
import com.nuwa.client.ticket.util.ChannelResult;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.gateway.entity.MerchantPayGatewayConfig;
import com.nuwa.infrastructure.ticket.database.gateway.service.MerchantPayGatewayConfigService;
import com.nuwa.infrastructure.ticket.database.payconfig.param.PayConfigPageParam;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.PaymentMerchantGatewaySender;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.RequestHead;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.req.*;
import com.nuwa.infrastructure.ticket.third.paychannel.miniapp.resp.*;
import com.nuwa.ticket.start.api.controller.payconfig.param.*;
import com.nuwa.ticket.start.api.controller.payconfig.vo.MerchantPayGatewayConfigPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("paycenter/config/")
@Api(tags = {"支付中心配置"})
public class PayConfigController {

    public static final String MERCHANT_ID = "20210621";
    public static final String MERCHANT_SECRET_KEY = "<REDACTED>";

    @Autowired
    private MerchantPayGatewayConfigService merchantPayGatewayConfigService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/weixin/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> weixinAdd(@RequestBody CreateWeixinMiniMerchantParam param, UserAware userAware) {
        String secretKey = RandomUtil.randomString("ABCDEFGHJKLMNabcderflmn123456", 16);
        MerchantPayGatewayConfig gatewayConfig = new MerchantPayGatewayConfig();
        gatewayConfig.setMchId(userAware.getMchId());
        if (Objects.nonNull(param.getMchId())) {
            gatewayConfig.setMchId(param.getMchId());
        }
        gatewayConfig.setChannelAppId(param.getChannelAppId());
        gatewayConfig.setChannelMerchantId(param.getChannelMchId());
        gatewayConfig.setGatewaySecretKey(secretKey);
        gatewayConfig.setName(param.getName());
        gatewayConfig.setCreateDate(new Date());
        gatewayConfig.setGatewayPayType(param.getPayType());
        JSONObject extObj = new JSONObject();
        extObj.putOpt("priKeyUrl", param.getPriKeyUrl());
        extObj.putOpt("wxSecretKey", param.getWxSecretKey());
        gatewayConfig.setExtend(JSONUtil.toJsonStr(extObj));
        gatewayConfig.setStatus(0);
        boolean insert = gatewayConfig.insert();
        if (!insert) {
            return SingleResponse.buildFailure("9864", "创建商户失败");
        }

        CreateMerchantReq createReq = new CreateMerchantReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        CreateMerchantReq.Body body = new CreateMerchantReq.Body();
        body.setChannelAppId(param.getChannelAppId());
        body.setChannelMchId(param.getChannelMchId());
        body.setName(param.getName());
        body.setPayType(param.getPayType());
        body.setPrikey(param.getPriKeyUrl());
        body.setChannelSecretKey(param.getWxSecretKey());
        body.setSecretKey(secretKey);
        Map<String, String> extData = new HashedMap();
        body.setExtData(extData);

        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<CreateMerchantResp> alipayMiniMerchant = PaymentMerchantGatewaySender.createMerchant(createReq, "http://apipayment.zhongzhiyou.cn");
        if (alipayMiniMerchant.isSuccessful()) {
            CreateMerchantResp data = alipayMiniMerchant.getData();
            boolean update = merchantPayGatewayConfigService.lambdaUpdate()
                    .set(MerchantPayGatewayConfig::getGatewayMchId, data.getMchId())
                    .eq(MerchantPayGatewayConfig::getId, gatewayConfig.getId())
                    .update();

            if (update) {
                return SingleResponse.buildSuccess();
            }
        }

        return SingleResponse.buildFailure("9864", "创建商户失败");
    }

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> alipayAdd(@RequestBody CreateAliPayMiniMerchantParam param, UserAware userAware) {
        String secretKey = RandomUtil.randomString("ABCDEFGHJKLMNabcderflmn123456", 16);
        MerchantPayGatewayConfig gatewayConfig = new MerchantPayGatewayConfig();
        gatewayConfig.setMchId(userAware.getMchId());
        if (Objects.nonNull(param.getMchId())) {
            gatewayConfig.setMchId(param.getMchId());
        }
        gatewayConfig.setChannelAppId(param.getChannelAppId());
        gatewayConfig.setGatewaySecretKey(secretKey);
        gatewayConfig.setName(param.getName());
        gatewayConfig.setCreateDate(new Date());
        gatewayConfig.setChannelMerchantId(param.getChannelAppId());
        gatewayConfig.setGatewayPayType(param.getPayType());
        JSONObject extObj = new JSONObject();
        extObj.putOpt("priKeyUrl", param.getPriKeyUrl());
        extObj.putOpt("pubKeyUrl", param.getPubKeyUrl());
        extObj.putOpt("aliPayPubKeyUrl", param.getAliPayPubKeyUrl());
        extObj.putOpt("templateAppId", param.getTemplateAppId());
        gatewayConfig.setExtend(JSONUtil.toJsonStr(extObj));
        gatewayConfig.setStatus(0);
        boolean insert = gatewayConfig.insert();
        if (!insert) {
            return SingleResponse.buildFailure("9864", "创建商户失败");
        }

        CreateMerchantReq createReq = new CreateMerchantReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        CreateMerchantReq.Body body = new CreateMerchantReq.Body();
        body.setChannelAppId(param.getChannelAppId());
        body.setChannelMchId(param.getChannelAppId());
        body.setName(param.getName());
        body.setPayType(param.getPayType());
        body.setPrikey(param.getPriKeyUrl());
        body.setPubkey(param.getPubKeyUrl());
        body.setChannelPubkey(param.getAliPayPubKeyUrl());
        body.setSecretKey(secretKey);
        Map<String, String> extData = new HashedMap();
        extData.put("templateAppId", param.getTemplateAppId());
        body.setExtData(extData);

        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<CreateMerchantResp> alipayMiniMerchant = PaymentMerchantGatewaySender.createMerchant(createReq, "http://apipayment.zhongzhiyou.cn");
        if (alipayMiniMerchant.isSuccessful()) {
            CreateMerchantResp data = alipayMiniMerchant.getData();
            boolean update = merchantPayGatewayConfigService.lambdaUpdate()
                    .set(MerchantPayGatewayConfig::getGatewayMchId, data.getMchId())
                    .eq(MerchantPayGatewayConfig::getId, gatewayConfig.getId())
                    .update();

            if (update) {
                return SingleResponse.buildSuccess();
            }
        }

        return SingleResponse.buildFailure("9864", "创建商户失败");
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> alipayModify(@RequestBody ModifyAliPayMiniMerchantParam param, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(param.getId());

        MerchantPayGatewayConfig gatewayUpdate = new MerchantPayGatewayConfig();
        gatewayUpdate.setId(param.getId());
        gatewayUpdate.setName(param.getName());
        gatewayUpdate.setCreateDate(new Date());
        JSONObject extObj = new JSONObject();
        extObj.putOpt("priKeyUrl", param.getPriKeyUrl());
        extObj.putOpt("pubKeyUrl", param.getPubKeyUrl());
        extObj.putOpt("aliPayPubKeyUrl", param.getAliPayPubKeyUrl());
        gatewayUpdate.setExtend(JSONUtil.toJsonStr(extObj));
        gatewayUpdate.setStatus(0);
        boolean insert = gatewayUpdate.updateById();
        if (!insert) {
            return SingleResponse.buildFailure("9864", "修改商户失败");
        }

        ModifyMerchantReq createReq = new ModifyMerchantReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        ModifyMerchantReq.Body body = new ModifyMerchantReq.Body();
        body.setGatewayMchId(merchantPayGatewayConfig.getGatewayMchId());
        body.setName(param.getName());
        body.setPrikey(param.getPriKeyUrl());
        body.setPubkey(param.getPubKeyUrl());
        body.setChannelPubkey(param.getAliPayPubKeyUrl());
        Map<String, String> extData = new HashedMap();
        body.setExtData(extData);
        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<ModifyMerchantResp> modifyMerchantRespChannelResult = PaymentMerchantGatewaySender.modifyMerchant(createReq, "http://apipayment.zhongzhiyou.cn");
        if (modifyMerchantRespChannelResult.isSuccessful()) {
            return SingleResponse.buildSuccess();
        }

        return SingleResponse.buildFailure("9864", "修改商户失败");
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/weixinModify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> weixinModify(@RequestBody ModifyWeiXinMiniMerchantParam param, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(param.getId());

        MerchantPayGatewayConfig gatewayUpdate = new MerchantPayGatewayConfig();
        gatewayUpdate.setId(param.getId());
        gatewayUpdate.setName(param.getName());
        gatewayUpdate.setCreateDate(new Date());
        JSONObject extObj = new JSONObject();
        extObj.putOpt("priKeyUrl", param.getPriKeyUrl());
        extObj.putOpt("wxSecretKey", param.getWxSecretKey());
        gatewayUpdate.setExtend(JSONUtil.toJsonStr(extObj));
        gatewayUpdate.setStatus(0);
        boolean insert = gatewayUpdate.updateById();
        if (!insert) {
            return SingleResponse.buildFailure("9864", "修改商户失败");
        }

        ModifyMerchantReq createReq = new ModifyMerchantReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        ModifyMerchantReq.Body body = new ModifyMerchantReq.Body();
        body.setName(param.getName());
        body.setGatewayMchId(merchantPayGatewayConfig.getGatewayMchId());
        body.setPrikey(param.getPriKeyUrl());
        body.setChannelSecretKey(param.getWxSecretKey());
        body.setName(param.getName());
        Map<String, String> extData = new HashedMap();
        body.setExtData(extData);
        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<ModifyMerchantResp> modifyMerchantRespChannelResult = PaymentMerchantGatewaySender.modifyMerchant(createReq, "http://apipayment.zhongzhiyou.cn");
        if (modifyMerchantRespChannelResult.isSuccessful()) {
            return SingleResponse.buildSuccess();
        }

        return SingleResponse.buildFailure("9864", "修改商户失败");
    }

    @ApiOperation(value = "重置支付中台密钥")
    @RequestMapping(value = "/reset/secretKey", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> resetSecretKey(@RequestBody ResetMerchantSecretKeyParam param, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(param.getId());

        MerchantPayGatewayConfig gatewayUpdate = new MerchantPayGatewayConfig();
        gatewayUpdate.setId(param.getId());
        gatewayUpdate.setGatewaySecretKey(param.getSecretKey());
        gatewayUpdate.setCreateDate(new Date());
        JSONObject extObj = JSONUtil.parseObj(merchantPayGatewayConfig.getExtend());
        gatewayUpdate.setExtend(JSONUtil.toJsonStr(extObj));
        gatewayUpdate.setStatus(0);
        boolean insert = gatewayUpdate.updateById();
        if (!insert) {
            return SingleResponse.buildFailure("9864", "修改商户失败");
        }

        MerchantModifySecretKeyReq createReq = new MerchantModifySecretKeyReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        MerchantModifySecretKeyReq.Body body = new MerchantModifySecretKeyReq.Body();
        body.setGatewayMchId(merchantPayGatewayConfig.getGatewayMchId());
        body.setSecretKey(param.getSecretKey());
        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<MerchantParamPublishResp> merchantParamPublishRespChannelResult = PaymentMerchantGatewaySender.modifySecretKey(createReq, "http://apipayment.zhongzhiyou.cn");
        if (merchantParamPublishRespChannelResult.isSuccessful()) {
            return SingleResponse.buildSuccess();
        }

        return SingleResponse.buildFailure("9864", "重置密钥失败");
    }

    @ApiOperation(value = "分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantPayGatewayConfigPageVO>> page(PayConfigPageQry pageQry, UserAware userAware) {
        PayConfigPageParam pageParam = new PayConfigPageParam(pageQry);
        IPage<MerchantPayGatewayConfigPageVO> pageData = merchantPayGatewayConfigService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantPayGatewayConfigPageVO> getById(@PathVariable("id") Long id, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(id);
        //Assert.isTrue(userAware.getMchId().equals(merchantPayGatewayConfig.getMchId()));
        return SingleResponse.of(this.toVO(merchantPayGatewayConfig));
    }

    @ApiOperation(value = "启用")
    @RequestMapping(value = "/on/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> on(@PathVariable("id") Long id, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(id);
        Assert.isTrue(userAware.getMchId().equals(merchantPayGatewayConfig.getMchId()));
        MerchantPublishReq createReq = new MerchantPublishReq();
        RequestHead head = new RequestHead(MERCHANT_ID, new Date());
        createReq.setHead(head);
        MerchantPublishReq.Body body = new MerchantPublishReq.Body();
        body.setGatewayMchId(merchantPayGatewayConfig.getGatewayMchId());
        createReq.setBody(body);
        String sign = createReq.buildSign(MERCHANT_SECRET_KEY);
        head.setSign(sign);
        ChannelResult<MerchantParamPublishResp> alipayMiniMerchant = PaymentMerchantGatewaySender.publish(createReq, "http://apipayment.zhongzhiyou.cn");
        if (alipayMiniMerchant.isSuccessful()) {
            boolean update = merchantPayGatewayConfigService.lambdaUpdate()
                    .set(MerchantPayGatewayConfig::getStatus, 1)
                    .set(MerchantPayGatewayConfig::getUpdateDate, new Date())
                    .eq(MerchantPayGatewayConfig::getId, id)
                    .update();

            if (update) {
                return SingleResponse.buildSuccess();
            }
        }

        return SingleResponse.buildFailure("9876", "启用失败");
    }

    @ApiOperation(value = "关闭")
    @RequestMapping(value = "/off/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> off(@PathVariable("id") Long id, UserAware userAware) {
        MerchantPayGatewayConfig merchantPayGatewayConfig = merchantPayGatewayConfigService.getById(id);
        Assert.isTrue(userAware.getMchId().equals(merchantPayGatewayConfig.getMchId()));
        boolean update = merchantPayGatewayConfigService.lambdaUpdate()
                .set(MerchantPayGatewayConfig::getStatus, 0)
                .set(MerchantPayGatewayConfig::getUpdateDate, new Date())
                .eq(MerchantPayGatewayConfig::getId, id)
                .update();

        if (update) {
            return SingleResponse.buildSuccess();
        }

        return SingleResponse.buildFailure("9876", "关闭失败");
    }

    public MerchantPayGatewayConfigPageVO toVO(MerchantPayGatewayConfig config) {
        MerchantPayGatewayConfigPageVO merchantPayGatewayConfigPageVO = new MerchantPayGatewayConfigPageVO();
        BeanUtils.copyProperties(config, merchantPayGatewayConfigPageVO);
        String extend = config.getExtend();
        JSONObject jsonObject = JSONUtil.parseObj(extend);
        merchantPayGatewayConfigPageVO.setExtendObj(jsonObject);
        return merchantPayGatewayConfigPageVO;
    }
}
