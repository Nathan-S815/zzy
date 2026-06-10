package com.nuwa.app.ticket.command.complaint;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.complaint.CreateComplaintCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.co.CreateComplaintCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CreateComplaintCmdExe extends AbstractCmdExe<CreateComplaintCmd, SingleResponse> {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private MemberService memberService;

    @Override
    protected SingleResponse handle(CreateComplaintCmd cmd) {
        CreateComplaintCO co = cmd.getCreateComplaintCO();
        Complaint complaint = new Complaint();
        BeanUtil.copyProperties(co,complaint);
        complaint.setMchId(cmd.getUserAware().getMchId());
        complaint.setAppId(cmd.getUserAware().getMchAppId());
        complaint.setUserId(cmd.getUserAware().getUserId());
        complaint.setUserName(memberService.getById(cmd.getUserAware().getUserId()).getUserNike());
        boolean save = complaintService.save(complaint);
        if (save){
            return SingleResponse.of(complaint);
        }
        return ErrorEnum.FAILED.buildFailure();
    }
}
