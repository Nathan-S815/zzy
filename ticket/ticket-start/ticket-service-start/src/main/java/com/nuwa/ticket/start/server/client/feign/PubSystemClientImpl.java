package com.nuwa.ticket.start.server.client.feign;

import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayOpenMiniExperienceQueryModel;
import com.alipay.api.domain.AlipayOpenMiniVersionBuildQueryModel;
import com.alipay.api.request.AlipayOpenMiniExperienceQueryRequest;
import com.alipay.api.request.AlipayOpenMiniVersionBuildQueryRequest;
import com.alipay.api.response.AlipayOpenMiniExperienceQueryResponse;
import com.alipay.api.response.AlipayOpenMiniVersionBuildQueryResponse;
import com.nuwa.client.ticket.api.pubsystem.PubSystemClientI;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppOprateHistory;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppOprateHistoryService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.infrastructure.ticket.util.AlipayClientWrap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * OrderClient实现
 *
 * @author hy
 */
@Slf4j
@RestController
public class PubSystemClientImpl implements PubSystemClientI {

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsAppInfoService psAppInfoService;

    @Autowired
    private PsAppOprateHistoryService psAppOprateHistoryService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @Override
    public SingleResponse<?> takeAndUpdateVersionUploadStatus() {
        /* 创建版本的状态，状态说明如下：
         * 0：构建排队中；
         * 1：正在构建；
         * 2：构建成功；
         * 3：构建失败；
         * 5：构建超时；
         * 6：版本创建成功。
         */
        List<PsAppOprateHistory> list = psAppOprateHistoryService.lambdaQuery()
                .eq(PsAppOprateHistory::getNeedRotation, 0)
                .in(PsAppOprateHistory::getBuildStatus, 0, 1, 2)
                .last("limit 5")
                .list();
        list.forEach(this::doUpdateVersionUpload);
        return SingleResponse.of(list.size());
    }

    @Override
    public SingleResponse<?> takeAndUpdateExpStatus() {
        List<PsAppOprateHistory> list = psAppOprateHistoryService.lambdaQuery()
                .eq(PsAppOprateHistory::getNeedRotation, 0)
                .in(PsAppOprateHistory::getExperienceStatus, "wait_exp")
                .last("limit 5")
                .list();
        list.forEach(this::doUpdateExpStatus);
        return SingleResponse.of(list.size());
    }

