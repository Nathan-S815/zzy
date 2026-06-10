package com.nuwa.ticket.start.api.pubsystem;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alipay.api.AlipayApiException;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppOprateHistory;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppOprateHistoryService;
import com.nuwa.ticket.start.api.pubsystem.notify.AuditPassedNotify;
import com.nuwa.ticket.start.api.pubsystem.notify.AuditRejectedNotify;
import com.nuwa.ticket.start.api.pubsystem.notify.CommerceDataScenicAuditNotify;
import com.nuwa.ticket.start.api.pubsystem.notify.OpenAppAuthNotify;
import com.nuwa.ticket.start.api.pubsystem.service.BackToDevelopService;
import com.nuwa.ticket.start.api.pubsystem.util.GatewayDevelopKit;
import com.nuwa.ticket.start.api.pubsystem.util.RequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@Api(tags = {"支付宝-消息订阅"})
public class AlipayNotifyController {

    @Autowired
    private PsAppInfoService psAppInfoService;

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsAppOprateHistoryService psAppOprateHistoryService;

    @Autowired
    private BackToDevelopService backToDevelopService;

    @ApiOperation(value = "消息订阅")
    @RequestMapping(value = {"gateway.do"}/*, produces = "application/xml;charset=gbk"*/, method = RequestMethod.POST)
    @ResponseBody
    public String messageProcess(HttpServletRequest request) throws AlipayApiException {
        Map<String, String> params = RequestUtil.getRequestParams(request);
        log.info("消息订阅:{}", JSONUtil.toJsonStr(params));
        if (params.containsKey("service") && params.get("service").equalsIgnoreCase("alipay.service.check")) {
            log.info("开发者模式验证,biz_content:{}", params.get("biz_content"));
            return GatewayDevelopKit.doCheck(params);
        }
        String notifyType = params.get("notify_type");
        log.info("notify_type:{}", notifyType);
        if ("servicemarket_order_notify".equalsIgnoreCase(notifyType)) {
            log.info("servicemarket_order_notify");
        } else if ("open_app_auth_notify".equalsIgnoreCase(notifyType)) {
            log.info(">>>> open_app_auth_notify");
            return openAppAuthMessageProcess(params);
        } else {
            String msgMethod = params.get("msg_method");
            if ("alipay.open.mini.version.audit.passed".equalsIgnoreCase(msgMethod)) {
                return auditPassedMessageProcess(params);
            } else if ("alipay.open.mini.version.audit.rejected".equalsIgnoreCase(msgMethod)) {
                return auditRejectedMessageProcess(params);
            } else if ("alipay.commerce.data.scenic.audit".equalsIgnoreCase(msgMethod)) {
                return commerceDataScenicAuditMessageProcess(params);
            }
        }
        return "success";
    }

    private String openAppAuthMessageProcess(Map<String, String> params) {
        OpenAppAuthNotify appAuthNotify = BeanUtil.mapToBean(params, OpenAppAuthNotify.class, true, CopyOptions.create());
        String appId = appAuthNotify.getAppId();
        PsAlipayConfig appConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, appId).one();
        if (Objects.isNull(appConfig)) {
            log.error("PsAlipayConfig[appId:{}] can be not null", appId);
            return "fail";
        }

