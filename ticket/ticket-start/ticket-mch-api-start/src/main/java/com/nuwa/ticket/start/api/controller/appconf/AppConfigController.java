package com.nuwa.ticket.start.api.controller.appconf;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.appconf.qry.MerchantAppConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.param.MchAppConfPageParam;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.ticket.start.api.controller.appconf.param.AddAlipayAppConfParam;
import com.nuwa.ticket.start.api.controller.appconf.param.AddMchAppBaseConfParam;
import com.nuwa.ticket.start.api.controller.appconf.param.SetPaymentConfParam;
import com.nuwa.ticket.start.api.controller.appconf.vo.MchAppBaseConfigPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("merchant/client")
@Api(tags = {"商户客户端配置管理"})
public class AppConfigController {

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private PsAlipayConfigService psAlipayConfigService;

    @Autowired
    private PsTemplateInfoService psTemplateInfoService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody AddMchAppBaseConfParam param, UserAware userAware) {
        Integer count = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getOutAppId()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9871", "AppId已存在");
        }
        if ("SINGLE_SCENIC".equalsIgnoreCase(param.getType())) {
            Assert.notNull(param.getPoiList());
        }
        Integer mchAppCount = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getMchId, userAware.getMchId()).count();
        MerchantAppBaseConf merchantAppBaseConf = new MerchantAppBaseConf();
        BeanUtils.copyProperties(param, merchantAppBaseConf);
        merchantAppBaseConf.setMchId(userAware.getMchId());
        if (mchAppCount == 0) {
            merchantAppBaseConf.setDefaultFlag(1);
        }
        merchantAppBaseConf.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "新增支付宝")
    @RequestMapping(value = "/alipay/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> alipayAdd(@RequestBody AddAlipayAppConfParam param, UserAware userAware) {
        Integer count = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getOutAppId, param.getOutAppId()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9871", "AppId已存在");
        }
        if ("SINGLE_SCENIC".equalsIgnoreCase(param.getType())) {
           // Assert.notNull(param.getPoiList());
        }

        if (param.getAppType().equalsIgnoreCase("alipay_mini_template")) {
            Assert.isTrue(StrUtil.isNotBlank(param.getAppTemplateId()), "模板三方应用AppId不能为空");
        }
        Integer mchAppCount = merchantAppBaseConfService.lambdaQuery().eq(MerchantAppBaseConf::getMchId, userAware.getMchId()).count();
        MerchantAppBaseConf merchantAppBaseConf = new MerchantAppBaseConf();
        BeanUtils.copyProperties(param, merchantAppBaseConf);
        if (param.getAppType().equalsIgnoreCase("alipay_mini_template")) {
            PsTemplateInfo psTemplateInfo = psTemplateInfoService.lambdaQuery().eq(PsTemplateInfo::getTemplateId, param.getAppTemplateId()).one();
            Assert.notNull(psTemplateInfo, "模板id不存在");
            PsAlipayConfig psAlipayConfig = psAlipayConfigService.lambdaQuery().eq(PsAlipayConfig::getAppId, psTemplateInfo.getAppId()).one();
            merchantAppBaseConf.setChannelPublicKey(psAlipayConfig.getAlipayPublicKey());
            merchantAppBaseConf.setMchPrivateKey(psAlipayConfig.getPrivateKey());
            merchantAppBaseConf.setThirdAppId(psTemplateInfo.getAppId());
        }
        merchantAppBaseConf.setMchId(userAware.getMchId());
        if (mchAppCount == 0) {
            merchantAppBaseConf.setDefaultFlag(1);
        }
        merchantAppBaseConf.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "设为默认小程序")
    @RequestMapping(value = "/{id}/defaultAppSet", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> defaultAppSet(@PathVariable("id") Long id, UserAware userAware) {
        merchantAppBaseConfService.lambdaUpdate()
                .set(MerchantAppBaseConf::getDefaultFlag, 1)
                .eq(MerchantAppBaseConf::getId, id)
                .eq(MerchantAppBaseConf::getMchId, userAware.getMchId())
                .update();

        merchantAppBaseConfService.lambdaUpdate()
                .set(MerchantAppBaseConf::getDefaultFlag, 0)
                .ne(MerchantAppBaseConf::getId, id)
                .eq(MerchantAppBaseConf::getMchId, userAware.getMchId())
                .update();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "支付参数设置")
    @RequestMapping(value = "/{id}/paymentConfigSet", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> paymentConfigSet(@PathVariable("id") Long id, @RequestBody SetPaymentConfParam param, UserAware userAware) {
        MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getMchClientId, id).one();
        if (Objects.isNull(merchantAppPayConf)) {
            merchantAppPayConf = new MerchantAppPayConf();
        }
        BeanUtils.copyProperties(param, merchantAppPayConf);
        MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(id);
        merchantAppPayConf.setOutAppId(merchantAppBaseConf.getOutAppId());
        merchantAppPayConf.setChannelType(merchantAppBaseConf.getAppType());
        merchantAppPayConf.setMchClientId(id);
        merchantAppPayConf.setMchId(userAware.getMchId());
        merchantAppPayConf.insertOrUpdate();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取支付配置")
    @RequestMapping(value = "getPaymentConfig", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantAppPayConf> getPaymentConfig(@RequestParam("id") Long id, UserAware userAware) {
        MerchantAppPayConf merchantAppPayConf = merchantAppPayConfService.lambdaQuery().eq(MerchantAppPayConf::getMchClientId, id).one();
        return SingleResponse.of(merchantAppPayConf);
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> update(@PathVariable("id") Long id, @RequestBody AddMchAppBaseConfParam param, UserAware userAware) {
        MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(id);
        BeanUtils.copyProperties(param, merchantAppBaseConf);
        merchantAppBaseConf.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "支付宝修改")
    @RequestMapping(value = "/{id}/alipay/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> alipayUpdate(@PathVariable("id") Long id, @RequestBody AddAlipayAppConfParam param, UserAware userAware) {
        MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(id);
        BeanUtils.copyProperties(param, merchantAppBaseConf);
        merchantAppBaseConf.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MchAppBaseConfigPageVO>> page(@Valid MerchantAppConfPageQry pageQry, UserAware userAware) {
        MchAppConfPageParam pageParam = new MchAppConfPageParam(pageQry);
        IPage<MchAppBaseConfigPageVO> pageData = merchantAppBaseConfService.paginateAndConvert(pageParam, MchAppBaseConfigPageVO::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MchAppBaseConfigPageVO> detail(@PathVariable("id") Integer id, UserAware userAware) {
        MerchantAppBaseConf merchantSupplierConfig = merchantAppBaseConfService.getById(id);
        MchAppBaseConfigPageVO mchAppBaseConfigPageVO = MchAppBaseConfigPageVO.toVO(merchantSupplierConfig);
        return SingleResponse.of(mchAppBaseConfigPageVO);
    }
}
