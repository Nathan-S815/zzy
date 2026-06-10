package com.nuwa.ticket.start.api.controller.activity;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.activity.*;
import com.nuwa.app.ticket.command.activity.query.*;
import com.nuwa.client.ticket.dto.clientobject.activity.*;
import com.nuwa.client.ticket.dto.clientobject.activity.qry.*;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
@RequestMapping("/user/cultureActivity")
public class UserActivityController {

    @Autowired
    private CreateActivityApplyCmdExe createActivityApplyCmdExe;
    @Autowired
    private CreateActivityApplyV2CmdExe createActivityApplyV2CmdExe;
    @Autowired
    private ActivityQryExe activityQryExe;
    @Autowired
    private ActivityUserPageQryExe activityUserPageQryExe;
    @Autowired
    private GetCultureActivityApplyByUserQryExe getCultureActivityApplyByUserQryExe;
    @Autowired
    private GetUserCultureActivityApplyCmdExe getUserCultureActivityApplyCmdExe;
    @Autowired
    private OffUserCultureActivityApplyCmdExe offUserCultureActivityApplyCmdExe;

    @Autowired
    private ActivityUserV2PageQryExe activityUserV2PageQryExe;

    @ApiOperation(value = "报名")
    @PostMapping(value = "/apply")
    public SingleResponse<?> add(@RequestBody CreateActivityApplyCmd cmd, UserAware userAware){
        return createActivityApplyCmdExe.execute(cmd);
    }

    @ApiOperation(value = "报名V2")
    @PostMapping(value = "/applyV2")
    public SingleResponse<?> add(@RequestBody CreateActivityApplyV2Cmd cmd){
        return createActivityApplyV2CmdExe.execute(cmd);
    }

    @ApiOperation(value = "根据Id查询")
    @GetMapping(value = "/getById")
    public SingleResponse<?> getById(CultureActivityQry cmd, UserAware userAware){
        return activityQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询")
    @GetMapping(value = "/page")
    public SingleResponse<?> page(UserCultureActivityPageQry cmd, UserAware userAware){
        return activityUserPageQryExe.execute(cmd);
    }

    @ApiOperation(value = "分页查询V2")
    @GetMapping(value = "/pageV2")
    public SingleResponse<?> page(UserCultureActivityV2PageQry cmd){
        return activityUserV2PageQryExe.execute(cmd);
    }

    @ApiOperation(value = "用户活动报名数据分页查询")
    @GetMapping(value = "/pageUser")
    public SingleResponse<?> pageUser(GetCultureActivityApplyByUserQry cmd, UserAware userAware){
        return getCultureActivityApplyByUserQryExe.execute(cmd);
    }

    @ApiOperation(value = "根据id获取用户报名详情")
    @GetMapping(value = "/getUserCultureActivityApplyById")
    public SingleResponse<?> getUserCultureActivityApplyById(GetUserCultureActivityApplyCmd cmd, UserAware userAware){
        return getUserCultureActivityApplyCmdExe.execute(cmd);
    }
    @ApiOperation(value = "C端取消活动报名")
    @PostMapping(value = "/offUserCultureActivityApply")
    public SingleResponse<?> offUserCultureActivityApply(OffUserCultureActivityApplyCmd cmd, UserAware userAware){
        return offUserCultureActivityApplyCmdExe.execute(cmd);
    }

}