        String authAppId = appAuthNotify.getAuthAppId();
        OpenAppAuthNotify.BizDTO bizDTO = appAuthNotify.getBizDTO();
        OpenAppAuthNotify.BizDTO.DetailDTO detail = bizDTO.getDetail();
        String appAuthToken = detail.getAppAuthToken();
        Integer count = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, authAppId).count();
        if (count == 0) {
            PsAppInfo psAppInfo = new PsAppInfo();
            psAppInfo.setAlipayConfigId(appConfig.getId() + "");
            psAppInfo.setAlipayAppId(authAppId);
            psAppInfo.setAppStatus("init");
            //psAppInfo.setTemplateId(appConfig.getTemplateId());
            psAppInfo.setAppAuthToken(appAuthToken);
            psAppInfo.setAppName("未命名应用");
            psAppInfo.setGmtAuth(new Date());
            psAppInfo.setGmtCreate(new Date());
            boolean insert = psAppInfo.insert();
            if (insert) {
                return "success";
            }
        } else {
            boolean update = psAppInfoService.lambdaUpdate()
                    .set(PsAppInfo::getAppAuthTokenNew, appAuthToken)
                    .set(PsAppInfo::getGmtAuth, new Date())
                    .eq(PsAppInfo::getAlipayAppId, authAppId)
                    .update();
            if (update) {
                return "success";
            }
        }
        return "fail";
    }

    private String auditPassedMessageProcess(Map<String, String> params) {
        AuditPassedNotify appAuthNotify = BeanUtil.mapToBean(params, AuditPassedNotify.class, true, CopyOptions.create());
        String appId = appAuthNotify.getAppId();
        PsAlipayConfig appConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, appId).one();
        if (Objects.isNull(appConfig)) {
            log.error("PsAlipayConfig[appId:{}] can be not null", appId);
            return "fail";
        }
        AuditPassedNotify.BizDTO bizDTO = appAuthNotify.getBizDTO();
        String miniAppId = bizDTO.getMiniAppId();
        String miniAppVersion = bizDTO.getMiniAppVersion();

        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.lambdaQuery()
                .eq(PsAppOprateHistory::getAppId, miniAppId)
                .eq(PsAppOprateHistory::getAppVersion, miniAppVersion)
                .one();
        String jsonStr = psAppOprateHistory.getJson();
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
        String appName = jsonObject.getStr("appName");
        boolean update = psAppOprateHistoryService.lambdaUpdate()
                .set(PsAppOprateHistory::getAuditStatus, "passed")
                .set(PsAppOprateHistory::getGmtAudit, new Date())
                .set(PsAppOprateHistory::getAppName,appName )
                .eq(PsAppOprateHistory::getAppId, miniAppId)
                .eq(PsAppOprateHistory::getAppVersion, miniAppVersion)
                .eq(PsAppOprateHistory::getAuditStatus, "wait_audit")
                .update();
        if (update) {
            return "success";
        }
        return "fail";
    }

    private String auditRejectedMessageProcess(Map<String, String> params) {
        AuditRejectedNotify appAuthNotify = BeanUtil.mapToBean(params, AuditRejectedNotify.class, true, CopyOptions.create());
        String appId = appAuthNotify.getAppId();
        PsAlipayConfig appConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, appId).one();
        if (Objects.isNull(appConfig)) {
            log.error("PsAlipayConfig[appId:{}] can be not null", appId);
            return "fail";
        }
        AuditRejectedNotify.BizDTO bizDTO = appAuthNotify.getBizDTO();
        String miniAppId = bizDTO.getMiniAppId();
        String miniAppVersion = bizDTO.getMiniAppVersion();
        boolean update = psAppOprateHistoryService.lambdaUpdate()
                .set(PsAppOprateHistory::getAuditStatus, "rejected")
                .set(PsAppOprateHistory::getAuditRejectReason, bizDTO.getAuditReason())
                .set(PsAppOprateHistory::getGmtAudit, new Date())
                .eq(PsAppOprateHistory::getAppId, miniAppId)
                .eq(PsAppOprateHistory::getAppVersion, miniAppVersion)
                .update();
        if (update) {
            /*PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.lambdaQuery()
                    .eq(PsAppOprateHistory::getAppId, miniAppId)
                    .eq(PsAppOprateHistory::getAppVersion, miniAppVersion)
                    .one();
            boolean back = backToDevelopService.back(psAppOprateHistory);
            if (back) {
                return "success";
            }*/
            return "success";
        }
        return "fail";
    }

    private String commerceDataScenicAuditMessageProcess(Map<String, String> params) {
        CommerceDataScenicAuditNotify appAuthNotify = BeanUtil.mapToBean(params, CommerceDataScenicAuditNotify.class, true, CopyOptions.create());
        CommerceDataScenicAuditNotify.BizDTO bizDTO = appAuthNotify.getBizDTO();
        String appId = bizDTO.getScenicAppId();
       /* boolean update = alipayDataScenicService.lambdaUpdate()
                .set(AlipayDataScenic::getStatus, bizDTO.getAuditStatus())
                .set(AlipayDataScenic::getRemark, bizDTO.getAuditMsg())
                .set(AlipayDataScenic::getGmtModified, new Date())
                .eq(AlipayDataScenic::getAlipayAppId, appId)
                .eq(AlipayDataScenic::getAlipayScenicId, bizDTO.getScenicId())
                .eq(AlipayDataScenic::getScenicId, bizDTO.getOuterIdZfb())
                .update();
        if (update) {
            return "success";
        }*/
        return "fail";
    }

}
