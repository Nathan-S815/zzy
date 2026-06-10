package com.nuwa.app.zeus.command.mch;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.nuwa.client.zeus.dto.clientobject.mch.EditMerchantChildElementCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.EditPasswordCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseMenu;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseResourceAuthority;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseMenuService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseResourceAuthorityService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * OpenMerchantAppCmdExe 开通应用
 *
 * @author hy
 * @date 2021/6/3 9:52
 * @since 1.0.0
 */
@Slf4j
@Component
public class EditMerchantChildElementCmdExe extends AbstractCmdExe<EditMerchantChildElementCmd, SingleResponse> {

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    @Autowired
    private BaseMenuService baseMenuService;

    @Override
    protected SingleResponse handle(EditMerchantChildElementCmd cmd) {
        baseResourceAuthorityService.remove(new LambdaQueryWrapper<BaseResourceAuthority>()
                .eq(BaseResourceAuthority::getAuthorityType, "user")
                .eq(BaseResourceAuthority::getAuthorityId, cmd.getUserId())
                .eq(BaseResourceAuthority::getParentId, cmd.getId())
                .eq(BaseResourceAuthority::getParentAppId, cmd.getAppId())
        );
        List<BaseResourceAuthority> authorities = cmd.getElements().stream().map(x -> {
            BaseResourceAuthority authority = new BaseResourceAuthority();
            authority.setAuthorityId(cmd.getUserId().toString());
            authority.setAuthorityType("user");
            authority.setResourceId(x.getId().toString());
            authority.setResourceType(x.getType());
            authority.setParentId(cmd.getId());
            authority.setParentAppId(cmd.getAppId());
            return authority;
        }).collect(Collectors.toList());

        BaseMenu menu = baseMenuService.lambdaQuery().eq(BaseMenu::getAppId, cmd.getId()).eq(BaseMenu::getParentId, "-1").one();
        BaseResourceAuthority authority = new BaseResourceAuthority();
        authority.setAuthorityId(cmd.getUserId().toString());
        authority.setAuthorityType("user");
        authority.setResourceId(menu.getId().toString());
        authority.setResourceType("menu");
        authority.setParentId(cmd.getId());
        authority.setParentAppId(cmd.getAppId());

        authorities.add(authority);

        baseResourceAuthorityService.saveBatch(authorities);

        return SingleResponse.buildSuccess();
    }
}