    public void doUpdateVersionUpload(PsAppOprateHistory history) {
        try {
            String templateId = history.getTemplateId();
            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, templateId).one();

            PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                    .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                    .one();
            if (Objects.isNull(alipayConfig)) {
                log.error("PsAlipayConfig(TemplateId:{}) 不存在", templateId);
                return;
            }

            PsAppInfo appInfo = psAppInfoService.lambdaQuery()
                    .eq(PsAppInfo::getAlipayAppId, history.getAppId())
                    .eq(PsAppInfo::getTemplateId, history.getTemplateId())
                    .one();
            if (Objects.isNull(appInfo)) {
                log.error("PsAppInfo(appId:{}) 不存在", history.getAppId());
                return;
            }

            AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
            AlipayOpenMiniVersionBuildQueryRequest request = new AlipayOpenMiniVersionBuildQueryRequest();
            AlipayOpenMiniVersionBuildQueryModel bizModel = new AlipayOpenMiniVersionBuildQueryModel();
            bizModel.setAppVersion(history.getAppVersion());
            bizModel.setBundleId("com.alipay.alipaywallet");
            request.setBizModel(bizModel);
            request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
            try {
                AlipayOpenMiniVersionBuildQueryResponse response = alipayClient.execute(request);
                log.info("AlipayOpenMiniVersionBuildQueryResponse:{}", JSONUtil.toJsonPrettyStr(response));
                if (response.isSuccess()) {
                    /**
                     * 创建版本的状态，状态说明如下：
                     * 0：构建排队中；
                     * 1：正在构建；
                     * 2：构建成功；
                     * 3：构建失败；
                     * 5：构建超时；
                     * 6：版本创建成功。
                     */
                    String createStatus = response.getCreateStatus();
                    psAppOprateHistoryService.lambdaUpdate()
                            .eq(PsAppOprateHistory::getId, history.getId())
                            .set(PsAppOprateHistory::getBuildStatus, createStatus)
                            .set(createStatus.equalsIgnoreCase("6"), PsAppOprateHistory::getDevelopStatus, "develop")
                            .set(PsAppOprateHistory::getNeedRotation, response.getNeedRotation().equalsIgnoreCase("true") ? 0 : 1)
                            .set(PsAppOprateHistory::getGmtModified, new Date())
                            .update();
                } else {
                    String subMsg = response.getSubMsg();
                    String subCode = response.getSubCode();
                    log.error("appId:{},subCode:{},subMsg:{}", history.getAppId(), subCode, subMsg);
                }
            } catch (Exception exception) {
                log.error("do AlipayOpenMiniVersionBuildQuery error", exception);
            }
        } catch (Exception ex) {
            log.error("appId:{},处理异常", history.getAppId(), ex);
        }
    }

    public void doUpdateExpStatus(PsAppOprateHistory history) {
        try {
            String templateId = history.getTemplateId();
            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, templateId).one();

            PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery()
                    .eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId())
                    .one();
            if (Objects.isNull(alipayConfig)) {
                log.error("PsAlipayConfig(TemplateId:{}) 不存在", templateId);
                return;
            }

            PsAppInfo appInfo = psAppInfoService.lambdaQuery()
                    .eq(PsAppInfo::getAlipayAppId, history.getAppId())
                    .eq(PsAppInfo::getTemplateId, history.getTemplateId())
                    .one();
            if (Objects.isNull(appInfo)) {
                log.error("PsAppInfo(appId:{}) 不存在", history.getAppId());
                return;
            }

            AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
            AlipayOpenMiniExperienceQueryRequest request = new AlipayOpenMiniExperienceQueryRequest();
            AlipayOpenMiniExperienceQueryModel bizModel = new AlipayOpenMiniExperienceQueryModel();
            bizModel.setAppVersion(history.getAppVersion());
            bizModel.setBundleId("com.alipay.alipaywallet");
            request.setBizModel(bizModel);
            request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
            try {
                AlipayOpenMiniExperienceQueryResponse response = alipayClient.execute(request);
                log.info("AlipayOpenMiniExperienceQueryResponse:{}", JSONUtil.toJsonPrettyStr(response));
                if (response.isSuccess()) {
                    /**
                     * 体验版打包状态。状态枚举如下：
                     * expVersionPackged：体验版打包成功；
                     * expVersionPackaging：体验版打包中；
                     * notExpVersion：非体验版。
                     */
                    String status = response.getStatus();
                    if ("expVersionPackged".equalsIgnoreCase(status)) {
                        psAppOprateHistoryService.lambdaUpdate()
                                .eq(PsAppOprateHistory::getId, history.getId())
                                .set(PsAppOprateHistory::getExpQrCodeUrl, response.getExpQrCodeUrl())
                                .set(PsAppOprateHistory::getExperienceStatus, status)
                                .set(PsAppOprateHistory::getNeedRotation, 1)
                                .set(PsAppOprateHistory::getGmtModified, new Date())
                                .update();
                    }
                } else {
                    String subMsg = response.getSubMsg();
                    String subCode = response.getSubCode();
                    log.error("appId:{},subCode:{},subMsg:{}", history.getAppId(), subCode, subMsg);
                }
            } catch (Exception exception) {
                log.error("do AlipayOpenMiniExperienceQueryRequest error", exception);
            }
        } catch (Exception ex) {
            log.error("appId:{},处理异常", history.getAppId(), ex);
        }
    }
}
