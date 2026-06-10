package com.nuwa.app.zeus.command.mch;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.zeus.dto.clientobject.mch.DisableMerchantCmd;
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
import org.springframework.data.redis.core.RedisTemplate;
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
public class CheckRegisterCodeCmdExe extends AbstractCmdExe<MerchantRegisterCheckCodeCmd, SingleResponse<CheckRegisterCodeCmdExe.RegisterCheckCodeVO>> {

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    protected SingleResponse<RegisterCheckCodeVO> handle(MerchantRegisterCheckCodeCmd cmd) {
        SmsCode smsCode = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_PASSWORD)
                .eq(SmsCode::getMobile, cmd.getMobile())
                .eq(SmsCode::getCheckStatus, 10)
                .gt(SmsCode::getDeadTime, new Date())
                .orderByDesc(SmsCode::getSendTime)
                .last("limit 1")
                .one();
        if (BeanUtil.isEmpty(smsCode) || !smsCode.getCode().equals(cmd.getCode())) {
            return ErrorEnum.CODE_CHECK_FAILED.buildFailure();
        }
        smsCodeService.lambdaUpdate()
                .set(SmsCode::getCheckStatus, 20)
                .eq(SmsCode::getCode, cmd.getCode())
                .eq(SmsCode::getMobile, cmd.getMobile())
                .update();
        BaseUser user = baseUserService.lambdaQuery().eq(BaseUser::getMobilePhone, cmd.getMobile()).one();
        Merchant merchant = merchantService.lambdaQuery().eq(Merchant::getMchId, user.getTenantId()).one();
        RegisterCheckCodeVO vo = new RegisterCheckCodeVO();
        vo.setMerchantName(merchant.getMchName());
        vo.setUserName(user.getUsername());
        String code = UUID.randomUUID().toString();
        stringRedisTemplate.opsForValue().set(code, user.getId().toString(), 300, TimeUnit.SECONDS);
        vo.setCode(code);
        return SingleResponse.of(vo);
    }

    @Data
    public static class RegisterCheckCodeVO{
        @ApiModelProperty(value = "商户名")
        private String merchantName;
        @ApiModelProperty(value = "用户名")
        private String userName;
        @ApiModelProperty(value = "标识码")
        private String code;
    }
}
