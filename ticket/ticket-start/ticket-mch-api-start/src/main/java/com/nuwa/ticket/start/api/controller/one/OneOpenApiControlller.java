package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneOpenApiRecordPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.*;
import com.nuwa.infrastructure.ticket.database.one.param.OneOpenApiRecordPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.controller.one.param.ParseEelectronicIdentityQrCodeParam;
import com.nuwa.ticket.start.api.controller.one.param.RightsConfirmParam;
import com.nuwa.ticket.start.api.controller.one.param.ScenicspotBindParam;
import com.nuwa.ticket.start.api.controller.one.vo.EelectronicIdentityQrCodeVO;
import com.nuwa.ticket.start.api.controller.one.vo.IdentityRightsVO;
import com.nuwa.ticket.start.api.controller.one.vo.OneOpenApiRecordPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("one/service/openapi")
@Api(tags = {"开放接口"})
public class OneOpenApiControlller {

    @Autowired
    private ServiceOpenApiConfigService serviceOpenApiConfigService;

    @Autowired
    private ScenicspotService scenicspotService;

    @Autowired
    private OneClientConfigService oneClientConfigService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private OneMemberService oneMemberService;

    @Autowired
    private OneRightsConfService oneRightsConfService;

    @Autowired
    private OneMerchantScenicspotRightsService oneMerchantScenicspotRightsService;

