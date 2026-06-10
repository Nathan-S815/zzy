package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.ticket.command.one.query.MerchantScenicsportRightsPageQuery;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneOpenApiRecordPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneRightsConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantScenicspotRights;
import com.nuwa.infrastructure.ticket.database.one.entity.OneOpenApiRecord;
import com.nuwa.infrastructure.ticket.database.one.entity.OneRightsConf;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.mapper.join.MerchantScenicspotRightsJoinMapper;
import com.nuwa.infrastructure.ticket.database.one.mapper.join.query.MerchantScenicsportRightsJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.one.mapper.vo.MerchantScenicsportRightsPageVO;
import com.nuwa.infrastructure.ticket.database.one.param.OneOpenApiRecordPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneRightsConfPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.*;
import com.nuwa.ticket.start.api.controller.one.param.AddOneRightsConfParam;
import com.nuwa.ticket.start.api.controller.one.param.ModifyOneToolLinkSortNumParam;
import com.nuwa.ticket.start.api.controller.one.param.ScenicspotAddRightsParam;
import com.nuwa.ticket.start.api.controller.one.param.ScenicspotRemoveRightsParam;
import com.nuwa.ticket.start.api.controller.one.vo.MerchantOneRightsPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("one/merchant/rights")
@Api(tags = {"商户一码通权益接口"})
public class MerchantOneRightsControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneMerchantUsableIdentityService oneMerchantUsableIdentityService;

    @Autowired
    private OneRightsConfService oneRightsConfService;

    @Autowired
    private OneOpenApiRecordService oneOpenApiRecordService;

    @Autowired
    private OneMerchantScenicspotRightsService oneMerchantScenicspotRightsService;

    @Autowired
    private MerchantScenicspotRightsJoinMapper merchantScenicspotRightsJoinMapper;

    @ApiOperation(value = "通用权益新增或修改")
    @RequestMapping(value = "/addOrModify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> addOrModify(@RequestBody @Valid AddOneRightsConfParam param, UserAware userAware) {
        Integer count = oneRightsConfService.lambdaQuery()
                .eq(OneRightsConf::getMchId, userAware.getMchId())
                .eq(OneRightsConf::getTitle, param.getTitle())
                .ne(Objects.nonNull(param.getId()), OneRightsConf::getId, param.getId())
                .count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "权益名称重复");
        }

        if (param.getValidityMode().equalsIgnoreCase("range_date")) {
            if (Objects.isNull(param.getValidityBeginDate()) || Objects.isNull(param.getRightsType())) {
                return SingleResponse.buildFailure("9824", "有效期不能为空");
            }
        }

        OneRightsConf rightsConf = new OneRightsConf();
        BeanUtils.copyProperties(param, rightsConf);
        rightsConf.setMchId(userAware.getMchId());
        if (StrUtil.isBlank(param.getIdentityCodeList())) {
            rightsConf.setIdentityCodeList("-1");
        } else {
            rightsConf.setIdentityCodeList(param.getIdentityCodeList());
        }
        rightsConf.setSortNum(0);
        if (Objects.isNull(param.getScenicspotId())) {
            //全部景区
            rightsConf.setScenicspotId(-1L);
        }
        rightsConf.insertOrUpdate();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "通用权益详情")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneRightsConf> detail(@PathVariable("id") Long id, UserAware userAware) {
        OneRightsConf oneRightsConf = oneRightsConfService.getById(id);
        return SingleResponse.of(oneRightsConf);
    }

    @ApiOperation(value = "通用权益删除")
    @RequestMapping(value = "/{id}/removed", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<OneRightsConf> removed(@PathVariable("id") Long id, UserAware userAware) {
        oneRightsConfService.removeById(id);
        oneMerchantScenicspotRightsService.lambdaUpdate()
                .eq(OneMerchantScenicspotRights::getRightsId, id)
                .remove();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "/modifySortNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyOrderNum(@RequestBody @Valid ModifyOneToolLinkSortNumParam param, UserAware userAware) {
        oneRightsConfService.lambdaUpdate()
                .set(OneRightsConf::getSortNum, param.getSortNum())
                .eq(OneRightsConf::getId, param.getId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "通用权益分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantOneRightsPageVO>> page(OneRightsConfPageQry pageQry, UserAware userAware) {
        OneRightsConfPageParam pageParam = new OneRightsConfPageParam(pageQry);
        IPage<MerchantOneRightsPageVO> pageData = oneRightsConfService.paginateAndConvert(pageParam, this::toVO);
        pageData.getRecords().forEach(x -> {
            if (x.getIdentityCodeList().equalsIgnoreCase("-1")) {
                x.setIdentityCodeList("全部");
            } else if (StrUtil.isNotBlank(x.getIdentityCodeList())) {
                ArrayList<String> identityCodeList = CollectionUtil.toList(x.getIdentityCodeList().split(","));
                List<OneUsableIdentity> identities = oneUsableIdentityService
                        .lambdaQuery()
                        .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
                String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                x.setIdentityCodeList(identityNames);
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "景区权益添加")
    @RequestMapping(value = "/scenicspotAddRights", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> scenicspotAddRights(@RequestBody @Valid ScenicspotAddRightsParam param, UserAware userAware) {
        Integer count = oneMerchantScenicspotRightsService.lambdaQuery()
                .eq(OneMerchantScenicspotRights::getRightsId, param.getRightsId())
                .eq(OneMerchantScenicspotRights::getMerchantId, userAware.getMchId())
                .eq(OneMerchantScenicspotRights::getScenicspotId, param.getScenicspotId())
                .count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "重复添加");
        }
        OneMerchantScenicspotRights oneMerchantScenicspotRights = new OneMerchantScenicspotRights();
        BeanUtils.copyProperties(param, oneMerchantScenicspotRights);
        oneMerchantScenicspotRights.setMerchantId(userAware.getMchId());
        oneMerchantScenicspotRights.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "景区权益分页查询")
    @RequestMapping(value = "scenicsRights/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicsportRightsPageVO>> refundPage(MerchantScenicsportRightsPageQuery query, UserAware userAware) {
        MerchantScenicsportRightsJoinPageJoinQuery queryPage = new MerchantScenicsportRightsJoinPageJoinQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setMerchantId(userAware.getMchId());
        Page<MerchantScenicsportRightsPageVO> pageData = merchantScenicspotRightsJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            if (x.getIdentityCodeList().equalsIgnoreCase("-1")) {
                x.setIdentityCodeList("全部");
            } else if (StrUtil.isNotBlank(x.getIdentityCodeList())) {
                ArrayList<String> identityCodeList = CollectionUtil.toList(x.getIdentityCodeList().split(","));
                List<OneUsableIdentity> identities = oneUsableIdentityService
                        .lambdaQuery()
                        .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
                String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                x.setIdentityCodeList(identityNames);
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "景区权益移除")
    @RequestMapping(value = "/scenicsRights/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyOrderNum(@RequestBody @Valid ScenicspotRemoveRightsParam param, UserAware userAware) {
        oneMerchantScenicspotRightsService.removeById(param.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "接口日志分页")
    @RequestMapping(value = "/apiRecord/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneOpenApiRecord>> apiRecordPage(OneOpenApiRecordPageQry pageQry, UserAware userAware) {
        pageQry.setOneMchId(userAware.getMchId());
        OneOpenApiRecordPageParam pageParam = new OneOpenApiRecordPageParam(pageQry);
        IPage<OneOpenApiRecord> pageData = oneOpenApiRecordService.paginateByParam(pageParam);
        pageData.getRecords().forEach(x -> {
            if (StrUtil.isNotBlank(x.getIdentityCode())) {
                if (x.getIdentityCode().equalsIgnoreCase("-1")) {
                    x.setIdentityCode("全部");
                } else if (StrUtil.isNotBlank(x.getIdentityCode())) {
                    ArrayList<String> identityCodeList = CollectionUtil.toList(x.getIdentityCode().split(","));
                    List<OneUsableIdentity> identities = oneUsableIdentityService
                            .lambdaQuery()
                            .in(OneUsableIdentity::getIdentityCode, identityCodeList)
                            .list();
                    String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                    x.setIdentityCode(identityNames);
                }
            }
        });
        return SingleResponse.of(pageData);
    }

    public MerchantOneRightsPageVO toVO(OneRightsConf entity) {
        MerchantOneRightsPageVO vo = new MerchantOneRightsPageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
