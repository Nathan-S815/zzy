package com.nuwa.app.ticket.command.callcenter.talk;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.GetTalkManageAppCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.TalkManage;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import com.nuwa.infrastructure.ticket.database.callcenter.service.TalkManageService;
import com.nuwa.infrastructure.ticket.enums.ErrorEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class GetTalkManageAppCmdExe  extends AbstractQryExe<GetTalkManageAppCmd, SingleResponse> {
    @Autowired
    private TalkManageService talkManageService;
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(GetTalkManageAppCmd cmd) {
        TalkManage talkManage = talkManageService.lambdaQuery()
                .eq(TalkManage::getEnableStatus,10)
//                .eq(TalkManage::getAppId, cmd.getUserAware().getMchAppId())
                .eq(TalkManage::getMchId,cmd.getUserAware().getMchId())
                .one();
        if(talkManage!=null) {
            List<String> problems = Arrays.asList(talkManage.getProblems().split(","));
            LinkedList<OnlineProblem> problemVOS = new LinkedList<>();
            problems.stream().forEach(x -> {
                OnlineProblem one = onlineProblemService.lambdaQuery().eq(OnlineProblem::getId, x).one();
                problemVOS.add(one);
            });
            GetTalkManageCmdExe.TalkManageVO talkManageVO = new GetTalkManageCmdExe.TalkManageVO();
            BeanUtil.copyProperties(talkManage, talkManageVO);
            talkManageVO.setProblemVOList(problemVOS);
            return SingleResponse.of(talkManageVO);
        }
        return SingleResponse.buildFailure(ErrorEnum.DATA_NON.getErrCode(), ErrorEnum.DATA_NON.getErrDesc());
    }

    @Data
    public static class TalkManageVO{
        private Long id;

        private Long mchId;

        private Long appId;

        private String title;

        private String problems;

        private LinkedList<OnlineProblem> problemVOList;

        private Integer sort;

        private Integer enableStatus;

        public static GetTalkManageCmdExe.TalkManageVO toVO(TalkManage talkManage) {
            GetTalkManageCmdExe.TalkManageVO vo = new GetTalkManageCmdExe.TalkManageVO();
            BeanUtil.copyProperties(talkManage, vo);
            return vo;
        }
    }

}
