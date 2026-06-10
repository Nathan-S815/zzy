package com.nuwa.app.zeus.command.auth.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.auth.vo.UserAuthorityVO;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetCurrentUserAuthInfoQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * GetCurrentUserAuthInfoQry
 *
 * @author hy
 * @date 2021/5/25 15:32
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetCurrentUserAuthInfoQryExe extends AbstractCmdExe<GetCurrentUserAuthInfoQry, SingleResponse<UserAuthorityVO>> {

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Autowired
    private BaseGroupService baseGroupService;

    @Autowired
    private BaseGroupMemberService baseGroupMemberService;

    @Override
    protected SingleResponse<UserAuthorityVO> handle(GetCurrentUserAuthInfoQry cmd) {
        Long userId = cmd.getUserId();
        List<BaseGroupMember> userGroupList = baseGroupMemberService.lambdaQuery().eq(BaseGroupMember::getUserId, userId).list();
        List<BaseGroup> groupList = userGroupList.stream().map(x -> baseGroupService.getById(x.getGroupId())).collect(Collectors.toList());
        List<BaseMenu> userAuthorityList = baseMapperExt.selectAuthorityMenuByUserId(userId.intValue());
        List<BaseElement> baseElements = baseMapperExt.selectAuthorityElementByUserId(userId+"");
        return SingleResponse.of(new UserAuthorityVO(groupList, userAuthorityList,baseElements));
    }
}
