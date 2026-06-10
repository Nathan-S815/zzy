package com.nuwa.app.zeus.command.auth.qry;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.app.zeus.command.auth.vo.UserAuthorityVO;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetCurrentUserAuthInfoQry;
import com.nuwa.client.zeus.dto.clientobject.auth.qry.GetCurrentUserInfoQry;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.*;
import com.nuwa.infrastructure.zeus.database.base.mapper.ext.BaseMapperExt;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupMemberService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseGroupService;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
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
public class GetCurrentUserInfoQryExe extends AbstractCmdExe<GetCurrentUserInfoQry, SingleResponse<GetCurrentUserInfoQryExe.AdminUserVO>> {

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse<AdminUserVO> handle(GetCurrentUserInfoQry cmd) {
        Merchant merchant = merchantService.getById(cmd.getUserAware().getMchId());
        AdminUserVO vo = new AdminUserVO();
        BeanUtil.copyProperties(cmd.getUserAware(),vo);
        if (BeanUtil.isNotEmpty(merchant)){
            vo.setLogo(merchant.getLogo());
        }
        BaseUser user = baseUserService.getById(cmd.getUserAware().getMchUserId());
        vo.setMobilePhone(user.getMobilePhone());
        vo.setUserType(user.getType());
        return SingleResponse.of(vo);
    }

    @Data
    public static class AdminUserVO {

        @ApiModelProperty(value = "商户用户id")
        private Long mchUserId;

        @ApiModelProperty(value = "商户id")
        private Long mchId;

        @ApiModelProperty(value = "登录用户名")
        private String userName;

        @ApiModelProperty(value = "省")
        private String provinceId;

        @ApiModelProperty(value = "市")
        private String cityId;

        @ApiModelProperty(value = "区")
        private String countyId;

        @ApiModelProperty(value = "hostIp")
        private String hostIp;

        @ApiModelProperty(value = "logo")
        @JsonSerialize(using = MaterialJson.class)
        private Long logo;

        @ApiModelProperty(value = "手机号")
        private String mobilePhone;

        @ApiModelProperty(value = "类型  0:普通用户 1:管理员")
        private String userType;
    }
}
