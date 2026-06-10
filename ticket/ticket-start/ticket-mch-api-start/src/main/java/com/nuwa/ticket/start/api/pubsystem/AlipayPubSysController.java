package com.nuwa.ticket.start.api.pubsystem;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.*;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.pubsystem.qry.PsAppInfoPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAppOprateHistory;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.param.PsAppInfoPageParam;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppInfoService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAppOprateHistoryService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.ticket.start.api.pubsystem.param.*;
import com.nuwa.ticket.start.api.pubsystem.service.BackToDevelopService;
import com.nuwa.ticket.start.api.pubsystem.util.AlipayClientWrap;
import com.nuwa.ticket.start.api.pubsystem.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/alipay/ps")
@Api(tags = {"支付宝-小程序发布系统"})
public class AlipayPubSysController {

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsAppInfoService psAppInfoService;

    @Autowired
    private PsAppOprateHistoryService psAppOprateHistoryService;

    @Autowired
    private BackToDevelopService backToDevelopService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @ApiOperation(value = "小程序分页")
    @RequestMapping(value = "/appPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<PsAppInfo>> page(PsAppInfoPageQry pageQry) {
        PsAppInfoPageParam pageParam = new PsAppInfoPageParam(pageQry);
        IPage<PsAppInfo> psAppInfoIPage = psAppInfoService.paginateByParam(pageParam);
        return SingleResponse.of(psAppInfoIPage);
    }

    @ApiOperation(value = "小程序详情")
    @RequestMapping(value = "/appInfoDetail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<PsAppInfo> appInfoDetail(@PathVariable("id") Long id) {
        PsAppInfo psAppInfo = psAppInfoService.getById(id);
        return SingleResponse.of(psAppInfo);
    }

    @ApiOperation(value = "小程序应用authToken")
    @RequestMapping(value = "/appInfo/{id}/usedNewAuthToken", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> usedNewAuthToken(@PathVariable("id") Long id) {
        PsAppInfo psAppInfo = psAppInfoService.getById(id);
        Assert.notNull(psAppInfo, "PsAppInfo 不存在");
        Assert.isTrue(StrUtil.isNotBlank(psAppInfo.getAppAuthTokenNew()), "authToken不存在");
        boolean update = psAppInfoService.lambdaUpdate().set(PsAppInfo::getAppAuthToken, psAppInfo.getAppAuthTokenNew()).set(PsAppInfo::getAppAuthTokenNew, null).set(PsAppInfo::getGmtAuth, new Date()).eq(PsAppInfo::getId, id).update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "操作失败");
    }

    @ApiOperation(value = "小程序修改authToken")
    @RequestMapping(value = "/appInfo/{id}/modifyAuthToken", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyAuthToken(@PathVariable("id") Long id, @RequestBody ModifyAuthTokenParam param) {
        PsAppInfo psAppInfo = psAppInfoService.getById(id);
        Assert.notNull(psAppInfo, "PsAppInfo 不存在");
        boolean update = psAppInfoService.lambdaUpdate().set(PsAppInfo::getAppAuthToken, param.getAppAuthToken()).set(PsAppInfo::getGmtAuth, new Date()).eq(PsAppInfo::getId, id).update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "操作失败");
    }

