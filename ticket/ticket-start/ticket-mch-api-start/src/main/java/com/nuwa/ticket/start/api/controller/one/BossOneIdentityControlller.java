package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneOpenApiRecordPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUsableIdentityPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneOpenApiRecord;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.param.OneOpenApiRecordPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneUsableIdentityPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.OneOpenApiRecordService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import com.nuwa.ticket.start.api.controller.one.param.AddUsableIdentityParam;
import com.nuwa.ticket.start.api.controller.one.param.ListUsableIdentityParam;
import com.nuwa.ticket.start.api.controller.one.param.ModifyUsableIdentityParam;
import com.nuwa.ticket.start.api.controller.one.param.OnOrOffIdentityParam;
import com.nuwa.ticket.start.api.controller.one.vo.BossOneUsableIdentityPageVO;
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
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("one/boss/identity")
@Api(tags = {"Boss一码通身份认证接口"})
public class BossOneIdentityControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneOpenApiRecordService oneOpenApiRecordService;

    @ApiOperation(value = "新增身份")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody @Valid AddUsableIdentityParam param, UserAware userAware) {
        Integer count = oneUsableIdentityService.lambdaQuery().eq(OneUsableIdentity::getIdentityCode, param.getIdentityCode()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "编码不能重复");
        }
        OneUsableIdentity usableIdentity = new OneUsableIdentity();
        BeanUtils.copyProperties(param, usableIdentity);
        usableIdentity.setMchId(userAware.getMchId());
        usableIdentity.setStatus("on");
        usableIdentity.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "身份详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneUsableIdentity> page(@PathVariable("id") Long id, UserAware userAware) {
        OneUsableIdentity oneUsableIdentity = oneUsableIdentityService.getById(id);
        return SingleResponse.of(oneUsableIdentity);
    }

    @ApiOperation(value = "修改身份")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid ModifyUsableIdentityParam param, UserAware userAware) {
        oneUsableIdentityService.lambdaUpdate()
                .set(StrUtil.isNotBlank(param.getIconId()),OneUsableIdentity::getIconId,param.getIconId())
                .set(StrUtil.isNotBlank(param.getLinkUrl()),OneUsableIdentity::getLinkUrl,param.getLinkUrl())
                .set(StrUtil.isNotBlank(param.getName()),OneUsableIdentity::getName,param.getName())
                .set(StrUtil.isNotBlank(param.getIntroduction()),OneUsableIdentity::getIntroduction,param.getIntroduction())
                .eq(OneUsableIdentity::getId, param.getId())
                .update();

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "开启")
    @RequestMapping(value = "/{id}/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@PathVariable("id") Long id, UserAware userAware) {
        oneUsableIdentityService.lambdaUpdate()
                .set(OneUsableIdentity::getStatus, "on")
                .eq(OneUsableIdentity::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "关闭")
    @RequestMapping(value = "/{id}/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@PathVariable("id") Long id, UserAware userAware) {
        oneUsableIdentityService.lambdaUpdate()
                .set(OneUsableIdentity::getStatus, "off")
                .eq(OneUsableIdentity::getId, id)
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "根据code获取身份信息列表")
    @RequestMapping(value = "/listByCodes", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<List<OneUsableIdentity>> listByCodes(@RequestBody ListUsableIdentityParam pageQry, UserAware userAware) {
        if (StrUtil.isNotBlank(pageQry.getCodes())) {
            List<OneUsableIdentity> list = oneUsableIdentityService.lambdaQuery().in(OneUsableIdentity::getIdentityCode, ListUtil.toList(pageQry.getCodes().split(","))).list();
            return SingleResponse.of(list);
        } else {
            return SingleResponse.of(new ArrayList<>());
        }
    }

    @ApiOperation(value = "身份分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<BossOneUsableIdentityPageVO>> page(OneUsableIdentityPageQry pageQry, UserAware userAware) {
        OneUsableIdentityPageParam pageParam = new OneUsableIdentityPageParam(pageQry);
        IPage<BossOneUsableIdentityPageVO> pageData = oneUsableIdentityService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "接口日志分页")
    @RequestMapping(value = "/apiRecord/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneOpenApiRecord>> apiRecord(OneOpenApiRecordPageQry pageQry, UserAware userAware) {
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
                            .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
                    String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                    x.setIdentityCode(identityNames);
                }
            }
        });
        return SingleResponse.of(pageData);
    }

    public BossOneUsableIdentityPageVO toVO(OneUsableIdentity entity) {
        BossOneUsableIdentityPageVO vo = new BossOneUsableIdentityPageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
