package com.nuwa.app.zeus.command.mch;

import cn.hutool.crypto.digest.MD5;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantChangePasswordCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantRegisterCheckCodeCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.SmsBizConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * OpenMerchantAppCmdExe 开通应用
 *
 * @author hy
 * @date 2021/6/3 9:52
 * @since 1.0.0
 */
@Slf4j
@Component
public class ChangePasswordCmdExe extends AbstractCmdExe<MerchantChangePasswordCmd, SingleResponse> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected SingleResponse handle(MerchantChangePasswordCmd cmd) {
        String userId = stringRedisTemplate.opsForValue().get(cmd.getCode());
        boolean update = baseUserService.lambdaUpdate().eq(BaseUser::getId, userId).set(BaseUser::getPassword, MD5.create().digestHex(cmd.getPassword(), "utf-8")).update();
        if (update){
            stringRedisTemplate.delete(cmd.getCode());
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.DATA_FAIL.buildFailure();
    }

}
