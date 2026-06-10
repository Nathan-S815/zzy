package com.nuwa.ticket.start.api.alipaydata;

import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.nuwa.infrastructure.ticket.database.manager.AlipayDataManager;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.alipaydata.param.DataScenicQueryParam;
import com.nuwa.ticket.start.api.alipaydata.param.DataScenicSyncParam;
import com.nuwa.ticket.start.api.pubsystem.util.AlipayClientWrap;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/alipaydata")
@Api(tags = {"支付宝-数据同步"})
public class AlipayDataController {

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsAppInfoService psAppInfoService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private AlipayDataManager alipayDataManager;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @ApiOperation(value = "支付宝景区信息获取")
    @RequestMapping(value = "/dataScenicQuery", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> dataScenicQuery(@RequestBody @Valid DataScenicQueryParam param) {
        log.info("dataScenicQuery:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, param.getScenicAppId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                .one();

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayCommerceDataScenicQueryRequest request = new AlipayCommerceDataScenicQueryRequest();
        AlipayCommerceDataScenicQueryModel bizModel = new AlipayCommerceDataScenicQueryModel();
        bizModel.setPageNo(1L);
        bizModel.setPageSize(100L);
        bizModel.setScenicAppId(param.getScenicAppId());
        request.setBizModel(bizModel);
        //request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayCommerceDataScenicQueryResponse response = alipayClient.execute(request);
            log.info("AlipayCommerceDataScenicQueryResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                String body = response.getBody();
                return SingleResponse.of(JSONUtil.parseObj(body).getJSONObject("alipay_commerce_data_scenic_query_response").getJSONArray("scenic_info"));
               /* if (Objects.nonNull(response.getPaginationScenicInfo())) {
                    return SingleResponse.of(response.getPaginationScenicInfo().getScenicInfo());
                } else {
                    return SingleResponse.buildFailure("0987", "没有获取到数据");
                }*/
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayCommerceDataScenicQueryRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }


    @ApiOperation(value = "景区ID内外域映射配置")
    @RequestMapping(value = "/dataScenicSync", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> dataScenicSync(@RequestBody @Valid DataScenicSyncParam param) {
        log.info("dataScenicQuery:{}", JSONUtil.toJsonPrettyStr(param));
        Scenicspot scenicspot = scenicspotService.lambdaQuery()
                .eq(Scenicspot::getId, param.getScenicId())
                .eq(Scenicspot::getVersionFlag, 1)
                .one();
        Assert.notNull(scenicspot, "景区不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, param.getScenicAppId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                .one();
        /*AlipayDataScenic dataScenic = new AlipayDataScenic();
        dataScenic.setScenicId(param.getScenicId());
        dataScenic.setAlipayAppId(param.getScenicAppId());
        dataScenic.setAlipayScenicId(param.getAlipayScenicId());
        dataScenic.setScenicName(scenicspot.getName());
        dataScenic.setIsvName(alipayConfig.getIsvName());
        dataScenic.setStatus("init");
        dataScenic.setGmtCreate(new Date());
        dataScenic.setIsvId(alipayConfig.getIsvId());
        dataScenic.setTemplateId(alipayConfig.getTemplateId());
        boolean insert = dataScenic.insert();
        if (!insert) {
            return SingleResponse.buildFailure("0987", "景区ID内外域映射配置操作失败");
        }*/

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayCommerceDataScenicSyncRequest request = new AlipayCommerceDataScenicSyncRequest();
        AlipayCommerceDataScenicSyncModel bizModel = new AlipayCommerceDataScenicSyncModel();
        bizModel.setScenicAppId(param.getScenicAppId());
        bizModel.setScenicId(param.getAlipayScenicId());
        bizModel.setOuterId(param.getScenicId());
        bizModel.setIsvScenicName(scenicspot.getName());
        bizModel.setIsvScenicAddress(scenicspot.getAddress());
        bizModel.setIsvName(alipayConfig.getIsvName());
        bizModel.setScenicAppId(param.getScenicAppId());
        request.setBizModel(bizModel);
        // request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayCommerceDataScenicSyncResponse response = alipayClient.execute(request);
            log.info("AlipayCommerceDataScenicSyncResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
              /*  boolean update = alipayDataScenicService.lambdaUpdate()
                        .set(AlipayDataScenic::getStatus, "audtiting")
                        .set(AlipayDataScenic::getGmtModified, new Date())
                        .eq(AlipayDataScenic::getId, dataScenic.getId())
                        .update();
                if (update) {
                    log.info("AlipayCommerceDataScenicSyncRequest[id:{}] success", dataScenic.getId());
                }*/
                return SingleResponse.buildSuccess();
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayCommerceDataScenicQueryRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }

    @ApiOperation(value = "景区ID内外域映射配置-结果查询")
    @RequestMapping(value = "/dataScenicSyncQuery", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> dataScenicSyncQuery(@RequestBody @Valid DataScenicSyncParam param) {
        log.info("dataScenicQuery:{}", JSONUtil.toJsonPrettyStr(param));
        Scenicspot scenicspot = scenicspotService.lambdaQuery()
                .eq(Scenicspot::getId, param.getScenicId())
                .eq(Scenicspot::getVersionFlag, 1)
                .one();
        Assert.notNull(scenicspot, "景区不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, param.getScenicAppId()).one();

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                .one();
       /* AlipayDataScenic dataScenic = alipayDataScenicService.lambdaQuery()
                .eq(AlipayDataScenic::getScenicId, param.getScenicId())
                .eq(AlipayDataScenic::getAlipayAppId, param.getScenicAppId())
                .one();*/

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayCommerceDataScenicMappingQueryRequest request = new AlipayCommerceDataScenicMappingQueryRequest();
        AlipayCommerceDataScenicMappingQueryModel bizModel = new AlipayCommerceDataScenicMappingQueryModel();
        List<ScenicAuditQueryReq> scenicAuditQueryReq = new ArrayList<>();
        ScenicAuditQueryReq auditQueryReq = new ScenicAuditQueryReq();
    /*    auditQueryReq.setScenicAppId(dataScenic.getAlipayAppId());
        auditQueryReq.setScenicId(dataScenic.getAlipayScenicId());*/
        scenicAuditQueryReq.add(auditQueryReq);
        bizModel.setScenicAuditQueryReq(scenicAuditQueryReq);
        request.setBizModel(bizModel);
        // request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayCommerceDataScenicMappingQueryResponse response = alipayClient.execute(request);
            log.info("AlipayCommerceDataScenicMappingQueryResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess() && Objects.nonNull(response.getScenicAuditResponse())) {
                return SingleResponse.of(response.getScenicAuditResponse().getScenicAuditInfo());
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayCommerceDataScenicMappingQueryRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }

    @ApiOperation(value = "更新订单")
    @RequestMapping(value = "/orderUpdate/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> dataScenicSyncQuery(@PathVariable("id") Long orderId) {
        alipayDataManager.orderUpdate(orderId);
        return SingleResponse.buildSuccess();
    }

}
