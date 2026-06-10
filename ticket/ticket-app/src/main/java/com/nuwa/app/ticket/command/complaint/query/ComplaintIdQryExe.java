package com.nuwa.app.ticket.command.complaint.query;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.ComplaintQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ComplaintIdQryExe extends AbstractQryExe<ComplaintQry, SingleResponse> {

    @Autowired
    private ComplaintService complaintService;

    @Override
    protected SingleResponse handle(ComplaintQry cmd) {
        return SingleResponse.of(complaintService.getById(cmd.getId()));
    }
}
