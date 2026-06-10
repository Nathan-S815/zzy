package com.nuwa.ticket.start.api.controller.diy;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.diy.entity.DiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.entity.MerchantDiyTemplate;
import com.nuwa.infrastructure.ticket.database.diy.service.DiyTemplateService;
import com.nuwa.infrastructure.ticket.database.diy.service.MerchantDiyTemplateService;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.controller.diy.param.*;
import com.nuwa.ticket.start.api.controller.diy.vo.DiyTemplateVO;
import com.nuwa.ticket.start.api.controller.diy.vo.TemplateJsonVO;
import com.nuwa.ticket.start.api.controller.dto.ScenicspotBaseInfoDTO;
import com.nuwa.ticket.start.api.controller.vo.ScenicspotDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("diy")
@Api(tags = {"装修管理相关"})
public class DiyController {

    @Autowired
    private MerchantDiyTemplateService merchantDiyTemplateService;

    @Autowired
    private DiyTemplateService diyTemplateService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private ScenicspotService scenicspotService;

    @ApiOperation(value = "获取商户应用模板列表")
    @RequestMapping(value = "/getAppTemplateList", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getAppTemplateList(GetAppTemplateListParam param, UserAware userAware) {
        List<DiyTemplate> diyTemplates = diyTemplateService.lambdaQuery().eq(StrUtil.isNotBlank(param.getType()), DiyTemplate::getType, param.getType()).list();
        List<DiyTemplateVO> diyTemplateVOList = diyTemplates.stream().map(x -> {
            DiyTemplateVO vo = new DiyTemplateVO();
            BeanUtils.copyProperties(x, vo);
            vo.setDefaultFlag("default".equalsIgnoreCase(x.getMark()));
            return vo;
        }).collect(Collectors.toList());
        return SingleResponse.of(diyTemplateVOList);
    }

    @ApiOperation(value = "根据id获取模板数据")
    @RequestMapping(value = "/getTemplateValueById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<TemplateJsonVO> getTemplateJsonById(@PathVariable("id") Long id, UserAware userAware) {
        DiyTemplate diyTemplate = diyTemplateService.getById(id);
        if (Objects.nonNull(diyTemplate)) {
            String jsonVal = diyTemplate.getValue();
            if (JSONUtil.isJson(jsonVal)) {
                return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseObj(jsonVal), null));
            } else {
                return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseArray(jsonVal), null));
            }
        }
        return null;
    }

    @ApiOperation(value = "获取商户应用模板JSON")
    @RequestMapping(value = "/getAppDefaultTemplate", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<TemplateJsonVO> getAppDefaultTemplateJson(GetAppDefaultTemplateParam param, UserAware userAware) {
        String mark = "default";
        List<MerchantDiyTemplate> merchantDiyTemplates = merchantDiyTemplateService.lambdaQuery()
                .eq(MerchantDiyTemplate::getMerchantId, userAware.getMchId())
                .eq(MerchantDiyTemplate::getAppId, param.getAppId())
                .eq(MerchantDiyTemplate::getType, param.getType())
                .eq(MerchantDiyTemplate::getSnapshoot, "LOCAL")
                .list();

        if (merchantDiyTemplates.isEmpty()) {
            DiyTemplate diyTemplate = diyTemplateService.lambdaQuery()
                    .eq(DiyTemplate::getType, param.getType())
                    .eq(DiyTemplate::getMark, mark)
                    .one();
            if (Objects.nonNull(diyTemplate)) {
                String jsonVal = diyTemplate.getValue();
                if (JSONUtil.isJson(jsonVal)) {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseObj(jsonVal), null));
                } else {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseArray(jsonVal), null));
                }
            }
        } else {
            MerchantDiyTemplate merchantDiyTemplate = merchantDiyTemplates.get(0);
            if (Objects.nonNull(merchantDiyTemplate)) {
                String jsonVal = merchantDiyTemplate.getValue();
                if (JSONUtil.isJson(jsonVal)) {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseObj(jsonVal), merchantDiyTemplate.getId().longValue()));
                } else {
                    return SingleResponse.of(new TemplateJsonVO(JSONUtil.parseArray(jsonVal), merchantDiyTemplate.getId().longValue()));
                }
            }
        }
        return null;
    }

    @ApiOperation(value = "保存装修")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> saveTemplate(@RequestBody SaveTemplateParam param, UserAware userAware) {
        List<MerchantDiyTemplate> merchantDiyTemplates = merchantDiyTemplateService.lambdaQuery()
                .eq(MerchantDiyTemplate::getMerchantId, userAware.getMchId())
                .eq(MerchantDiyTemplate::getAppId, param.getAppId())
                .eq(MerchantDiyTemplate::getType, param.getType())
                .eq(MerchantDiyTemplate::getMark, "default")
                .eq(MerchantDiyTemplate::getSnapshoot, "LOCAL")
                .list();
        MerchantDiyTemplate merchantDiyTemplate = new MerchantDiyTemplate();

        if (merchantDiyTemplates.size() > 0) {
            MerchantDiyTemplate oldMerchantDiyTemplate = merchantDiyTemplates.get(0);
            merchantDiyTemplate = oldMerchantDiyTemplate;
            merchantDiyTemplate.setValue(JSONUtil.toJsonStr(param.getValue()));
            merchantDiyTemplate.setMark("default");
            merchantDiyTemplate.setId(oldMerchantDiyTemplate.getId());
            merchantDiyTemplate.setLastUpdateById(userAware.getUserId() + "");
            merchantDiyTemplate.setLastUpdateTime(new Date());
            merchantDiyTemplate.setLastUpdateByName(userAware.getUserName());
        } else {
            merchantDiyTemplate.setMerchantId(userAware.getMchId());
            merchantDiyTemplate.setValue(JSONUtil.toJsonStr(param.getValue()));
            merchantDiyTemplate.setAppId(param.getAppId());
            merchantDiyTemplate.setType(param.getType());
            merchantDiyTemplate.setMark("default");
            merchantDiyTemplate.setSnapshoot("LOCAL");
            merchantDiyTemplate.setCreateTime(new Date());
        }
        boolean save = merchantDiyTemplate.insertOrUpdate();
        if (save) {
            return SingleResponse.of(merchantDiyTemplate.getId());
        }
        return SingleResponse.buildFailure("9887", "保存失败");
    }

    @ApiOperation(value = "发布")
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> publish(@RequestBody PublishTemplateParam param, UserAware userAware) {
        MerchantDiyTemplate merchantDiyTemplate = merchantDiyTemplateService.getById(param.getId());
        List<MerchantDiyTemplate> publicTemplates = merchantDiyTemplateService.lambdaQuery()
                .eq(MerchantDiyTemplate::getMerchantId, userAware.getMchId())
                .eq(MerchantDiyTemplate::getAppId, merchantDiyTemplate.getAppId())
                .eq(MerchantDiyTemplate::getType, merchantDiyTemplate.getType())
                .eq(MerchantDiyTemplate::getMark, "default")
                .eq(MerchantDiyTemplate::getSnapshoot, "PUBLIC")
                .list();
        MerchantDiyTemplate publishTemplate = new MerchantDiyTemplate();
        BeanUtils.copyProperties(merchantDiyTemplate, publishTemplate);
        if (publicTemplates.isEmpty()) {
            publishTemplate.setId(null);
        } else {
            publishTemplate.setId(publicTemplates.get(0).getId());
        }
        publishTemplate.setSnapshoot("PUBLIC");
        publishTemplate.setLastUpdateTime(new Date());
        publishTemplate.setPublishTime(new Date());
        boolean save = publishTemplate.insertOrUpdate();
        if (save) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9887", "发布失败");
    }

    @ApiOperation(value = "应用发布")
    @RequestMapping(value = "/appPublish", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> appPublish(@RequestBody AppPublishParam param, UserAware userAware) {
        List<MerchantDiyTemplate> batchMerchantDiyPage = new ArrayList<>();
        Long appId = param.getAppId();
        List<String> appPageList = new ArrayList<>();
        appPageList.add("DIYVIEW_INDEX");
        appPageList.add("DIYVIEW_USER_CENTER");
        appPageList.add("DIYVIEW_BOTTOM_BAR");
        appPageList.forEach(type -> {
            MerchantDiyTemplate merchantDiyTemplate = merchantDiyTemplateService.lambdaQuery()
                    .eq(MerchantDiyTemplate::getMerchantId, userAware.getMchId())
                    .eq(MerchantDiyTemplate::getAppId, appId)
                    .eq(MerchantDiyTemplate::getType, type)
                    .eq(MerchantDiyTemplate::getMark, "default")
                    .eq(MerchantDiyTemplate::getSnapshoot, "LOCAL")
                    .last("limit 1")
                    .one();
            if (Objects.isNull(merchantDiyTemplate)) {
                String mark = "default";
                DiyTemplate diyTemplate = diyTemplateService.lambdaQuery()
                        .eq(DiyTemplate::getType, type)
                        .eq(DiyTemplate::getMark, mark)
                        .one();
                String jsonVal = diyTemplate.getValue();
                MerchantDiyTemplate newTemplateByDefaultPublic = new MerchantDiyTemplate();
                newTemplateByDefaultPublic.setMerchantId(userAware.getMchId());
                newTemplateByDefaultPublic.setValue(jsonVal);
                newTemplateByDefaultPublic.setAppId(param.getAppId());
                newTemplateByDefaultPublic.setType(type);
                newTemplateByDefaultPublic.setMark("default");
                newTemplateByDefaultPublic.setSnapshoot("PUBLIC");
                newTemplateByDefaultPublic.setCreateTime(new Date());
                batchMerchantDiyPage.add(newTemplateByDefaultPublic);

                MerchantDiyTemplate newTemplateByDefaultLocal = new MerchantDiyTemplate();
                BeanUtils.copyProperties(newTemplateByDefaultPublic, newTemplateByDefaultLocal);
                newTemplateByDefaultLocal.setSnapshoot("LOCAL");
                batchMerchantDiyPage.add(newTemplateByDefaultLocal);
            } else {
                MerchantDiyTemplate publishTemplate = new MerchantDiyTemplate();
                BeanUtils.copyProperties(merchantDiyTemplate, publishTemplate);
                publishTemplate.setId(merchantDiyTemplate.getId());
                publishTemplate.setSnapshoot("PUBLIC");
                publishTemplate.setLastUpdateTime(new Date());
                publishTemplate.setPublishTime(new Date());
                MerchantDiyTemplate merchantPublicDiyTemplate = merchantDiyTemplateService.lambdaQuery()
                        .eq(MerchantDiyTemplate::getMerchantId, userAware.getMchId())
                        .eq(MerchantDiyTemplate::getAppId, appId)
                        .eq(MerchantDiyTemplate::getType, type)
                        .eq(MerchantDiyTemplate::getMark, "default")
                        .eq(MerchantDiyTemplate::getSnapshoot, "PUBLIC")
                        .last("limit 1")
                        .one();
                if (Objects.nonNull(merchantPublicDiyTemplate)) {
                    publishTemplate.setId(merchantPublicDiyTemplate.getId());
                } else {
                    publishTemplate.setId(null);
                }
                batchMerchantDiyPage.add(publishTemplate);
            }
        });
        boolean b = merchantDiyTemplateService.saveOrUpdateBatch(batchMerchantDiyPage);
        if (b) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9887", "发布失败");
    }

    @ApiOperation(value = "根据appId获取POI信息")
    @RequestMapping(value = "/getPoiInfoByAppId/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ScenicspotDetailVO> detail(@PathVariable("id") Integer id, UserAware userAware) {
        MerchantAppBaseConf merchantSupplierConfig = merchantAppBaseConfService.getById(id);
        Assert.isTrue(userAware.getMchId().equals(merchantSupplierConfig.getMchId()));
        Long poiId = merchantSupplierConfig.getPoiId();
        if (Objects.isNull(poiId)) {
            return SingleResponse.buildFailure("9876", "无法获取poi信息");
        }
        Scenicspot scenicspot = scenicspotService.getById(id);
        ScenicspotBaseInfoDTO baseInfo = new ScenicspotBaseInfoDTO();
        BeanUtils.copyProperties(scenicspot, baseInfo);
        ScenicspotDetailVO detailVO = new ScenicspotDetailVO();
        detailVO.setBaseInfo(baseInfo);
        return SingleResponse.of(detailVO);
    }
}
