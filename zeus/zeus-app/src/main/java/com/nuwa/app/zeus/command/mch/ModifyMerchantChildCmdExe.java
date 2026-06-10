package com.nuwa.app.zeus.command.mch;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.service.MerchantBiz;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantChildCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.ModifyMerchantChildCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.AdminCommonConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * CreateMerchantCmdExe 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:23
 * @since 1.0.0
 */
@Slf4j
@Component
public class ModifyMerchantChildCmdExe extends AbstractCmdExe<ModifyMerchantChildCmd, SingleResponse> {

    @Autowired
    private MerchantBiz merchantBiz;

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse handle(ModifyMerchantChildCmd cmd) {
        BaseUser baseUser = baseUserService.getById(cmd.getId());
        BeanUtils.copyProperties(cmd, baseUser);
        baseUser.setUpdateUserId(cmd.getUserAware().getMchUserId().toString());
        baseUser.setUpdateUserName(cmd.getUserAware().getUserName());
        baseUser.setUpdateHost(cmd.getIp());

        Boolean flag = merchantBiz.modifyMerchantChild(baseUser, cmd.getAppIds());
        if (flag) {
            return ErrorEnum.DATA_SUCCESS.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildSuccess();
    }
}
