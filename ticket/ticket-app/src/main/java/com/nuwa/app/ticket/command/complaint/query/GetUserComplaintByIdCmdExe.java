package com.nuwa.app.ticket.command.complaint.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.GetUserComplaintByIdCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class GetUserComplaintByIdCmdExe extends AbstractQryExe<GetUserComplaintByIdCmd, SingleResponse> {
    @Autowired
    private ComplaintService complaintService;
    @Override
    protected SingleResponse handle(GetUserComplaintByIdCmd cmd) {
        Complaint one = complaintService.lambdaQuery().eq(Complaint::getId, cmd.getId()).one();
        return SingleResponse.of(one);
    }
}
