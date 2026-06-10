package com.nuwa.app.zeus.command.mch;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantAdminChangePasswordCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantChangePasswordCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * OpenMerchantAppCmdExe 开通应用
 *
 * @author hy
 * @date 2021/6/3 9:52
 * @since 1.0.0
 */
@Slf4j
@Component
public class AdminChangePasswordCmdExe extends AbstractCmdExe<MerchantAdminChangePasswordCmd, SingleResponse> {

    @Autowired
    private BaseUserService baseUserService;

    @Override
    protected SingleResponse handle(MerchantAdminChangePasswordCmd cmd) {
        if (cmd.getOldPassword().equals(cmd.getNewPassword())){
            return ErrorEnum.PASSWORD_SAME_ERROR.buildFailure();
        }
        boolean update = baseUserService.lambdaUpdate()
                .eq(BaseUser::getId, cmd.getUserAware().getMchUserId())
                .eq(BaseUser::getPassword, MD5.create().digestHex(cmd.getOldPassword(), "utf-8"))
                .set(BaseUser::getPassword, MD5.create().digestHex(cmd.getNewPassword(), "utf-8"))
                .update();
        if (update){
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.PASSWORD_WRONG.buildFailure();
    }

}
