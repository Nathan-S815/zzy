package com.nuwa.app.ticket.command.complaint;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.nuwa.client.ticket.dto.clientobject.complaint.DelComplaintCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.complaint.entity.Complaint;
import com.nuwa.infrastructure.ticket.database.complaint.service.ComplaintService;
import com.nuwa.infrastructure.ticket.enums.DeleteFlagEnum;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import com.nuwa.infrastructure.ticket.util.SerializUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class DeleteComplaintCmdExe extends AbstractCmdExe<DelComplaintCmd, SingleResponse> {

    @Autowired
    private ComplaintService complaintService;

    @Override
    protected SingleResponse handle(DelComplaintCmd cmd) {
        List<String> ids = SerializUtil.strToList(cmd.getId());
        if (ids == null || ids.size() == 0) {
            return ErrorEnum.FAILED.buildFailure();
        }
        LambdaUpdateChainWrapper<Complaint> lambdaUpdate = complaintService.lambdaUpdate()
                .set(Complaint::getDeleteFlag, DeleteFlagEnum.DELETE.getCode())
//                .eq(true, Complaint::getAppId, cmd.getAppId())
                .eq(Complaint::getMchId,cmd.getUserAware().getMchId())
                .in(true, Complaint::getId, ids);
        return lambdaUpdate.update() ? SingleResponse.buildSuccess() : ErrorEnum.FAILED.buildFailure();
    }
}
