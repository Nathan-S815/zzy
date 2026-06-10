package com.nuwa.zeus.start.api.controller.merchant;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.app.zeus.command.mch.AdminChangePasswordCmdExe;
import com.nuwa.app.zeus.command.mch.ChangePasswordCmdExe;
import com.nuwa.app.zeus.command.mch.CheckRegisterCodeCmdExe;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantAdminChangePasswordCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantChangePasswordCmd;
import com.nuwa.client.zeus.dto.clientobject.mch.MerchantRegisterCheckCodeCmd;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.zeus.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.zeus.constant.SmsBizConstant;
import com.nuwa.infrastructure.zeus.database.base.entity.BaseUser;
import com.nuwa.infrastructure.zeus.database.base.service.BaseUserService;
import com.nuwa.infrastructure.zeus.database.mch.entity.Merchant;
import com.nuwa.infrastructure.zeus.database.mch.service.MerchantService;
import com.nuwa.infrastructure.zeus.database.sms.entity.SmsCode;
import com.nuwa.infrastructure.zeus.database.sms.service.SmsCodeService;
import com.nuwa.infrastructure.zeus.enums.ErrorEnum;
import com.nuwa.infrastructure.zeus.enums.SmsStatusEnum;
import com.nuwa.zeus.start.api.constants.NoticeMessageTypeEnum;
import com.nuwa.zeus.start.api.controller.common.param.MerchantRegisterCheckCodeParam;
import com.nuwa.zeus.start.api.controller.common.param.MerchantRegisterSendCodeParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * MerchantAppController
 *
 * @author hy
 * @date 2021/6/9 14:11
 * @since 1.0.0
 */
@Controller
@RequestMapping("merchant/admin")
@Api(tags = {"商户账号相关"})
public class MerchantAdminController {

    @Autowired
    private BaseUserService baseUserService;

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private CheckRegisterCodeCmdExe checkRegisterCodeCmdExe;

    @Autowired
    private ChangePasswordCmdExe changePasswordCmdExe;

    @Autowired
    private AdminChangePasswordCmdExe adminChangePasswordCmdExe;

    @ApiOperation(value = "发送验证码")
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse sendCode(@RequestBody @Validated MerchantRegisterSendCodeParam param) {
        Integer count = baseUserService.lambdaQuery().eq(BaseUser::getMobilePhone, param.getMobile()).count();
        if (count == 0){
            return ErrorEnum.ADMIN_DOES_NOT_EXIST.buildFailure();
        }
        boolean checkFlag = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_PASSWORD)
                .eq(SmsCode::getMobile, param.getMobile())
                .gt(SmsCode::getSendTime, DateUtil.offsetMinute(new Date(),-1))
                .count() > 0;
        if (checkFlag) {
            return ErrorEnum.VALID_CODE_TO_BUSY.buildFailure();
        }

        String code = RandomUtil.randomNumbers(4);
        String strTemplate = NoticeMessageTypeEnum.VALID_CODE.getMessage();
        String content = String.format(strTemplate, code);
        Date timestamp = new Date();
        SmsCode sms = new SmsCode();
        sms.setTitle("忘记密码");
        sms.setCode(code);
        sms.setContent(content);
        sms.setCreateDate(timestamp);
        sms.setMobile(param.getMobile());
        sms.setSendTime(timestamp);
        sms.setDeadTime(DateUtil.offsetSecond(timestamp, 300));
        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
        sms.setSmsId(StrUtil.EMPTY);
        sms.setBizCode(SmsBizConstant.MCH_PASSWORD);
        sms.setSendType(10);
        sms.setCheckStatus(10);
        boolean insert = sms.insert();
        if (insert) {
            domainEventPublisher.publishEvent(new PersistentSmsCodeSucceededEvent(sms.getId()));
            return SingleResponse.buildSuccess();
        }
        return ErrorEnum.VALID_CODE_SEND_FAILED.buildFailure();
    }

    @ApiOperation(value = "检测验证码是否有效")
    @RequestMapping(value = "checkSmsCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<CheckRegisterCodeCmdExe.RegisterCheckCodeVO> checkRegisterCode(@RequestBody @Validated MerchantRegisterCheckCodeCmd cmd) {
        return checkRegisterCodeCmdExe.execute(cmd);
    }

    @ApiOperation(value = "忘记密码")
    @RequestMapping(value = "changePassword", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse changePassword(MerchantChangePasswordCmd cmd) {
        return changePasswordCmdExe.execute(cmd);
    }


    @ApiOperation(value = "修改密码")
    @RequestMapping(value = "admin/changePassword", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse adminChangePassword(@RequestBody MerchantAdminChangePasswordCmd cmd, UserAware userAware) {
        return adminChangePasswordCmdExe.execute(cmd);
    }

}
