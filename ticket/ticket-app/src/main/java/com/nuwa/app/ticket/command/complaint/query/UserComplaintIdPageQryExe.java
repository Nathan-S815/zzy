package com.nuwa.app.ticket.command.complaint.query;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.complaint.qry.UserComplaintPageQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.param.UserComplaintPageParam;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserComplaintIdPageQryExe extends AbstractQryExe<UserComplaintPageQry, SingleResponse> {

    @Autowired
    private ComplaintService complaintService;

    @Override
    protected SingleResponse handle(UserComplaintPageQry cmd) {
        IPage<Complaint> complaintIPage = complaintService.paginateByParam(new UserComplaintPageParam(cmd));
        return SingleResponse.of(complaintIPage);
    }
}
