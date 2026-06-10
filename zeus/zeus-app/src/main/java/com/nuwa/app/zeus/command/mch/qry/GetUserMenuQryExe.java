package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetUserMenuQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.service.*;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GetAppTreeQryExe
 *
 * @author hy
 * @date 2021/6/3 18:14
 * @since 1.0.0
 */
@Slf4j
@Component
public class GetUserMenuQryExe extends AbstractQryExe<GetUserMenuQry, SingleResponse<List<GetUserMenuQryExe.MenuVO>>> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Override
    protected SingleResponse<List<MenuVO>> handle(GetUserMenuQry cmd) {
        List<MenuVO> vos = new ArrayList<>();
        BaseUser user = baseUserService.getById(cmd.getUserAware().getMchUserId());
        if (user.getType().equals(AdminCommonConstant.NORMAL_USER_TYPE)){
            List<String> menuIds = baseResourceAuthorityService.lambdaQuery()
                    .eq(BaseResourceAuthority::getAuthorityId, user.getId())
                    .eq(BaseResourceAuthority::getAuthorityType, "user")
                    .eq(BaseResourceAuthority::getResourceType, "menu")
                    .eq(BaseResourceAuthority::getParentAppId, cmd.getAppId())
                    .eq(BaseResourceAuthority::getParentId, cmd.getId())
                    .list().stream().map(BaseResourceAuthority::getResourceId).collect(Collectors.toList());
            if (menuIds.size() != 0){
                vos = baseMenuService.lambdaQuery()
                        .in(BaseMenu::getId, menuIds)
                        .list().stream().map(x -> {
                            MenuVO vo = new MenuVO();
                            vo.setId(x.getId());
                            vo.setParentId(x.getParentId());
                            vo.setCode(x.getCode());
                            vo.setName(x.getTitle());
                            vo.setUri(x.getHref());
                            return vo;
                        }).collect(Collectors.toList());
            }
        }else if (user.getType().equals(AdminCommonConstant.SUPPER_USER_TYPE)){
            vos = baseMenuService.lambdaQuery()
                    .eq(BaseMenu::getAppId, cmd.getId())
                    .list().stream().map(x -> {
                        MenuVO vo = new MenuVO();
                        vo.setId(x.getId());
                        vo.setParentId(x.getParentId());
                        vo.setCode(x.getCode());
                        vo.setName(x.getTitle());
                        vo.setUri(x.getHref());
                        return vo;
                    }).collect(Collectors.toList());
        }
        return SingleResponse.of(TreeUtil.bulid(vos,-1));
    }

    @Data
    public class MenuVO extends TreeNode {
        private String code;
        private String name;
        private String uri;
    }
}
