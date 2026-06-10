package com.nuwa.zeus.start.api.controller.merchant;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.ssh.JschRuntimeException;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.mzt.logapi.starter.annotation.LogRecordAnnotation;
import com.nuwa.app.zeus.command.mch.CreateMerchantAppSubmitUrlCmdExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantAppTreeQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantCoreAppListQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantNavigationListQryExe;
import com.nuwa.app.zeus.command.mch.qry.GetMerchantOpenedSubAppQryExe;
import com.nuwa.app.zeus.command.mch.vo.SortAppInfo;
import com.nuwa.app.zeus.vo.AppTree;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantAppSubmitUrlCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetAppTreeQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantNavigationListQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetMerchantSubAppQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantAppServer;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppServerService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import com.nuwa.zeus.start.api.constants.LogCategoryType;
import com.nuwa.zeus.start.api.constants.LogRecordType;
import com.nuwa.zeus.start.api.controller.merchant.param.CheckAppServerStatusParam;
import com.nuwa.zeus.start.api.controller.merchant.param.GetUserAppGroupListParam;
import com.nuwa.zeus.start.api.controller.merchant.param.UpdateAppOrderNumParam;
import com.nuwa.zeus.start.api.controller.merchant.vo.CheckAppServerStatusVO;
import com.nuwa.zeus.start.api.controller.merchant.vo.InnerAppGroupVO;
import com.nuwa.zeus.start.api.controller.merchant.vo.MerchantAppListVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * MerchantAppController
 *
 * @author hy
 * @date 2021/6/9 14:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/app")
@Api(tags = {"商户应用管理模块"})
public class MerchantAppController {

    @Autowired
    private GetMerchantAppTreeQryExe getMerchantAppTreeQryExe;

    @Autowired
    private GetMerchantOpenedSubAppQryExe getMerchantOpenedSubAppQryExe;

    @Autowired
    private GetMerchantNavigationListQryExe getMerchantNavigationListQryExe;

    @Autowired
    private GetMerchantCoreAppListQryExe getMerchantCoreAppListQryExe;

    @Autowired
    private CreateMerchantAppSubmitUrlCmdExe createMerchantAppSubmitUrlCmdExe;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private MerchantAppService merchantAppService;

    @Autowired
    private MerchantAppServerService merchantAppServerService;

    @Autowired
    private AppInfoService appInfoService;

