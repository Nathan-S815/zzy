package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.center.*;
import com.nuwa.app.ticket.command.callcenter.center.query.GetCallCenterQryExe;
import com.nuwa.app.ticket.command.callcenter.center.query.PageCallCenterQryExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.CreateCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.DelCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.GetCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.UpdateCallCenterCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.qry.CallCenterPageQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"呼叫中心"})
@Slf4j
@RestController
@RequestMapping("/callCenter")
public class CallCenterController {
    @Autowired
    private CreateCallCenterCmdExe createCallCenterCmdExe;
    @Autowired
    private DelCallCenterCmdExe delCallCenterCmdExe;
    @Autowired
    private GetCallCenterQryExe getCallCenterQryExe;
    @Autowired
    private PageCallCenterQryExe pageCallCenterQryExe;
    @Autowired
    private UpdateCallCenterCmdExe updateCallCenterCmdExe;

    @PostMapping("/createCallCenter")
    @ApiOperation(value = "新增呼叫中心")
    //@RequiresPermissions("center_add")
    public SingleResponse createCallCenter(@RequestBody CreateCallCenterCmd cmd, UserAware userAware) {
        return createCallCenterCmdExe.execute(cmd);
    }

    @PostMapping("/updateCallCenter")
    @ApiOperation(value = "修改呼叫中心")
    //@RequiresPermissions("center_edit")
    public SingleResponse updateCallCenter(@RequestBody UpdateCallCenterCmd cmd, UserAware userAware) {
        return updateCallCenterCmdExe.execute(cmd);
    }

    @ApiOperation(value = "获取呼叫中心数据列表")
    @GetMapping(value = "/pageCallCenter")
    public SingleResponse<?> pageCallCenter(CallCenterPageQry cmd, UserAware userAware) throws Exception {
        return pageCallCenterQryExe.execute(cmd);
    }

    @ApiOperation(value = "根据id获取呼叫中心数据")
    @GetMapping(value = "/getCallCenter")
    public SingleResponse<?> getCallCenter(GetCallCenterCmd cmd, UserAware userAware) throws Exception {
        return getCallCenterQryExe.execute(cmd);
    }
    @ApiOperation(value = "删除呼叫中心数据")
    @PostMapping(value = "/delCallCenter")
    //@RequiresPermissions("center_del")
    public SingleResponse<?> delCallCenter(DelCallCenterCmd cmd, UserAware userAware) throws Exception {
        return delCallCenterCmdExe.execute(cmd);
    }
}