    @Autowired
    private OneOpenApiRecordService oneOpenApiRecordService;

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @ApiOperation(value = "检测景区是否已经绑定(返回true或false)")
    @RequestMapping(value = "/checkScenicspotBindStatas", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<String> scenicspotBindStatas(UserAware userAware) {
        ServiceOpenApiConfig serviceOpenApiConfig = serviceOpenApiConfigService.lambdaQuery().eq(ServiceOpenApiConfig::getMchId, userAware.getMchId()).one();
        if (Objects.nonNull(serviceOpenApiConfig)) {
            return SingleResponse.of("true");
        } else {
            return SingleResponse.of("false");
        }
    }

    @ApiOperation(value = "景区绑定")
    @RequestMapping(value = "/scenicspot/bind", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> scenicspotBind(@RequestBody @Valid ScenicspotBindParam param, UserAware userAware) {
        Integer count = serviceOpenApiConfigService.lambdaQuery().eq(ServiceOpenApiConfig::getMchId, userAware.getMchId()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9875", "重复绑定");
        }

        Scenicspot scenicspot = scenicspotService.getById(param.getScenicspotId());
        ServiceOpenApiConfig config = new ServiceOpenApiConfig();
        config.setScenicspotList(param.getScenicspotId() + "");
        config.setMchId(userAware.getMchId());
        config.setName(scenicspot.getName());
        config.setClientType("service_one_merchant_client");
        config.setAppId(param.getScenicspotId());
        config.insert();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "解析电子身份码信息")
    @RequestMapping(value = "parseEelectronicIdentityQrCode", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<EelectronicIdentityQrCodeVO> parseEelectronicIdentityQrCode(ParseEelectronicIdentityQrCodeParam param, UserAware userAware) {
        String[] splitData = param.getQrCode().split(":");
        String outAppId = splitData[0];
        OneClientConfig oneClientConfig = oneClientConfigService.lambdaQuery().eq(OneClientConfig::getOutAppId, outAppId).one();
        Long mchId = oneClientConfig.getMchId();
        String qrCodeJson = stringRedisTemplate.opsForValue().get(param.getQrCode());
        if (StrUtil.isBlank(qrCodeJson) || "invalid_data".equalsIgnoreCase(qrCodeJson)) {
            return SingleResponse.buildFailure("9874", "电子凭证码已失效");
        }
        ServiceOpenApiConfig serviceOpenApiConfig = serviceOpenApiConfigService.lambdaQuery().eq(ServiceOpenApiConfig::getMchId, userAware.getMchId()).one();
        stringRedisTemplate.opsForValue().set(param.getQrCode(), "invalid_data", 2, TimeUnit.SECONDS);
        EelectronicIdentityQrCodeVO vo = JSONUtil.toBean(qrCodeJson, EelectronicIdentityQrCodeVO.class);
        vo.setClientName(oneClientConfig.getName());
        Integer memberId = vo.getMemberId();
        OneMember oneMember = oneMemberService.getById(memberId);
        String identityCode = oneMember.getIdentityCode();
        if (StrUtil.isNotBlank(identityCode)) {
            List<String> identityCodeList = Arrays.stream(identityCode.split(",")).collect(Collectors.toList());
            List<IdentityRightsVO> identityRightsVOList = oneMerchantScenicspotRightsService.lambdaQuery()
                    .eq(OneMerchantScenicspotRights::getScenicspotId, serviceOpenApiConfig.getScenicspotList())
                    .eq(OneMerchantScenicspotRights::getMerchantId, mchId)
                    .list().stream().map(x -> {
                        return oneRightsConfService.getById(x.getRightsId());
                    }).filter(x -> {
                                if (x.getIdentityCodeList().equalsIgnoreCase("-1")) {
                                    return true;
                                }
                                List<String> rightsIdentityCode = Arrays.stream(x.getIdentityCodeList().split(",")).collect(Collectors.toList());
                                Set<String> ts = CollectionUtil.intersectionDistinct(rightsIdentityCode, identityCodeList);
                                return ts.size() > 0;
                            }
                    ).map(x -> {
                        IdentityRightsVO rightsVO = new IdentityRightsVO();
                        BeanUtils.copyProperties(x, rightsVO);
                        rightsVO.setId(x.getId());
                        return rightsVO;
                    }).collect(Collectors.toList());
            vo.setIdentityRightsList(identityRightsVOList);
        }

        OneOpenApiRecord openApiRecord = new OneOpenApiRecord();
        openApiRecord.setCreateTime(new Date());
        openApiRecord.setIdName(vo.getIdName());
        openApiRecord.setIdNo(vo.getIdCardNo());
        openApiRecord.setMobile(vo.getMobile());
        openApiRecord.setOneCleintAppId(outAppId);
        openApiRecord.setMchName(null);
        openApiRecord.setOneCleintName(oneClientConfig.getName());
        openApiRecord.setOneMchId(oneClientConfig.getMchId());
        openApiRecord.setOneCleintId(oneClientConfig.getId());
        openApiRecord.setScanClientAppId(serviceOpenApiConfig.getAppId());
        openApiRecord.setScanClientAppName(serviceOpenApiConfig.getName());
        openApiRecord.setScanMchId(userAware.getMchId());
        openApiRecord.setScanClientType("service_one_merchant_client");
        openApiRecord.setScenicspotId(Long.parseLong(serviceOpenApiConfig.getScenicspotList()));
        openApiRecord.setIdentityCode(oneMember.getIdentityCode());
        if (Objects.nonNull(vo.getIdentityRightsList())) {
            openApiRecord.setRightsInfo(JSONUtil.toJsonStr(vo.getIdentityRightsList()));
        }
        Scenicspot scenicspot = scenicspotService.getById(Long.parseLong(serviceOpenApiConfig.getScenicspotList()));
        if (Objects.nonNull(scenicspot)) {
            openApiRecord.setScenicspotName(scenicspot.getName());
        }
        openApiRecord.insert();
        vo.setId(openApiRecord.getId());
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "权益确认")
    @RequestMapping(value = "choose/rights", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> chooseRights(@RequestBody @Valid RightsConfirmParam param, UserAware userAware) {
        oneOpenApiRecordService.lambdaUpdate().
                set(OneOpenApiRecord::getRightsId, param.getRightsId())
                .eq(OneOpenApiRecord::getId, param.getId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "接口日志分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneOpenApiRecordPageVO>> page(OneOpenApiRecordPageQry pageQry, UserAware userAware) {
        pageQry.setScanMchId(userAware.getMchId());
        OneOpenApiRecordPageParam pageParam = new OneOpenApiRecordPageParam(pageQry);
        IPage<OneOpenApiRecordPageVO> pageData = oneOpenApiRecordService.paginateAndConvert(pageParam, this::toOneOpenApiRecordPageVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "接口日志详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneOpenApiRecordPageVO> detai(@PathVariable("id") Long id, UserAware userAware) {
        OneOpenApiRecord oneOpenApiRecord = oneOpenApiRecordService.getById(id);
        OneOpenApiRecordPageVO vo = toOneOpenApiRecordPageVO(oneOpenApiRecord);
        return SingleResponse.of(vo);
    }

    private OneOpenApiRecordPageVO toOneOpenApiRecordPageVO(OneOpenApiRecord record) {
        OneOpenApiRecordPageVO vo = new OneOpenApiRecordPageVO();
        BeanUtils.copyProperties(record, vo);
        if (StrUtil.isNotBlank(record.getIdentityCode())) {
            if (record.getIdentityCode().equalsIgnoreCase("-1")) {
                vo.setIdentityCode("全部");
            } else if (StrUtil.isNotBlank(record.getIdentityCode())) {
                ArrayList<String> identityCodeList = CollectionUtil.toList(record.getIdentityCode().split(","));
                List<OneUsableIdentity> identities = oneUsableIdentityService
                        .lambdaQuery()
                        .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
                String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                vo.setIdentityCode(identityNames);
            }
        }
        if (Objects.nonNull(record.getRightsId())) {
            vo.setChooseRights(oneRightsConfService.getById(record.getRightsId()));
        }
        return vo;
    }

}
