package com.nuwa.app.zeus.command.mch.qry;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildElementTreeCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildPermissionTreeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.service.*;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
public class MerchantChildElementTreeQryExe extends AbstractQryExe<MerchantChildElementTreeCmd, SingleResponse<List<MerchantChildElementTreeQryExe.MerchantChildElementTree>>> {

    @Autowired
    private BaseMenuService baseMenuService;

    @Autowired
    private BaseElementService baseElementService;

    @Autowired
    private BaseResourceAuthorityService baseResourceAuthorityService;

    @Override
    protected SingleResponse<List<MerchantChildElementTree>> handle(MerchantChildElementTreeCmd cmd) {
        List<BaseResourceAuthority> opens = baseResourceAuthorityService.lambdaQuery()
                .eq(BaseResourceAuthority::getAuthorityId, cmd.getUserId())
                .eq(BaseResourceAuthority::getAuthorityType, "user")
                .eq(BaseResourceAuthority::getParentId, cmd.getId())
                .eq(BaseResourceAuthority::getParentAppId, cmd.getAppId())
                .list();

        List<Integer> menus = opens.stream().filter(x -> x.getResourceType().equals("menu")).map(x->Integer.parseInt(x.getResourceId())).collect(Collectors.toList());
        List<Integer> buttons = opens.stream().filter(x -> x.getResourceType().equals("button")).map(x->Integer.parseInt(x.getResourceId())).collect(Collectors.toList());


        List<MerchantChildElementTree> trees = baseMenuService.lambdaQuery()
                .eq(BaseMenu::getAppId, cmd.getId())
                .ne(BaseMenu::getParentId,-1)
                .list().stream().map(x -> {
                    MerchantChildElementTree tree = new MerchantChildElementTree();
                    tree.setId(x.getId());
                    tree.setName(x.getTitle());
                    tree.setType("menu");
                    if (menus.contains(x.getId())){
                        tree.setIsCheck(1);
                    }
                    return tree;
                }).collect(Collectors.toList());

        trees.stream().forEach(x -> {
            List<MerchantChildElementTree> button = baseElementService.lambdaQuery()
                    .eq(BaseElement::getMenuId, x.getId())
                    .list().stream().map(y -> {
                        MerchantChildElementTree tree = new MerchantChildElementTree();
                        tree.setId(y.getId());
                        tree.setName(y.getName());
                        tree.setType("button");
                        if (buttons.contains(y.getId())){
                            tree.setIsCheck(1);
                        }
                        return tree;
                    }).collect(Collectors.toList());
            x.setChild(button);
        });
        return SingleResponse.of(trees);
    }

    @Data
    public static class MerchantChildElementTree {
        private Integer id;

        private String name;

        @ApiModelProperty("menu button")
        private String type;

        @ApiModelProperty("0未选中 1选中")
        private Integer isCheck = 0;

        private List<MerchantChildElementTree> child;
    }
}
