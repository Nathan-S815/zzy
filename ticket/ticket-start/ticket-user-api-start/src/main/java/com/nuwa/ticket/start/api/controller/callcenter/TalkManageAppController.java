package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.talk.GetTalkManageAppCmdExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.GetTalkManageAppCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"会话管理"})
@Slf4j
@RestController
@RequestMapping("/app/talk")
public class TalkManageAppController {
    @Autowired
    private GetTalkManageAppCmdExe getTalkManageCmdExe;

    @ApiOperation(value = "获取会话管理数据")
    @GetMapping(value = "/getTalkManage")
    public SingleResponse<?> getTalkManage(GetTalkManageAppCmd cmd, UserAware userAware) throws Exception {
        return getTalkManageCmdExe.execute(cmd);
    }
}
