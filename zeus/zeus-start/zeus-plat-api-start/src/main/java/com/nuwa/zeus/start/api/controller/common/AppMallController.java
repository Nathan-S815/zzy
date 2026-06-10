package com.nuwa.zeus.start.api.controller.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.util.CharsetUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.app.zeus.command.app.qry.AppMallListQryExe;
import com.nuwa.app.zeus.command.app.qry.AppMallPageListQryExe;
import com.nuwa.app.zeus.command.app.qry.AppMallQryExe;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppInfoPageQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallListQry;
import com.nuwa.client.zeus.dto.clientobject.app.qry.AppMallQry;
import com.nuwa.framework.shiro.starter.JWTToken;
import com.nuwa.infrastructure.zeus.database.app.entity.AppDependent;
import com.nuwa.infrastructure.zeus.database.app.entity.AppInfo;
import com.nuwa.infrastructure.zeus.database.app.service.AppDependentService;
import com.nuwa.infrastructure.zeus.database.app.service.AppInfoService;
import com.nuwa.zeus.start.api.aop.annotation.IgnoreAuth;
import com.nuwa.zeus.start.api.controller.common.param.SubAppMallListParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.xml.stream.events.Characters;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * AuthController
 *
 * @author hy
 * @date 2021/5/25 14:20
 * @since 1.0.0
 */
@Api(tags = {"应用商城"})
@RestController
@RequestMapping("/appMall")
public class AppMallController {

    @Autowired
    private AppMallListQryExe appMallListQryExe;

    @Autowired
    private AppMallQryExe appMallQryExe;

    @Autowired
    private AppMallPageListQryExe appMallPageListQryExe;

    @Autowired
    private AppInfoService appInfoService;

    @Autowired
    private AppDependentService appDependentService;

    @ApiOperation(value = "获取应用列表分页")
    @IgnoreAuth
    @GetMapping("/listPage")
    public SingleResponse<IPage<AppMallPageListQryExe.AppInfoVO>> getAppListPage(AppInfoPageQry cmd) throws Exception {
        return appMallPageListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取应用列表")
    @IgnoreAuth
    @GetMapping("/list")
    public SingleResponse<List<AppMallListQryExe.AppInfoVO>> getAppList(AppMallListQry cmd) throws Exception {
        return appMallListQryExe.execute(cmd);
    }

    @ApiOperation(value = "获取子应用")
    @IgnoreAuth
    @GetMapping("/listSubApp")
    public SingleResponse<List<AppMallListQryExe.AppInfoVO>> listSubApp(SubAppMallListParam cmd) throws Exception {
        List<AppDependent> appDependents = appDependentService.lambdaQuery().eq(AppDependent::getDependentAppId, cmd.getParentAppId()).list();
        List<Long> appIds = appDependents.stream().map(AppDependent::getAppId).collect(Collectors.toList());
        if (appIds.size() > 0) {
            List<AppInfo> appInfoList = appInfoService.lambdaQuery().eq(AppInfo::getStatus, 1).in(AppInfo::getId, appIds).list();
            List<AppMallListQryExe.AppInfoVO> appInfoVOList = appInfoList.stream().map(x -> {
                AppMallListQryExe.AppInfoVO vo = new AppMallListQryExe.AppInfoVO();
                BeanUtil.copyProperties(x, vo);
                return vo;
            }).collect(Collectors.toList());
            return SingleResponse.of(appInfoVOList);
        }
        return SingleResponse.of(new ArrayList<>());
    }

    @ApiOperation(value = "获取应用详情")
    @IgnoreAuth
    @GetMapping("/getById")
    public SingleResponse<AppMallQryExe.AppInfoVO> getById(AppMallQry cmd) throws Exception {
        return appMallQryExe.execute(cmd);
    }

}
