package com.nuwa.ticket.start.api.controller.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.activity.*;
import com.nuwa.app.ticket.command.activity.query.ActivityApplyPageQryExe;
import com.nuwa.app.ticket.command.activity.query.ActivityApplyQryExe;
import com.nuwa.client.ticket.dto.clientobject.activity.*;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityApplyPageQry;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityApplyQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * ExampleOrderController
 *
 * @author hy
 * @date 2021/4/15 14:09
 * @since 1.0.0
 */
@Api(tags = {"文化活动报名"})
@Slf4j
@RestController
@RequestMapping("/merchant/cultureActivityApply")
public class MchActivityApplyController {

    @Autowired
    private EditActivityApplyCmdExe editActivityApplyCmdExe;

    @Autowired
    private DeleteActivityApplyCmdExe deleteActivityApplyCmdExe;

    @Autowired
    private ActivityApplyQryExe activityApplyQryExe;

    @Autowired
    private ActivityApplyPageQryExe activityApplyPageQryExe;

    @ApiOperation(value = "更新数据")
    @PostMapping(value = "/update")
    public SingleResponse<?> update(@RequestBody EditActivityApplyCmd cmd, UserAware userAware){
        return editActivityApplyCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/del")
    @RequiresPermissions("cultureActivityEnter:del")
    public SingleResponse<?> del(DeleteActivityApplyCmd cmd, UserAware userAware){
        return deleteActivityApplyCmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据ID查询")
    @GetMapping(value = "/getById")
    @RequiresPermissions("cultureActivityEnter:info")
    public SingleResponse<?> getById(CultureActivityApplyQry cmd, UserAware userAware){
        return activityApplyQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(CultureActivityApplyPageQry cmd, UserAware userAware){
        return activityApplyPageQryExe.execute(cmd);
    }

}
