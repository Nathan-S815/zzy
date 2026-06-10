package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetUserElementQry;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.GetUserMenuQry;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseElement;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseResourceAuthority;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseElementService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseResourceAuthorityService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
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
public class GetUserElementQryExe extends AbstractQryExe<GetUserElementQry, SingleResponse<List<GetUserElementQryExe.ElementVO>>> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    @Autowired
    private BaseElementService baseElementService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Override
    protected SingleResponse<List<ElementVO>> handle(GetUserElementQry cmd) {
        List<ElementVO> vos = new ArrayList<>();
        BaseUser user = baseUserService.getById(cmd.getUserAware().getMchUserId());
        if (user.getType().equals(AdminCommonConstant.NORMAL_USER_TYPE)){
            List<String> buttonIds = baseResourceAuthorityService.lambdaQuery()
                    .eq(BaseResourceAuthority::getAuthorityId, user.getId())
                    .eq(BaseResourceAuthority::getParentAppId, cmd.getAppId())
                    .eq(BaseResourceAuthority::getAuthorityType, "user")
                    .eq(BaseResourceAuthority::getResourceType, "button")
                    .eq(BaseResourceAuthority::getParentId, cmd.getId())
                    .list().stream().map(BaseResourceAuthority::getResourceId).collect(Collectors.toList());
            if (buttonIds.size() != 0){
                vos = baseElementService.lambdaQuery()
                        .in(BaseElement::getId, buttonIds)
                        .list().stream().map(x -> {
                            ElementVO vo = new ElementVO();
                            vo.setId(x.getId());
                            vo.setCode(x.getCode());
                            vo.setName(x.getName());
                            vo.setUri(x.getUri());
                            return vo;
                        }).collect(Collectors.toList());
            }
        }else if (user.getType().equals(AdminCommonConstant.SUPPER_USER_TYPE)){
            List<Integer> menuIds = baseMenuService.lambdaQuery()
                    .eq(BaseMenu::getAppId, cmd.getId())
                    .list().stream().map(BaseMenu::getId).collect(Collectors.toList());
            vos = baseElementService.lambdaQuery()
                    .in(BaseElement::getMenuId, menuIds)
                    .list().stream().map(x -> {
                        ElementVO vo = new ElementVO();
                        vo.setId(x.getId());
                        vo.setCode(x.getCode());
                        vo.setName(x.getName());
                        vo.setUri(x.getUri());
                        return vo;
                    }).collect(Collectors.toList());
        }
        return SingleResponse.of(vos);
    }

    @Data
    public class ElementVO {
        private Integer id;
        private String code;
        private String name;
        private String uri;
    }
}
