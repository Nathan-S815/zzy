package com.nuwa.app.zeus.command.mch;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.app.zeus.service.dto.MerchantUserDTO;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantChildCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.enums.AuditStatusEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * CreateMerchantCmdExe 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class CreateMerchantChildCmdExe extends AbstractCmdExe<CreateMerchantChildCmd, SingleResponse> {

    @Autowired
    private MerchantBiz merchantBiz;

    @Override
    protected SingleResponse handle(CreateMerchantChildCmd cmd) {
        BaseUser baseUser = new BaseUser();
        BeanUtils.copyProperties(cmd, baseUser);
        baseUser.setPassword(MD5.create().digestHex(cmd.getPassword(), "utf-8"));
        baseUser.setType(AdminCommonConstant.NORMAL_USER_TYPE);
        baseUser.setTenantId(cmd.getUserAware().getMchId());
        baseUser.setTenantAppId(-1L);
        baseUser.setStatus("0");
        baseUser.setCreateUserId(cmd.getUserAware().getMchUserId().toString());
        baseUser.setCreateUserName(cmd.getUserAware().getUserName());
        baseUser.setCreateHost(cmd.getIp());

        Boolean flag = merchantBiz.createMerchantChild(baseUser, cmd.getAppIds());
        if (flag) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
