package com.nuwa.zeus.start.api.controller.common;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.extension.api.R;
import com.nuwa.app.zeus.command.mch.CreateMerchantCmdExe;
import com.nuwa.client.zeus.dto.clientobject.mch.CreateMerchantCmd;
import com.nuwa.client.zeus.dto.domainevent.sms.PersistentSmsCodeSucceededEvent;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * MerchantRegisterController
 *
 * @author hy
 * @date 2021/6/8 20:05
 * @since 1.0.0
 */
@Api(tags = {"商户注册"})
@Slf4j
@RestController
@RequestMapping("/merchant/register")
public class MerchantRegisterController {

    @Autowired
    private SmsCodeService smsCodeService;

    @Autowired
    private CreateMerchantCmdExe createMerchantCmdExe;

    @Autowired
    private MerchantService merchantService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @Autowired
    private BaseUserService baseUserService;

    @ApiOperation(value = "发送验证码")
    @RequestMapping(value = "sendCode", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse sendCode(@RequestBody @Validated MerchantRegisterSendCodeParam param) {
        Integer count = merchantService.lambdaQuery().eq(Merchant::getContentPhone, param.getMobile()).count();
        if (count > 0) {
            return ErrorEnum.MOBILE_IS_EXIST.buildFailure();
        }

        boolean checkFlag = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_REGISTER)
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
        sms.setTitle("商户注册");
        sms.setCode(code);
        sms.setContent(content);
        sms.setCreateDate(timestamp);
        sms.setMobile(param.getMobile());
        sms.setSendTime(timestamp);
        sms.setDeadTime(DateUtil.offsetSecond(timestamp, 300));
        sms.setStatus(SmsStatusEnum.UNSENT.getValue());
        sms.setSmsId(StrUtil.EMPTY);
        sms.setBizCode(SmsBizConstant.MCH_REGISTER);
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
    public SingleResponse checkRegisterCode(@RequestBody @Validated MerchantRegisterCheckCodeParam cmd) {
        SmsCode smsCode = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_REGISTER)
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
        return ErrorEnum.DATA_SUCCESS.buildSuccess();
    }

    @ApiOperation(value = "检测手机号是否可用")
    @RequestMapping(value = "{mobile}/checkMobileIsExist", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse checkMobileIsExist(@PathVariable("mobile") String mobile) {
        Integer count = baseUserService.lambdaQuery()
                .eq(BaseUser::getMobilePhone,mobile)
                .count();
        if (count>0){
            return ErrorEnum.MOBILE_IS_EXIST.buildFailure();
        }
        return ErrorEnum.DATA_SUCCESS.buildSuccess();
    }

    @ApiOperation(value = "检测用户名是否可用")
    @RequestMapping(value = "{username}/checkUserNameIsExist", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse checkUserNameIsExist(@PathVariable("username") String userName) {
        Integer count = baseUserService.lambdaQuery()
                .eq(BaseUser::getUsername,userName)
                .count();
        if (count>0){
            return ErrorEnum.USER_NAME_IS_EXIST.buildFailure();
        }
        return ErrorEnum.DATA_SUCCESS.buildSuccess();
    }

    @ApiOperation(value = "商户注册")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse register(@RequestBody @Validated CreateMerchantCmd cmd) {
        Assert.isTrue(!StrUtil.isBlank(cmd.getCode()), "验证码不能为空");
        Assert.isTrue(!StrUtil.isBlank(cmd.getContentPhone()), "手机号不能为空");
 /*       Integer registerSmsCount = smsCodeService.lambdaQuery()
                .eq(SmsCode::getBizCode, SmsBizConstant.MCH_REGISTER)
                .eq(SmsCode::getMobile, cmd.getContentPhone())
                .eq(SmsCode::getCheckStatus, 10)
                .eq(SmsCode::getCode, cmd.getCode())
                .gt(SmsCode::getDeadTime, new Date())
                .count();
        if (registerSmsCount == 0) {
            return ErrorEnum.CODE_CHECK_FAILED.buildFailure();
        }
        smsCodeService.lambdaUpdate()
                .set(SmsCode::getCheckStatus, 20)
                .eq(SmsCode::getCode, cmd.getCode())
                .eq(SmsCode::getMobile, cmd.getContentPhone())
                .update();*/
        return createMerchantCmdExe.execute(cmd);
    }
}
