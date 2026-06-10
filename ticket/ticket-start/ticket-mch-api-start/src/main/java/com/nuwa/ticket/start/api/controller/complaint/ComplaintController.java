package com.nuwa.ticket.start.api.controller.complaint;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.ticket.command.complaint.AuditComplaintCmdExe;
import com.nuwa.app.ticket.command.complaint.DeleteComplaintCmdExe;
import com.nuwa.app.ticket.command.complaint.query.ComplaintIdPageQryExe;
import com.nuwa.app.ticket.command.complaint.query.ComplaintIdQryExe;
import com.nuwa.client.ticket.dto.clientobject.complaint.AuditComplaintCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.DelComplaintCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.ComplaintPageQry;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.ComplaintQry;
import com.nuwa.framework.base.UserAware;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = {"用户投诉"})
@Slf4j
@RestController
@RequestMapping("/complaint")
public class ComplaintController {

    @Autowired
    private DeleteComplaintCmdExe deleteComplaintCmdExe;

    @Autowired
    private AuditComplaintCmdExe auditComplaintCmdExe;

    @Autowired
    private ComplaintIdQryExe complaintIdQryExe;

    @Autowired
    private ComplaintIdPageQryExe complaintIdPageQryExe;

    @GetMapping("/del")
    @ApiOperation(value = "删除数据")
    @RequiresPermissions("complaintManage:del")
    public SingleResponse del(DelComplaintCmd cmd, UserAware userAware) {
        return deleteComplaintCmdExe.execute(cmd);
    }

    @PostMapping("/auditFail")
    @ApiOperation(value = "投诉处理")
    @RequiresPermissions("complaintManage:handle")
    public SingleResponse auditFail(@RequestBody AuditComplaintCmd cmd, UserAware userAware) {
        return auditComplaintCmdExe.execute(cmd);
    }

    @GetMapping("/getById")
    @ApiOperation(value = "根据id查询数据")
    @RequiresPermissions(value = {"complaintManage:info", "complaintManage:rate"}, logical = Logical.OR)
    public SingleResponse getById(ComplaintQry cmd, UserAware userAware) {
        return complaintIdQryExe.execute(cmd);
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public SingleResponse page(ComplaintPageQry cmd, UserAware userAware) {
        return complaintIdPageQryExe.execute(cmd);
    }

}
