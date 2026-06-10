package com.nuwa.app.ticket.command.callcenter.talk;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.talk.GetTalkManageCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblem;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.TalkManage;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemService;
import com.nuwa.infrastructure.ticket.database.callcenter.service.TalkManageService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class GetTalkManageCmdExe  extends AbstractQryExe<GetTalkManageCmd, SingleResponse> {
    @Autowired
    private TalkManageService talkManageService;
    @Autowired
    private OnlineProblemService onlineProblemService;
    @Override
    protected SingleResponse handle(GetTalkManageCmd cmd) {
        TalkManage talkManage = talkManageService.lambdaQuery().eq(TalkManage::getAppId, cmd.getAppId()).eq(TalkManage::getMchId, cmd.getUserAware().getMchId()).one();
        if(talkManage==null){
            TalkManage manage = new TalkManage();
            manage.setMchId(cmd.getUserAware().getMchId());
            manage.setAppId(cmd.getAppId());
            manage.setTitle("我猜你是想问：");
            talkManageService.save(manage);
            talkManage=manage;
        }
        LinkedList<OnlineProblem> problemVOS = new LinkedList<>();
        if(talkManage.getProblems()!=null) {
            List<String> problems = Arrays.asList(talkManage.getProblems().split(","));
            problems.stream().forEach(x -> {
                OnlineProblem one = onlineProblemService.lambdaQuery().eq(OnlineProblem::getId, x).one();
                problemVOS.add(one);
            });
        }
        TalkManageVO talkManageVO = new TalkManageVO();
        BeanUtil.copyProperties(talkManage, talkManageVO);
        talkManageVO.setProblemVOList(problemVOS);
        return SingleResponse.of(talkManageVO);
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

        public static TalkManageVO toVO(TalkManage talkManage) {
            TalkManageVO vo = new TalkManageVO();
            BeanUtil.copyProperties(talkManage, vo);
            return vo;
        }
    }
}
