package com.nuwa.zeus.start.api.controller.plat.app;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppUpdateLogPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.entity.AppUpdateLog;
import com.nuwa.infrastructure.zeus.database.app.param.AppUpdateLogPageParam;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.infrastructure.zeus.database.app.service.AppUpdateLogService;
import com.nuwa.zeus.start.api.controller.plat.app.param.CreateAppUpdateLogParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.GetAppUpdateLogByIdParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.ModifyAppUpdateLogParam;
import com.nuwa.zeus.start.api.controller.plat.app.param.RemovedAppUpdateLogParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Date;


@Controller
@RequestMapping("app/version")
@Api(tags = {"应用版本管理"})
public class AppUpdateController {

    @Autowired
    private AppUpdateLogService appUpdateLogService;

    @Autowired
    private AppInfoService appInfoService;

    @ApiOperation(value = "新增版本")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse add(@RequestBody @Valid CreateAppUpdateLogParam param, UserAware userAware) {
        AppUpdateLog appUpdateLog = new AppUpdateLog();
        BeanUtil.copyProperties(param, appUpdateLog);
        appUpdateLog.setUpgradeDate(new Date());
        boolean insert = appUpdateLog.insert();
        if (insert) {
            appInfoService.lambdaUpdate()
                    .set(AppInfo::getVersion, param.getVersion())
                    .set(AppInfo::getVersionName, param.getVersion())
                    .eq(AppInfo::getId, param.getAppId())
                    .update();
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9865", "新增版本失败");
    }

    @ApiOperation(value = "删除版本")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse delete(@RequestBody RemovedAppUpdateLogParam cmd, UserAware userAware) {
        boolean remove = appUpdateLogService.lambdaUpdate().in(AppUpdateLog::getId, cmd.getId()).remove();
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9865", "删除版本失败");
    }

    @ApiOperation(value = "修改版本")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse modify(@RequestBody ModifyAppUpdateLogParam param, UserAware userAware) {
        AppUpdateLog appUpdateLog = appUpdateLogService.getById(param.getId());
        BeanUtil.copyProperties(param, appUpdateLog);
        boolean update = appUpdateLog.updateById();
        if (update) {
            appInfoService.lambdaUpdate()
                    .set(AppInfo::getVersion, param.getVersion())
                    .set(AppInfo::getVersionName, param.getVersion())
                    .eq(AppInfo::getId, appUpdateLog.getAppId())
                    .update();
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9865", "修改版本失败");
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<AppUpdateLog> getById(GetAppUpdateLogByIdParam param, UserAware userAware) {
        AppUpdateLog appUpdateLog = appUpdateLogService.getById(param.getId());
        return SingleResponse.of(appUpdateLog);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<AppUpdateLog>> page(AppUpdateLogPageQry cmd, UserAware userAware) {
        IPage<AppUpdateLog> appUpdateLogIPage = appUpdateLogService.paginateByParam(new AppUpdateLogPageParam(cmd));
        return SingleResponse.of(appUpdateLogIPage);
    }
}
