package com.nuwa.ticket.start.api.controller.complaint;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.complaint.CreateComplaintCmdExe;
import com.nuwa.app.ticket.command.complaint.query.GetUserComplaintByIdCmdExe;
import com.nuwa.app.ticket.command.complaint.query.UserComplaintIdPageQryExe;
import com.nuwa.client.ticket.dto.clientobject.complaint.CreateComplaintCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.GetUserComplaintByIdCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.UserComplaintPageQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"用户投诉"})
@Slf4j
@RestController
@RequestMapping("/user/complaint")
public class UserComplaintController {

    @Autowired
    private CreateComplaintCmdExe createComplaintCmdExe;

    @Autowired
    private UserComplaintIdPageQryExe userComplaintIdPageQryExe;
    @Autowired
    private GetUserComplaintByIdCmdExe getUserComplaintByIdCmdExe;

    @PostMapping("/create")
    @ApiOperation(value = "新增数据")
    public SingleResponse del(@RequestBody CreateComplaintCmd cmd, UserAware userAware) {
        return createComplaintCmdExe.execute(cmd);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public SingleResponse page(UserComplaintPageQry cmd, UserAware userAware) {
        return userComplaintIdPageQryExe.execute(cmd);
    }
    @GetMapping("/getUserComplaintById")
    @ApiOperation(value = "根据id获取投诉数据详情")
    public SingleResponse getUserComplaintById(GetUserComplaintByIdCmd cmd, UserAware userAware) {
        return getUserComplaintByIdCmdExe.execute(cmd);
    }

}
