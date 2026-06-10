package com.nuwa.ticket.start.api.controller.one;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneMerchantUsableIdentityPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUsableIdentityPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.param.OneMerchantUsableIdentityPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneUsableIdentityPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantUsableIdentityService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import com.nuwa.ticket.start.api.controller.one.param.AddUsableIdentityParam;
import com.nuwa.ticket.start.api.controller.one.param.ModifyMerchantIdentityParam;
import com.nuwa.ticket.start.api.controller.one.param.OnOrOffIdentityParam;
import com.nuwa.ticket.start.api.controller.one.vo.BossOneUsableIdentityPageVO;
import com.nuwa.ticket.start.api.controller.one.vo.MerchantOneUsableIdentityPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("one/merchant/identity")
@Api(tags = {"商户一码通身份认证接口"})
public class MerchantOneIdentityControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneMerchantUsableIdentityService oneMerchantUsableIdentityService;

    @ApiOperation(value = "开启")
    @RequestMapping(value = "/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@RequestBody @Valid OnOrOffIdentityParam param, UserAware userAware) {
        Integer count = oneMerchantUsableIdentityService.lambdaQuery()
                .eq(OneMerchantUsableIdentity::getMchId, userAware.getMchId())
                .eq(OneMerchantUsableIdentity::getIdentityCode, param.getIdentityCode())
                .count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "重复开启");
        }
        OneMerchantUsableIdentity merchantUsableIdentity = new OneMerchantUsableIdentity();
        merchantUsableIdentity.setMchId(userAware.getMchId());
        merchantUsableIdentity.setIdentityCode(param.getIdentityCode());
        merchantUsableIdentity.setStatus("on");
        merchantUsableIdentity.setSortNum(0);
        merchantUsableIdentity.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "/modifySortNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyOrderNum(@RequestBody @Valid ModifyMerchantIdentityParam param, UserAware userAware) {
        OneMerchantUsableIdentity merchantUsableIdentity = oneMerchantUsableIdentityService.lambdaQuery()
                .eq(OneMerchantUsableIdentity::getMchId, userAware.getMchId())
                .eq(OneMerchantUsableIdentity::getIdentityCode, param.getIdentityCode())
                .one();
        if (Objects.isNull(merchantUsableIdentity)) {
            merchantUsableIdentity = new OneMerchantUsableIdentity();
            merchantUsableIdentity.setMchId(userAware.getMchId());
            merchantUsableIdentity.setIdentityCode(param.getIdentityCode());
            merchantUsableIdentity.setStatus("on");
            merchantUsableIdentity.setSortNum(0);
        } else {
            merchantUsableIdentity.setSortNum(param.getSortNum());
        }
        merchantUsableIdentity.insertOrUpdate();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "关闭")
    @RequestMapping(value = "/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@RequestBody @Valid OnOrOffIdentityParam param, UserAware userAware) {
        oneMerchantUsableIdentityService.lambdaUpdate()
                .eq(OneMerchantUsableIdentity::getMchId, userAware.getMchId())
                .eq(OneMerchantUsableIdentity::getIdentityCode, param.getIdentityCode())
                .remove();
        return SingleResponse.buildSuccess();
    }


    @ApiOperation(value = "身份分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantOneUsableIdentityPageVO>> page(OneUsableIdentityPageQry pageQry, UserAware userAware) {
        OneUsableIdentityPageParam pageParam = new OneUsableIdentityPageParam(pageQry);
        IPage<MerchantOneUsableIdentityPageVO> pageData = oneUsableIdentityService.paginateAndConvert(pageParam, this::toVO);
        pageData.getRecords().forEach(x -> {
            OneMerchantUsableIdentity merchantUsableIdentity = oneMerchantUsableIdentityService.lambdaQuery()
                    .eq(OneMerchantUsableIdentity::getMchId, userAware.getMchId())
                    .eq(OneMerchantUsableIdentity::getIdentityCode, x.getIdentityCode())
                    .one();
            if (Objects.isNull(merchantUsableIdentity)) {
                x.setOpenStatus("off");
                x.setSortNum(0);
            } else {
                x.setOpenStatus("on");
                x.setSortNum(merchantUsableIdentity.getSortNum());
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "已开启的身份分页查询")
    @RequestMapping(value = "/onstatus/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantOneUsableIdentityPageVO>> onStatusPage(OneMerchantUsableIdentityPageQry pageQry, UserAware userAware) {
        pageQry.setMchId(userAware.getMchId());
        OneMerchantUsableIdentityPageParam pageParam = new OneMerchantUsableIdentityPageParam(pageQry);
        IPage<MerchantOneUsableIdentityPageVO> oneMerchantUsableIdentityIPage = oneMerchantUsableIdentityService.paginateAndConvert(pageParam, this::merchantUsableIdentityToVO);
        return SingleResponse.of(oneMerchantUsableIdentityIPage);
    }

    public MerchantOneUsableIdentityPageVO merchantUsableIdentityToVO(OneMerchantUsableIdentity entity) {
        OneUsableIdentity oneUsableIdentity = oneUsableIdentityService.lambdaQuery().eq(OneUsableIdentity::getIdentityCode, entity.getIdentityCode()).one();
        return toVO(oneUsableIdentity);
    }

    public MerchantOneUsableIdentityPageVO toVO(OneUsableIdentity entity) {
        MerchantOneUsableIdentityPageVO vo = new MerchantOneUsableIdentityPageVO();
        if (Objects.nonNull(entity)) {
            BeanUtils.copyProperties(entity, vo);
        }
        return vo;
    }
}
