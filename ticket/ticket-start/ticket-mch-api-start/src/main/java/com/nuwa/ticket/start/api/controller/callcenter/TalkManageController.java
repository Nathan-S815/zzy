package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.talk.GetTalkManageCmdExe;
import com.nuwa.app.ticket.command.callcenter.talk.UpdateTalkManageCmdExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.GetTalkManageCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.UpdateTalkManageCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"会话管理"})
@Slf4j
@RestController
@RequestMapping("/talk")
public class TalkManageController {
    @Autowired
    private GetTalkManageCmdExe getTalkManageCmdExe;
    @Autowired
    private UpdateTalkManageCmdExe updateTalkManageCmdExe;
    @ApiOperation(value = "根据id获取会话管理数据")
    @GetMapping(value = "/getTalkManage")
    public SingleResponse<?> getTalkManage(GetTalkManageCmd cmd, UserAware userAware) throws Exception {
        return getTalkManageCmdExe.execute(cmd);
    }

    @PostMapping("/updateTalkManage")
    @ApiOperation(value = "修改会话管理数据")
    //@RequiresPermissions("manage_edit")
    public SingleResponse updateTalkManage(@RequestBody UpdateTalkManageCmd cmd, UserAware userAware) {
        return updateTalkManageCmdExe.execute(cmd);
    }
}
