package com.nuwa.app.ticket.command.callcenter.type.query;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.dto.clientobject.callcenter.type.ListAllOnlineProblemTypeByListCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.ticket.database.callcenter.entity.OnlineProblemType;
import com.nuwa.infrastructure.ticket.database.callcenter.service.OnlineProblemTypeService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@Component
public class ListAllOnlineProblemTypeByListCmdExe extends AbstractQryExe<ListAllOnlineProblemTypeByListCmd, SingleResponse> {

    @Autowired
    private OnlineProblemTypeService onlineProblemTypeService;
    @Override
    protected SingleResponse handle(ListAllOnlineProblemTypeByListCmd cmd) {
        List<OnlineProblemType> list = onlineProblemTypeService.lambdaQuery()
                .eq(OnlineProblemType::getAppId, cmd.getAppId())
                .eq(OnlineProblemType::getMchId, cmd.getUserAware().getMchId())
                .eq(OnlineProblemType::getDeleteFlag, 0).eq(OnlineProblemType::getParentId, 0).list();
        List<OnlineProblemTypeVO> onlineProblemTypeVOS = new LinkedList<>();
        list.stream().forEach(x->{
            OnlineProblemTypeVO onlineProblemTypeVO = OnlineProblemTypeVO.toVo(x);
            List<OnlineProblemType> list1 = onlineProblemTypeService.lambdaQuery().eq(OnlineProblemType::getAppId, cmd.getAppId())
                    .eq(OnlineProblemType::getMchId, cmd.getUserAware().getMchId())
                    .eq(OnlineProblemType::getDeleteFlag, 0)
                    .eq(OnlineProblemType::getParentId, x.getId()).list();
            onlineProblemTypeVO.setOnlineProblemTypeS(list1);
            onlineProblemTypeVOS.add(onlineProblemTypeVO);
        });
        return SingleResponse.of(onlineProblemTypeVOS);
    }

    @Data
    public static class  OnlineProblemTypeVO{
        private Long id;
        /**
         * 父id
         */
        private Long parentId;

        /**
         * 类别
         */
        private String category;

        /**
         * 类型
         */
        private String type;

        private List<OnlineProblemType> onlineProblemTypeS;

        public static OnlineProblemTypeVO toVo(OnlineProblemType onlineProblemType){
            OnlineProblemTypeVO vo = new OnlineProblemTypeVO();
            BeanUtil.copyProperties(onlineProblemType,vo);
            return vo;
        }
    }
}
