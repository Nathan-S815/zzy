package com.nuwa.ticket.start.api.pubsystem.service;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenMiniVersionAuditedCancelModel;
import com.alipay.api.domain.AlipayOpenMiniVersionOfflineModel;
import com.alipay.api.request.AlipayOpenMiniVersionAuditedCancelRequest;
import com.alipay.api.request.AlipayOpenMiniVersionOfflineRequest;
import com.alipay.api.response.AlipayOpenMiniVersionAuditedCancelResponse;
import com.alipay.api.response.AlipayOpenMiniVersionOfflineResponse;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppOprateHistory;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppOprateHistoryService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.ticket.start.api.pubsystem.util.AlipayClientWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Date;

@Slf4j
@Service
public class BackToDevelopService {

    @Autowired
    private PsAppOprateHistoryService psAppOprateHistoryService;

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsAppInfoService psAppInfoService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    public boolean back(PsAppOprateHistory psAppOprateHistory) {
        Assert.isTrue(psAppOprateHistory.getAuditStatus().equalsIgnoreCase("passed")
                || psAppOprateHistory.getAuditStatus().equalsIgnoreCase("rejected"), "审核通过或者拒绝的才能操作");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板小程序配置不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                .one();
        Assert.notNull(alipayConfig, "三方应用不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery()
                .eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId())
                .eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId())
                .one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionAuditedCancelRequest request = new AlipayOpenMiniVersionAuditedCancelRequest();
        AlipayOpenMiniVersionAuditedCancelModel bizModel = new AlipayOpenMiniVersionAuditedCancelModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionAuditedCancelResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionAuditedCancelRequest:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getId, psAppOprateHistory.getId())
                        .set(PsAppOprateHistory::getDevelopStatus, "develop")
                        .set(PsAppOprateHistory::getAuditStatus, null)
                        .set(PsAppOprateHistory::getExpQrCodeUrl, null)
                        .set(PsAppOprateHistory::getExperienceStatus, null)
                        .set(PsAppOprateHistory::getGmtPublish, new Date())
                        .update();
                return true;
            } else {
                String subMsg = response.getSubMsg();
                String subCode = response.getSubCode();
                Integer historyId = psAppOprateHistory.getId();
                String json = psAppOprateHistory.getJson();
                JSONObject jsonObject = new JSONObject();
                if (StrUtil.isNotBlank(json)) {
                    jsonObject = JSONUtil.parseObj(json);
                }
                jsonObject.putOpt("subMsg", subMsg);
                jsonObject.putOpt("subCode", subCode);
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getId, historyId)
                        .set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject))
                        .set(PsAppOprateHistory::getGmtModified, new Date())
                        .update();
                log.error("subCode:{},subMsg:{}", subCode, subMsg);
                return false;
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionOfflineRequest error", exception);
            log.error("psAppOprateHistory[{}]", psAppOprateHistory.getId(), exception);
        }
        return false;
    }
}
