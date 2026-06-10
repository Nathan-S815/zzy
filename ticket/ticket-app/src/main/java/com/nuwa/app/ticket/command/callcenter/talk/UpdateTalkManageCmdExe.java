package com.nuwa.app.ticket.command.callcenter.talk;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.UpdateTalkManageCmd;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.co.UpdateTalkManageCO;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.TalkManage;
import com.nuwa.infrastructure.ticket.database.callcenter.service.TalkManageService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
public class UpdateTalkManageCmdExe extends AbstractCmdExe<UpdateTalkManageCmd, SingleResponse> {
    @Autowired
    private TalkManageService talkManageService;
    @Override
    protected SingleResponse handle(UpdateTalkManageCmd cmd) {
        UpdateTalkManageCO updateTalkManageCO = cmd.getUpdateTalkManageCO();
        TalkManage one = talkManageService.lambdaQuery().eq(TalkManage::getId, updateTalkManageCO.getId()).one();
        Assert.notNull(one, ErrorEnum.DATA_NON, "会话管理(id:" + cmd.getUpdateTalkManageCO().getId() + ")不存在");
        one.setTitle(updateTalkManageCO.getTitle()).setProblems(updateTalkManageCO.getProblems()).setUpdateTime(new Date()).setEnableStatus(updateTalkManageCO.getEnableStatus());
        boolean updateById = one.updateById();
        if (updateById) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess(one.getId());
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }
}
