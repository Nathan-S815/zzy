package com.nuwa.ticket.start.api.controller.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.activity.CreateActivityCmdExe;
import com.nuwa.app.ticket.command.activity.DeleteActivityCmdExe;
import com.nuwa.app.ticket.command.activity.EditActivityCmdExe;
import com.nuwa.app.ticket.command.activity.query.ActivityPageQryExe;
import com.nuwa.app.ticket.command.activity.query.ActivityQryExe;
import com.nuwa.client.ticket.dto.clientobject.activity.CreateActivityCmd;
import com.nuwa.client.ticket.dto.clientobject.activity.DeleteActivityCmd;
import com.nuwa.client.ticket.dto.clientobject.activity.EditActivityCmd;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityPageQry;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.CultureActivityQry;
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
@Api(tags = {"文化活动"})
@Slf4j
@RestController
@RequestMapping("/merchant/cultureActivity")
public class MchActivityController {

    @Autowired
    private CreateActivityCmdExe createActivityCmdExe;

    @Autowired
    private DeleteActivityCmdExe deleteActivityCmdExe;

    @Autowired
    private EditActivityCmdExe editActivityCmdExe;

    @Autowired
    private ActivityQryExe activityQryExe;

    @Autowired
    private ActivityPageQryExe activityPageQryExe;

    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    @RequiresPermissions("cultureActivityManage:add")
    public SingleResponse<?> add(@RequestBody CreateActivityCmd cmd, UserAware userAware){
        return createActivityCmdExe.execute(cmd);
    }

    @ApiOperation(value = "删除数据")
    @GetMapping(value = "/del")
    @RequiresPermissions("cultureActivityManage:del")
    public SingleResponse<?> del(DeleteActivityCmd cmd, UserAware userAware){
        return deleteActivityCmdExe.execute(cmd);
    }

    @ApiOperation(value = "修改数据")
    @PostMapping(value = "/update")
    @RequiresPermissions("cultureActivityManage:edit")
    public SingleResponse<?> update(@RequestBody EditActivityCmd cmd, UserAware userAware){
        return editActivityCmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据Id查询")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(CultureActivityQry cmd, UserAware userAware){
        return activityQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(CultureActivityPageQry cmd, UserAware userAware){
        return activityPageQryExe.execute(cmd);
    }

}
