package com.nuwa.ticket.start.api.controller.callcenter;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.callcenter.center.query.ListAppCallCenterQryExe;
import com.nuwa.client.ticket.dto.clientobject.callcenter.center.ListCallCenterCmd;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {"呼叫中心"})
@Slf4j
@RestController
@RequestMapping("/app/callCenter")
public class CallCenterAppController {
    @Autowired
    private ListAppCallCenterQryExe listAppCallCenterQryExe;

    @ApiOperation(value = "获取呼叫中心数据列表")
    @GetMapping(value = "/listCallCenter")
    public SingleResponse<?> listCallCenter(ListCallCenterCmd cmd, UserAware userAware) throws Exception {
        return listAppCallCenterQryExe.execute(cmd);
    }
}
