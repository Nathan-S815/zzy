package com.nuwa.app.zeus.command.auth;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.constant.Constant;
import com.nuwa.client.zeus.dto.clientobject.auth.LoginMobileCmd;
import com.nuwa.framework.cola.starter.cmd.AbstractCmdExe;
import com.nuwa.infrastructure.zeus.constant.SmsBizConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.entity.MerchantSiteConfig;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantSiteConfigService;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.AuditStatusEnum;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
public class LoginMobileCmdExe extends AbstractCmdExe<LoginMobileCmd, SingleResponse> {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private MerchantSiteConfigService merchantSiteConfigService;

    @Override
    protected SingleResponse<BaseUser> handle(LoginMobileCmd cmd) {
        SmsCode smsCode = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_LOGIN)
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

        BaseUser adminUser = baseUserService.lambdaQuery().eq(BaseUser::getMobilePhone, cmd.getMobile()).one();
        if (Objects.isNull(adminUser)) {
            return SingleResponse.buildFailure(ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrCode(), ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrDesc());
        }

        if (StrUtil.isNotBlank(cmd.getDomain()) && Arrays.stream(Constant.ignoreDomains.split(",")).noneMatch(x -> x.contains(cmd.getDomain()))) {
            MerchantSiteConfig domainCfg = merchantSiteConfigService.lambdaQuery().eq(MerchantSiteConfig::getDomain, cmd.getDomain()).one();
            if (Objects.isNull(domainCfg)) {
                return SingleResponse.buildFailure(ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrCode(), ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrDesc());
            }

            if (!domainCfg.getMchId().equals(adminUser.getTenantId())) {
                return SingleResponse.buildFailure(ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrCode(), ErrorEnum.ADMIN_DOES_NOT_EXIST.getErrDesc());
            }
        }

        if ("1".equals(adminUser.getStatus())) {
            return SingleResponse.buildFailure(ErrorEnum.ADMIN_FORBID.getErrCode(), ErrorEnum.ADMIN_FORBID.getErrDesc());
        }

        if (adminUser.getTenantId().intValue() == -1) {
            return ErrorEnum.USER_TYPE_ERROR.buildFailure();
        }

        if (adminUser.getTenantId() != -1) {
            Integer count = merchantService.lambdaQuery()
                    .eq(Merchant::getMchId, adminUser.getTenantId())
                    .eq(Merchant::getAuditStatus, AuditStatusEnum.PASS.getCode())
                    .eq(Merchant::getStatus, 1)
                    .count();
            if (count == 0) {
                return SingleResponse.buildFailure(ErrorEnum.MERCHANT_STATUS_ERROR.getErrCode(), ErrorEnum.MERCHANT_STATUS_ERROR.getErrDesc());
            }
        }
        return SingleResponse.of(adminUser);
    }
}