    @ApiOperation(value = "获取商户 已开通应用Tree")
    @RequestMapping(value = "tree", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<AppTree>> treeAppList(@Validated GetAppTreeQry cmd, UserAware userAware) {
        return getMerchantAppTreeQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取应用[已开通]子应用列表")
    @RequestMapping(value = "subApp/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<SortAppInfo>> subAppList(@Validated GetMerchantSubAppQry cmd, UserAware userAware) {
        return getMerchantOpenedSubAppQryExe.execute(cmd);
    }

    @ApiOperation(value = "修改商户应用排序")
    @RequestMapping(value = "updateAppOrderNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> updateAppOrderNum(@Validated @RequestBody UpdateAppOrderNumParam param, UserAware userAware) {
        boolean update = merchantAppService.lambdaUpdate()
                .set(MerchantApp::getOrderNum, param.getOrderNum())
                .set(MerchantApp::getUpdateTime, new Date())
                .eq(MerchantApp::getId, param.getId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "修改排序失败");
    }

    @ApiOperation(value = "获取商户导航列表")
    @RequestMapping(value = "subApp/app", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GetMerchantNavigationListQryExe.MerchantAppVO>> openAppList(@Validated GetMerchantNavigationListQry cmd, UserAware userAware) {
        return getMerchantNavigationListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取商户已开通核心应用列表")
    @RequestMapping(value = "list/coreApp", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<GetMerchantCoreAppListQryExe.MerchantAppVO>> listCoreApp(@Validated GetMerchantNavigationListQry cmd, UserAware userAware) {
        return getMerchantCoreAppListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取商户系统应用列表")
    @RequestMapping(value = "listSystemApp", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MerchantAppListVO>> listSystemApp(@Validated GetMerchantNavigationListQry cmd, UserAware userAware) {
        List<MerchantApp> listApp = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getParentAppId, -1)
                .eq(MerchantApp::getMerchantId, userAware.getMchId())
                .orderByAsc(MerchantApp::getOrderNum)
                .list();
        List<MerchantAppListVO> merchantAppListVOList = listApp.stream().map(x -> {
            AppInfo appInfo = appInfoService.getById(x.getAppId());
            if (appInfo.getStatus().equals(0)) {
                return null;
            }
            MerchantAppListVO vo = new MerchantAppListVO();
            vo.setAppName(appInfo.getAppName());
            vo.setAppId(appInfo.getId());
            vo.setId(x.getId());
            vo.setProvider(appInfo.getProvider());
            vo.setAppType(appInfo.getAppType());
            vo.setOrderNum(x.getOrderNum());
            vo.setSsh(appInfo.getSsh());
            return vo;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        return SingleResponse.of(merchantAppListVOList);
    }

    @ApiOperation(value = "获取商户子应用列表")
    @RequestMapping(value = "listSubApp", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MerchantAppListVO>> listSubApp(@Validated GetMerchantSubAppQry cmd, UserAware userAware) {
        List<MerchantApp> listApp = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getParentAppId, cmd.getAppId())
                .eq(MerchantApp::getMerchantId, userAware.getMchId())
                .orderByAsc(MerchantApp::getOrderNum)
                .list();
        List<MerchantAppListVO> merchantAppListVOList = listApp.stream().map(x -> {
            AppInfo appInfo = appInfoService.getById(x.getAppId());
            MerchantAppListVO vo = new MerchantAppListVO();
            vo.setAppName(appInfo.getAppName());
            vo.setAppId(appInfo.getId());
            vo.setId(x.getId());
            vo.setProvider(appInfo.getProvider());
            vo.setAppType(appInfo.getAppType());
            vo.setOrderNum(x.getOrderNum());
            vo.setSsh(appInfo.getSsh());
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(merchantAppListVOList);
    }

    @LogRecordAnnotation(fail = "修改应用链接失败，原因：「{{#_errorMsg}}」", category = LogCategoryType.PLAT, success = "修改应用链接成功", prefix = LogRecordType.MERCHANT_APP, bizNo = "{{#cmd.id}}", detail = "{{#cmd.toJson()}}")
    @ApiOperation(value = "修改商户应用app登录提交链接")
    @RequestMapping(value = "editMerchantAppSubmitUrl", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse editMerchantAppSubmitUrl(@Validated @RequestBody CreateMerchantAppSubmitUrlCmd cmd, UserAware userAware) {
        return SingleResponse.of(createMerchantAppSubmitUrlCmdExe.execute(cmd));
    }

    @ApiOperation(value = "检测应用服务状态")
    @RequestMapping(value = "checkAppServerStatus", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<CheckAppServerStatusVO> checkAppServerStatus(@Validated CheckAppServerStatusParam cmd, UserAware userAware) {
        CheckAppServerStatusVO vo = new CheckAppServerStatusVO();
        MerchantApp merchantApp = merchantAppService.lambdaQuery()
                .eq(MerchantApp::getAppId, cmd.getAppId())
                .eq(MerchantApp::getMerchantId, userAware.getMchId())
                .one();

        MerchantAppServer appServer = merchantAppServerService.lambdaQuery()
                .eq(MerchantAppServer::getMerchantId, cmd.getUserAware().getMchId())
                .eq(MerchantAppServer::getAppId, merchantApp.getAppId())
                .eq(MerchantAppServer::getParentAppId, merchantApp.getParentAppId())
                .one();
        if (Objects.nonNull(appServer)) {
            Integer status = appServer.getStatus();
            if (DateUtil.beginOfDay(new Date()).isAfter(DateUtil.endOfDay(appServer.getEndTime()))) {
                merchantAppServerService.lambdaUpdate()
                        .set(MerchantAppServer::getStatus, 0)
                        .eq(MerchantAppServer::getId, appServer.getId())
                        .update();
                appServer = merchantAppServerService.lambdaQuery()
                        .eq(MerchantAppServer::getMerchantId, cmd.getUserAware().getMchId())
                        .eq(MerchantAppServer::getAppId, merchantApp.getAppId())
                        .eq(MerchantAppServer::getParentAppId, merchantApp.getParentAppId())
                        .one();
            }


            if (status.equals(1)) {
                long startOfferDay = DateUtil.between(DateUtil.endOfDay(appServer.getStartTime()), DateUtil.beginOfDay(new Date()), DateUnit.DAY, false);
                if (startOfferDay < 0) {
                    vo.setStatus(5);
                } else {
                    long day = DateUtil.between(DateUtil.beginOfDay(new Date()), DateUtil.endOfDay(appServer.getEndTime()), DateUnit.DAY, false);
                    vo.setStatus(3);
                    day = day + 1;
                    vo.setValidDay(day);
                    if (day <= 7 && day > 0) {
                        vo.setStatus(4);
                    }
                }
            } else if (status.equals(0)) {
                vo.setStatus(2);
            }
        } else {
            vo.setStatus(1);
        }
        vo.setAppInfo(appInfoService.getById(cmd.getAppId()));
        return SingleResponse.of(vo);
    }

}
