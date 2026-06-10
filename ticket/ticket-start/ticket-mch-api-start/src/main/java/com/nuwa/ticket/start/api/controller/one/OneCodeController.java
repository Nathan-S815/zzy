package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneAdConfigPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneClientConfigPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneMemberPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUserCenterPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.*;
import com.nuwa.infrastructure.ticket.database.one.param.OneAdConfigPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneClientConfigPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneMemberPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneUserCenterPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.*;
import com.nuwa.ticket.start.api.controller.one.param.*;
import com.nuwa.ticket.start.api.controller.one.vo.OneMemberPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("onecode")
@Api(tags = {"一码通相关接口"})
public class OneCodeController {

    @Autowired
    private OneClientConfigService oneClientConfigService;

    @Autowired
    private OneAdConfigService oneAdConfigService;

    @Autowired
    private OneUserCenterService oneUserCenterService;

    @Autowired
    private OneMemberService oneMemberService;

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @ApiOperation(value = "新增端口")
    @RequestMapping(value = "/client/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> addClient(@RequestBody @Valid AddClientParam param, UserAware userAware) {
        Integer mchClientCount = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getMchId, userAware.getMchId()).count();
        if (mchClientCount > 0) {
            return SingleResponse.buildFailure("9824", "一个商户只能创建一个端口");
        }
        String outAppId = param.getOutAppId();
        outAppId = "onecode" + outAppId;
        Integer count = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getOutAppId, outAppId).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "pid不能重复");
        }
        OneClientConfig clientConfig = new OneClientConfig();
        BeanUtils.copyProperties(param, clientConfig);
        clientConfig.setOutAppId(outAppId);
        if (Objects.isNull(param.getMchId())) {
            clientConfig.setMchId(userAware.getMchId());
        }

        clientConfig.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "开启")
    @RequestMapping(value = "/client/{id}/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@PathVariable("id") Long id, UserAware userAware) {
        oneClientConfigService.lambdaUpdate()
                .set(OneClientConfig::getStatus, "on")
                .eq(OneClientConfig::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "关闭")
    @RequestMapping(value = "/client/{id}/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@PathVariable("id") Long id, UserAware userAware) {
        oneClientConfigService.lambdaUpdate()
                .set(OneClientConfig::getStatus, "off")
                .eq(OneClientConfig::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改端口")
    @RequestMapping(value = "/client/modifyName", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> addClient(@RequestBody @Valid ModifyClientConfigParam param, UserAware userAware) {
        oneClientConfigService.lambdaUpdate()
                .set(OneClientConfig::getName, param.getName())
                .set(OneClientConfig::getBackgroundColor, param.getBackgroundColor())
                .set(OneClientConfig::getBackgroundPictureId, param.getBackgroundPictureId())
                .set(OneClientConfig::getRemark, param.getRemark())
                .eq(OneClientConfig::getId, param.getId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "端口详情")
    @RequestMapping(value = "/client/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneClientConfig> clientDetail(@PathVariable("id") Long id, UserAware userAware) {
        OneClientConfig oneClientConfig = oneClientConfigService.getById(id);
        return SingleResponse.of(oneClientConfig);
    }

    @ApiOperation(value = "功能配置")
    @RequestMapping(value = "/client/setBizScope", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> setClientBizScope(@RequestBody @Valid SetClientScopeParam param, UserAware userAware) {
        oneClientConfigService.lambdaUpdate().eq(OneClientConfig::getId, param.getId()).set(OneClientConfig::getBizList, param.getBizList()).update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "新增广告")
    @RequestMapping(value = "/client/createAdConfig", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> clientAddAdConfig(@RequestBody @Valid AddClientAdConfigParam param, UserAware userAware) {
        OneClientConfig clientConfig = oneClientConfigService.getById(param.getOneClientId());
        if (Objects.isNull(clientConfig)) {
            return SingleResponse.buildFailure("9874", "端口不存在");
        }
        OneAdConfig adConfig = new OneAdConfig();
        BeanUtils.copyProperties(param, adConfig);
        adConfig.setMchId(clientConfig.getMchId());
        adConfig.setCreateTime(new Date());
        adConfig.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改广告")
    @RequestMapping(value = "/client/modifyAdConfig", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyAdConfig(@RequestBody @Valid ModifyClientAdConfigParam param, UserAware userAware) {
        oneAdConfigService.lambdaUpdate()
                .set(OneAdConfig::getImage, param.getImage())
                .set(OneAdConfig::getTitle, param.getTitle())
                .set(OneAdConfig::getLink, param.getLink())
                .eq(OneAdConfig::getId, param.getAdId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "移除广告")
    @RequestMapping(value = "/client/removedAdConfig", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> removedAdConfig(@RequestBody @Valid ClientRemovedAdConfigParam param, UserAware userAware) {
        oneAdConfigService.removeById(param.getAdId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "广告排序")
    @RequestMapping(value = "/client/modifyAdOrderNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyAdOrderNum(@RequestBody @Valid ClientModifyAdOrderNumParam param, UserAware userAware) {
        oneAdConfigService.lambdaUpdate()
                .set(OneAdConfig::getOrderNum, param.getOrderNum())
                .set(OneAdConfig::getUpdateTime, new Date())
                .eq(OneAdConfig::getId, param.getAdId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "端口分页")
    @RequestMapping(value = "/client/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneClientConfig>> page(OneClientConfigPageQry pageQry, UserAware userAware) {
        OneClientConfigPageParam pageParam = new OneClientConfigPageParam(pageQry);
        IPage<OneClientConfig> pageData = oneClientConfigService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "广告分页")
    @RequestMapping(value = "/client/adPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneAdConfig>> clientAdPage(OneAdConfigPageQry pageQry, UserAware userAware) {
        OneAdConfigPageParam pageParam = new OneAdConfigPageParam(pageQry);
        IPage<OneAdConfig> pageData = oneAdConfigService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取广告详情")
    @RequestMapping(value = "/{adId}/getAdDetail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneAdConfig> getAdDetail(@PathVariable("adId") Long adId, UserAware userAware) {
        OneAdConfig oneAdConfig = oneAdConfigService.getById(adId);
        return SingleResponse.of(oneAdConfig);
    }

    @ApiOperation(value = "会员分页")
    @RequestMapping(value = "/member/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneMemberPageVO>> memberPage(OneMemberPageQry pageQry, UserAware userAware) {
        OneMemberPageParam pageParam = new OneMemberPageParam(pageQry);
        IPage<OneMemberPageVO> pageData = oneMemberService.paginateAndConvert(pageParam, this::toOneMemberPageVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "[BOSS]用户中心分页")
    @RequestMapping(value = "/userCenter/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneUserCenter>> userCenterPage(OneUserCenterPageQry pageQry, UserAware userAware) {
        OneUserCenterPageParam pageParam = new OneUserCenterPageParam(pageQry);
        IPage<OneUserCenter> pageData = oneUserCenterService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    public OneMemberPageVO toOneMemberPageVO(OneMember oneMember) {
        OneMemberPageVO vo = new OneMemberPageVO();
        BeanUtils.copyProperties(oneMember, vo);
        OneUserCenter userCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, oneMember.getUserPhone()).one();
        if (Objects.nonNull(userCenter)) {
            vo.setUserCenterId(userCenter.getUserId().longValue());
            vo.setUserRealName(userCenter.getUserRealName());
            vo.setUserIdCard(userCenter.getUserIdCard());
            if (StrUtil.isNotBlank(oneMember.getIdentityCode())) {
                if (oneMember.getIdentityCode().equalsIgnoreCase("-1")) {
                    vo.setIdentityCode("全部");
                } else if (StrUtil.isNotBlank(oneMember.getIdentityCode())) {
                    ArrayList<String> identityCodeList = CollectionUtil.toList(oneMember.getIdentityCode().split(","));
                    List<OneUsableIdentity> identities = oneUsableIdentityService
                            .lambdaQuery()
                            .in(OneUsableIdentity::getIdentityCode, identityCodeList)
                            .list();
                    String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                    vo.setIdentityCode(identityNames);
                }
            }
        }
        return vo;
    }

}
