package com.nuwa.app.ticket.command.complaint;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.nuwa.client.ticket.dto.clientobject.complaint.AuditComplaintCmd;
import com.nuwa.client.ticket.dto.clientobject.complaint.co.AuditComplaintCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import com.nuwa.infrastructure.ticket.enums.ComplaintStatusEnum;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class AuditComplaintCmdExe extends AbstractCmdExe<AuditComplaintCmd, SingleResponse> {

    @Autowired
    private ComplaintService complaintService;

    @Override
    protected SingleResponse handle(AuditComplaintCmd cmd) {
        AuditComplaintCO co = cmd.getAuditComplaintCO();
        LambdaUpdateChainWrapper<Complaint> lambdaUpdate = complaintService.lambdaUpdate()
                .set(Complaint::getAuditStatus, ComplaintStatusEnum.PROCESSED.getCode())
                .set(Complaint::getAuditResult, co.getStates())
                .set(Complaint::getAuditUserId, cmd.getUserAware().getMchUserId())
                .set(Complaint::getAuditRemark, co.getRemark())
                .set(Complaint::getAuditUserName, co.getAuditUserName())
                .set(Complaint::getAuditTime, new Date())
//                .eq(true, Complaint::getAppId, cmd.getAppId())
                .eq(Complaint::getMchId,cmd.getUserAware().getMchId())
                .eq(Complaint::getId, cmd.getId());
        return lambdaUpdate.update() ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
