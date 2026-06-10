package com.nuwa.app.zeus.command.mch.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.util.TreeUtil;
import com.nuwa.app.zeus.vo.TreeNode;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildAppTreeCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.qry.MerchantChildElementTreeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractQryExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroup;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseGroupMember;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantApp;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantAppService;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
public class MerchantChildAppTreeQryExe extends AbstractQryExe<MerchantChildAppTreeCmd, SingleResponse<List<MerchantChildAppTreeQryExe.MerchantChildAppTree>>> {

    @Autowired
    private BaseMapperExt baseMapperExt;

    @Override
    protected SingleResponse<List<MerchantChildAppTree>> handle(MerchantChildAppTreeCmd cmd) {
        List<Map<String, Object>> mapList = baseMapperExt.selectMerchantChildApps(cmd.getUserId());
        List<MerchantChildAppTree> trees = mapList.stream().map(x -> {
            if (x.get("status").toString().equals("0")) {
                return null;
            }
            MerchantChildAppTree tree = new MerchantChildAppTree();
            BeanUtil.copyProperties(x, tree);
            return tree;
        }).filter(Objects::nonNull).collect(Collectors.toList());
        List<MerchantChildAppTree> tree = TreeUtil.bulid(trees, -1);
        return SingleResponse.of(tree);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class MerchantChildAppTree extends TreeNode {

        private String name;

        private Long appId;

        @ApiModelProperty("系统提供方 outer |  inner")
        private String provider;

        private Long parentAppId;

        private Integer groupTotal;
    }
}
