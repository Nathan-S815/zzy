package com.nuwa.zeus.start.api.controller.common;


import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.zeus.dto.clientobject.other.qry.AppApplyPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.database.other.entity.AppApply;
import com.nuwa.infrastructure.zeus.database.other.param.AppApplyPageParam;
import com.nuwa.infrastructure.zeus.database.other.service.AppApplyService;
import com.nuwa.zeus.start.api.controller.common.param.CreateAppApplyParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Controller
@RequestMapping("app/apply")
@Api(tags = {"应用试用管理"})
public class AppApplyController {

    @Autowired
    private AppApplyService appApplyService;

    @ApiOperation(value = "新增申请")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse add(@RequestBody @Valid CreateAppApplyParam param) {
        AppApply appApply = new AppApply();
        BeanUtil.copyProperties(param, appApply);
        boolean insert = appApply.insert();
        if (insert) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9865", "新增版本失败");
    }

    @ApiOperation(value = "根据ID查询")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<AppApply> getById(@PathVariable("id") Long id) {
        AppApply appApply = appApplyService.getById(id);
        return SingleResponse.of(appApply);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<AppApply>> page(AppApplyPageQry cmd, UserAware userAware) {
        IPage<AppApply> appApplyIPage = appApplyService.paginateByParam(new AppApplyPageParam(cmd));
        return SingleResponse.of(appApplyIPage);
    }
}