    @ApiOperation(value = "小程序选择模板")
    @RequestMapping(value = "/appInfo/{id}/chooseTemplate", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> chooseTemplate(@PathVariable("id") Long id, @RequestBody ChooseTemplateParam param) {
        PsAppInfo psAppInfo = psAppInfoService.getById(id);
        Assert.notNull(psAppInfo, "PsAppInfo 不存在");
        boolean update = psAppInfoService.lambdaUpdate().set(PsAppInfo::getTemplateId, param.getTemplateId()).set(PsAppInfo::getGmtModified, new Date()).eq(PsAppInfo::getId, id).update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9874", "操作失败");
    }

    @ApiOperation(value = "小程序版本信息")
    @RequestMapping(value = "/{id}/appVersionInfo", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<PsAppVersionInfoVO> appVersionInfo(@PathVariable("id") Long id) {
        PsAppVersionInfoVO versionInfoVO = new PsAppVersionInfoVO();
        PsAppInfo psAppInfo = psAppInfoService.getById(id);
        List<PsAppOprateHistory> historyList = psAppOprateHistoryService.lambdaQuery()
                .eq(PsAppOprateHistory::getAppId, psAppInfo.getAlipayAppId())
                .eq(PsAppOprateHistory::getTemplateId, psAppInfo.getTemplateId())
                .orderByDesc(PsAppOprateHistory::getGmtCreate)
                .list();
        List<DevelopVO> developVOList = historyList.stream().filter(x -> {
            String publishStatus = x.getPublishStatus();
            if (StrUtil.isNotBlank(publishStatus)) {
                return false;
            }
            String auditStatus = x.getAuditStatus();
            return "cancel".equalsIgnoreCase(auditStatus) || !StrUtil.isNotBlank(auditStatus);
        }).map(x -> {
            DevelopVO vo = new DevelopVO();
            BeanUtil.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        versionInfoVO.setDevelopItems(developVOList);

        List<AuditVO> auditVOList = historyList.stream().filter(x -> {
            String publishStatus = x.getPublishStatus();
            if (StrUtil.isNotBlank(publishStatus)) {
                return false;
            }
            String auditStatus = x.getAuditStatus();
            return "rejected".equalsIgnoreCase(x.getAuditStatus()) || "wait_audit".equalsIgnoreCase(x.getAuditStatus()) || "passed".equalsIgnoreCase(x.getAuditStatus());
        }).map(x -> {
            AuditVO vo = new AuditVO();
            BeanUtil.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        versionInfoVO.setAuditItems(auditVOList);

        List<PublishVO> publishVOList = historyList.stream().filter(x -> {
            String publishStatus = x.getPublishStatus();
            return "online".equalsIgnoreCase(publishStatus) || "offline".equalsIgnoreCase(publishStatus);
        }).map(x -> {
            PublishVO vo = new PublishVO();
            BeanUtil.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        versionInfoVO.setPublishItems(publishVOList);

        return SingleResponse.of(versionInfoVO);
    }


    @ApiOperation(value = "小程序基于模板上传版本")
    @RequestMapping(value = "/versionUpload", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> versionUpload(@RequestBody VersionUploadParam param/*, UserAware userAware*/) {
        log.info("versionUpload:{}", JSONUtil.toJsonPrettyStr(param));

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, param.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, param.getAppId()).eq(PsAppInfo::getTemplateId, param.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        PsAppOprateHistory psAppOprateHistory = new PsAppOprateHistory();
        psAppOprateHistory.setAppId(appInfo.getAlipayAppId());
        psAppOprateHistory.setAppVersion(param.getAppVersion());
        psAppOprateHistory.setAppName(appInfo.getAppName());
        psAppOprateHistory.setBuildStatus("init");
        psAppOprateHistory.setDevelopStatus("init");
        psAppOprateHistory.setGmtBuild(new Date());
        psAppOprateHistory.setTemplateId(appInfo.getTemplateId());
        psAppOprateHistory.setTemplateVersion(param.getTemplateVersion());
        psAppOprateHistory.setGmtCreate(new Date());
        psAppOprateHistory.setTemplateVersion(param.getTemplateVersion());
        boolean insert = psAppOprateHistory.insert();
        if (insert) {
            AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
            AlipayOpenMiniVersionUploadRequest request = new AlipayOpenMiniVersionUploadRequest();
            AlipayOpenMiniVersionUploadModel bizModel = new AlipayOpenMiniVersionUploadModel();
            bizModel.setAppVersion(param.getAppVersion());
            bizModel.setTemplateVersion(param.getTemplateVersion());
            bizModel.setTemplateId(param.getTemplateId());
            bizModel.setBundleId("com.alipay.alipaywallet");
            JSONObject extJson = new JSONObject();
            extJson.putOpt("extEnable", true);
            extJson.putOpt("appId", appInfo.getAlipayAppId());
            extJson.putOpt("appName", appInfo.getAppName());
            extJson.putOpt("appVersion", param.getAppVersion());
            bizModel.setExt(JSONUtil.toJsonStr(extJson));
            request.setBizModel(bizModel);
            request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
            try {
                AlipayOpenMiniVersionUploadResponse response = alipayClient.execute(request);
                log.info("AlipayOpenMiniVersionUploadResponse:{}", JSONUtil.toJsonPrettyStr(response));
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
                    Integer historyId = psAppOprateHistory.getId();
                    psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getNeedRotation, response.getNeedRotation().equalsIgnoreCase("true") ? 0 : 1).set(PsAppOprateHistory::getBuildStatus, createStatus).set(PsAppOprateHistory::getGmtModified, new Date()).update();
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
                    psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getBuildStatus, "fail").set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                    return SingleResponse.buildFailure("4200", subMsg);
                }
            } catch (Exception exception) {
                log.error("do AlipayOpenMiniVersionUpload error", exception);
                return SingleResponse.buildFailure("4200", "未知异常");
            }
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序提交审核")
    @RequestMapping(value = "/auditApply", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditApply(@RequestBody @Valid AuditApplyParam param/*, UserAware userAware*/) {
        log.info("auditApply:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        Assert.isTrue(psAppOprateHistory.getDevelopStatus().equalsIgnoreCase("develop"), "开发中的版本才能操作");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        String json = psAppOprateHistory.getJson();
        JSONObject jsonObject = new JSONObject();
        if (StrUtil.isNotBlank(json)) {
            jsonObject = JSONUtil.parseObj(json);
        }
        jsonObject.putOpt("versionDesc", param.getVersionDesc());
        jsonObject.putOpt("regionType", "CHINA");
        jsonObject.putOpt("appDesc", param.getAppDesc());
        jsonObject.putOpt("appName", param.getAppName());
        jsonObject.putOpt("appSlogan", param.getAppSlogan());
        jsonObject.putOpt("servicePhone", param.getServicePhone());

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionAuditApplyRequest request = new AlipayOpenMiniVersionAuditApplyRequest();
        request.setServiceEmail("hehu@zhongzhiyou.com");
        request.setVersionDesc(param.getVersionDesc());
        request.setRegionType("CHINA");
        request.setAppVersion(psAppOprateHistory.getAppVersion());
        request.setAppDesc(param.getAppDesc());
        request.setAppName(param.getAppName());
        request.setAppSlogan(param.getAppSlogan());
        request.setServicePhone(param.getServicePhone());
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionAuditApplyResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionAuditApplyResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getId, psAppOprateHistory.getId())
                        .set(PsAppOprateHistory::getAuditStatus, "wait_audit").set(PsAppOprateHistory::getDevelopStatus, "auditing")
                        .set(PsAppOprateHistory::getAppName, param.getAppName())
                        .set(PsAppOprateHistory::getGmtModified, new Date())
                        .set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject))
                        .update();
            } else {
                String subMsg = response.getSubMsg();
                String subCode = response.getSubCode();
                Integer historyId = psAppOprateHistory.getId();
                jsonObject.putOpt("subMsg", subMsg);
                jsonObject.putOpt("subCode", subCode);
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getId, historyId)
                        .set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject))
                        .set(PsAppOprateHistory::getGmtModified, new Date())
                        .update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionAuditApplyRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序撤销审核")
    @RequestMapping(value = "/auditCancel", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditCancel(@RequestBody @Valid AuditCancelParam param/*, UserAware userAware*/) {
        log.info("auditCancel:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        Assert.isTrue(psAppOprateHistory.getAuditStatus().equalsIgnoreCase("wait_audit"), "当前状态不允许撤销审核");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionAuditCancelRequest request = new AlipayOpenMiniVersionAuditCancelRequest();
        AlipayOpenMiniVersionAuditCancelModel bizModel = new AlipayOpenMiniVersionAuditCancelModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionAuditCancelResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionAuditCancelResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, psAppOprateHistory.getId()).set(PsAppOprateHistory::getAuditStatus, "cancel").set(PsAppOprateHistory::getDevelopStatus, "develop").set(PsAppOprateHistory::getGmtModified, new Date()).update();
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
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionAuditCancelRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序上架")
    @RequestMapping(value = "/versionOnline", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> versionOnline(@RequestBody @Valid VersionOnlineParam param/*, UserAware userAware*/) {
        log.info("versionOnline:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        Assert.isTrue(psAppOprateHistory.getAuditStatus().equalsIgnoreCase("passed"), "审核通过的版本才能操作");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery()
                .eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId())
                .eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId())
                .one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionOnlineRequest request = new AlipayOpenMiniVersionOnlineRequest();
        AlipayOpenMiniVersionOnlineModel bizModel = new AlipayOpenMiniVersionOnlineModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionOnlineResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionOnlineResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                Date now = new Date();
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getId, psAppOprateHistory.getId())
                        .set(PsAppOprateHistory::getPublishStatus, "online")
                        .set(PsAppOprateHistory::getGmtPublish, now)
                        .update();

                String json = psAppOprateHistory.getJson();
                JSONObject jsonObject = new JSONObject();
                if (StrUtil.isNotBlank(json)) {
                    jsonObject = JSONUtil.parseObj(json);
                }
                String appName = jsonObject.getStr("appName");
                psAppOprateHistoryService.lambdaUpdate()
                        .eq(PsAppOprateHistory::getAppId, psAppOprateHistory.getAppId())
                        .set(PsAppOprateHistory::getAppName, appName)
                        .update();

                psAppInfoService.lambdaUpdate()
                        .set(PsAppInfo::getGmtPublish, now)
                        .set(PsAppInfo::getGmtModified, now)
                        .set(PsAppInfo::getAppName, appName)
                        .set(PsAppInfo::getAppStatus, "online")
                        .set(PsAppInfo::getAppVersion, psAppOprateHistory.getAppVersion())
                        .eq(PsAppInfo::getId, appInfo.getId())
                        .update();

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
                        .set(PsAppOprateHistory::getJson,
                                JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionOnlineRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序下架")
    @RequestMapping(value = "/versionOffline", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> versionOffline(@RequestBody @Valid VersionOfflineParam param/*, UserAware userAware*/) {
        log.info("versionOffline:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        Assert.isTrue(psAppOprateHistory.getPublishStatus().equalsIgnoreCase("online"), "已上架的版本才能操作");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionOfflineRequest request = new AlipayOpenMiniVersionOfflineRequest();
        AlipayOpenMiniVersionOfflineModel bizModel = new AlipayOpenMiniVersionOfflineModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionOfflineResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionOfflineResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, psAppOprateHistory.getId()).set(PsAppOprateHistory::getPublishStatus, "offline").set(PsAppOprateHistory::getGmtPublish, new Date()).update();

                psAppInfoService.lambdaUpdate().set(PsAppInfo::getAppVersion, psAppOprateHistory.getAppVersion()).set(PsAppInfo::getAppStatus, "offline").eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).update();
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
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionOfflineRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "退回开发")
    @RequestMapping(value = "/backDevelop", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> backDevelop(@RequestBody @Valid BackDevelopParam param/*, UserAware userAware*/) {
        log.info("versionOffline:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        boolean back = backToDevelopService.back(psAppOprateHistory);
        if (!back) {
            return SingleResponse.buildFailure("9874", "退回开发操作失败");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序回滚")
    @RequestMapping(value = "/versionRollback", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> versionRollback(@RequestBody @Valid VersionRollbackParam param/*, UserAware userAware*/) {
        log.info("versionRollback:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        Assert.isTrue(psAppOprateHistory.getPublishStatus().equalsIgnoreCase("online"), "已上架的版本才能操作");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniVersionRollbackRequest request = new AlipayOpenMiniVersionRollbackRequest();
        AlipayOpenMiniVersionRollbackModel bizModel = new AlipayOpenMiniVersionRollbackModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniVersionRollbackResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniVersionRollbackResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, psAppOprateHistory.getId()).set(PsAppOprateHistory::getPublishStatus, "offline").set(PsAppOprateHistory::getGmtPublish, new Date()).update();

                PsAppOprateHistory preAppVersion = psAppOprateHistoryService.lambdaQuery().eq(PsAppOprateHistory::getAppId, psAppOprateHistory.getAppId()).eq(PsAppOprateHistory::getPublishStatus, "online").ne(PsAppOprateHistory::getId, psAppOprateHistory.getId()).orderByDesc(PsAppOprateHistory::getAppVersion).last("limit 1").one();
                if (Objects.nonNull(preAppVersion)) {
                    psAppInfoService.lambdaUpdate().set(PsAppInfo::getAppVersion, preAppVersion.getAppVersion()).set(PsAppInfo::getAppStatus, "online").set(PsAppInfo::getGmtPublish, new Date()).eq(PsAppInfo::getId, preAppVersion.getId()).update();
                }
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
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniVersionRollbackRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "小程序生成体验版")
    @RequestMapping(value = "/expCreate", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> expCreate(@RequestBody @Valid ExpCreateParam param/*, UserAware userAware*/) {
        log.info("expCreate:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppOprateHistory psAppOprateHistory = psAppOprateHistoryService.getById(param.getId());
        boolean auditPassed = "passed".equalsIgnoreCase(psAppOprateHistory.getAuditStatus());
        if (auditPassed) {
            return SingleResponse.buildFailure("8923", "请在开发中或审核中版本设置体验版");
        }

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
        Assert.notNull(alipayConfig, "模板小程序配置不存在");

        PsAppInfo appInfo = psAppInfoService.lambdaQuery().eq(PsAppInfo::getAlipayAppId, psAppOprateHistory.getAppId()).eq(PsAppInfo::getTemplateId, psAppOprateHistory.getTemplateId()).one();
        Assert.notNull(appInfo, "小程序不存在");

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenMiniExperienceCreateRequest request = new AlipayOpenMiniExperienceCreateRequest();
        AlipayOpenMiniExperienceCreateModel bizModel = new AlipayOpenMiniExperienceCreateModel();
        bizModel.setAppVersion(psAppOprateHistory.getAppVersion());
        bizModel.setBundleId("com.alipay.alipaywallet");
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenMiniExperienceCreateResponse response = alipayClient.execute(request);
            log.info("AlipayOpenMiniExperienceCreateResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, psAppOprateHistory.getId()).set(PsAppOprateHistory::getExperienceStatus, "wait_exp").set(PsAppOprateHistory::getNeedRotation, 0).set(PsAppOprateHistory::getGmtModified, new Date()).update();

                psAppOprateHistoryService.lambdaUpdate().ne(PsAppOprateHistory::getId, psAppOprateHistory.getId()).eq(PsAppOprateHistory::getAppId, psAppOprateHistory.getAppId()).set(PsAppOprateHistory::getExperienceStatus, null).set(PsAppOprateHistory::getExpQrCodeUrl, null).update();
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
                psAppOprateHistoryService.lambdaUpdate().eq(PsAppOprateHistory::getId, historyId).set(PsAppOprateHistory::getJson, JSONUtil.toJsonStr(jsonObject)).set(PsAppOprateHistory::getGmtModified, new Date()).update();
                return SingleResponse.buildFailure("4200", subMsg);
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniExperienceCreateRequest error", exception);
            return SingleResponse.buildFailure("4200", "未知异常");
        }
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "应用查询成员列表")
    @RequestMapping(value = "/appMembersQuery", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> appMembersQuery(@RequestBody @Valid AppMembersQueryParam param/*, UserAware userAware*/) {
        log.info("expCreate:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppInfo appInfo = psAppInfoService.getById(param.getId());
        Assert.notNull(appInfo, "小程序不存在");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenAppMembersQueryRequest request = new AlipayOpenAppMembersQueryRequest();
        AlipayOpenAppMembersQueryModel bizModel = new AlipayOpenAppMembersQueryModel();
        bizModel.setRole(param.getRole());
        request.setBizModel(bizModel);
        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenAppMembersQueryResponse response = alipayClient.execute(request);
            log.info("AlipayOpenAppMembersQueryResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                OpenAppMembersQueryVO vo = new OpenAppMembersQueryVO();
                BeanUtil.copyProperties(response, vo);
                return SingleResponse.of(vo);
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniExperienceCreateRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }

    @ApiOperation(value = "应用添加成员")
    @RequestMapping(value = "/appMembersCreate", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> appMembersCreate(@RequestBody @Valid AppMembersCreateParam param/*, UserAware userAware*/) {
        log.info("expCreate:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppInfo appInfo = psAppInfoService.getById(param.getId());
        Assert.notNull(appInfo, "小程序不存在");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenAppMembersCreateRequest request = new AlipayOpenAppMembersCreateRequest();
        AlipayOpenAppMembersCreateModel bizModel = new AlipayOpenAppMembersCreateModel();
        bizModel.setRole(param.getRole());
        bizModel.setLogonId(param.getLogonId());
        request.setBizModel(bizModel);

        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenAppMembersCreateResponse response = alipayClient.execute(request);
            log.info("AlipayOpenAppMembersCreateResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                return SingleResponse.buildSuccess();
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniExperienceCreateRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }

    @ApiOperation(value = "应用删除成员")
    @RequestMapping(value = "/appMembersDelete", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> appMembersDelete(@RequestBody @Valid AppMembersDeleteParam param/*, UserAware userAware*/) {
        log.info("expCreate:{}", JSONUtil.toJsonPrettyStr(param));
        PsAppInfo appInfo = psAppInfoService.getById(param.getId());
        Assert.notNull(appInfo, "小程序不存在");

        PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, appInfo.getTemplateId()).one();
        Assert.notNull(psTemplateInfo, "模板不存在");

        PsAlipayConfig alipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();

        AlipayClient alipayClient = AlipayClientWrap.getAlipayClient(alipayConfig);
        AlipayOpenAppMembersDeleteRequest request = new AlipayOpenAppMembersDeleteRequest();
        AlipayOpenAppMembersDeleteModel bizModel = new AlipayOpenAppMembersDeleteModel();
        bizModel.setRole(param.getRole());
        bizModel.setUserId(param.getUserId());
        request.setBizModel(bizModel);

        request.putOtherTextParam("app_auth_token", appInfo.getAppAuthToken());
        try {
            AlipayOpenAppMembersDeleteResponse response = alipayClient.execute(request);
            log.info("AlipayOpenAppMembersDeleteResponse:{}", JSONUtil.toJsonPrettyStr(response));
            if (response.isSuccess()) {
                return SingleResponse.buildSuccess();
            } else {
                return SingleResponse.buildFailure("0987", response.getSubMsg());
            }
        } catch (Exception exception) {
            log.error("do AlipayOpenMiniExperienceCreateRequest error", exception);
        }
        return SingleResponse.buildFailure("0987", "请求异常");
    }

    @ApiOperation(value = "获取小程序模板列表")
    @RequestMapping(value = "/listAppTemplate", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> listAppTemplate(UserAware userAware) {
        List<PsTemplateInfo> list = psTemplateInfoService.lambdaQuery().list();
        return SingleResponse.of(list);
    }

}
