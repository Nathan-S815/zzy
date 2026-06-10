package com.nuwa.ticket.start.api.controller.product;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.product.qry.MerchantDayTimeTemplatePageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.article.param.ArticlePageParam;
import com.nuwa.infrastructure.ticket.database.member.entity.ContactsInfo;
import com.nuwa.infrastructure.ticket.database.operatelog.entity.OperateLog;
import com.nuwa.infrastructure.ticket.database.operatelog.service.OperateLogService;
import com.nuwa.infrastructure.ticket.database.product.entity.*;
import com.nuwa.infrastructure.ticket.database.product.mapper.MerchantProductJoinMapper;
import com.nuwa.infrastructure.ticket.database.product.param.MerchantDayTimeTemplatePageParam;
import com.nuwa.infrastructure.ticket.database.product.service.*;
import com.nuwa.infrastructure.ticket.database.scenicspot.service.ScenicspotService;
import com.nuwa.ticket.start.api.biz.ProductBiz;
import com.nuwa.ticket.start.api.constants.LogRecordPrefixConstant;
import com.nuwa.ticket.start.api.controller.articel.vo.ArticleInfoPageVO;
import com.nuwa.ticket.start.api.controller.dto.*;
import com.nuwa.ticket.start.api.controller.product.param.AddDayTimeTemplateParam;
import com.nuwa.ticket.start.api.controller.product.param.ModifyDayTimeTemplateParam;
import com.nuwa.ticket.start.api.controller.product.param.RemoveDayTimeTemplateParam;
import com.nuwa.ticket.start.api.controller.product.vo.DayTimeTemplateVO;
import com.nuwa.ticket.start.api.controller.product.vo.MerchantDayTimeTemplateVO;
import com.nuwa.ticket.start.api.controller.product.vo.ScenicspotProductDetailVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("/supplier/daytime/template")
@Api(tags = {"供应商场次模板管理"})
public class SupplierDayTimeTemplateController {
    @Autowired
    private MerchantDayTimeTemplateService merchantDayTimeTemplateService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody @Valid AddDayTimeTemplateParam param, UserAware userAware) {
        MerchantDayTimeTemplate merchantDayTimeTemplate = new MerchantDayTimeTemplate();
        BeanUtils.copyProperties(param, merchantDayTimeTemplate);
        Assert.isTrue(param.getDayTimeList().size() > 0);
        merchantDayTimeTemplate.setCreateTime(new Date());
        List<DayTimeTemplateVO> dayTimeList = param.getDayTimeList();
        String jsonStr = JSONUtil.toJsonStr(dayTimeList);
        merchantDayTimeTemplate.setTemplateData(jsonStr);
        merchantDayTimeTemplate.setMchId(userAware.getMchId());
        boolean insert = merchantDayTimeTemplate.insert();
        if (insert) {

            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "新增模板失败");
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid ModifyDayTimeTemplateParam param, UserAware userAware) {
        MerchantDayTimeTemplate merchantDayTimeTemplate = merchantDayTimeTemplateService.getById(param.getId());
        BeanUtils.copyProperties(param, merchantDayTimeTemplate);
        merchantDayTimeTemplate.setLastUpdateTime(new Date());
        merchantDayTimeTemplate.setLastUpdateById(userAware.getUserId() + "");
        List<DayTimeTemplateVO> dayTimeList = param.getDayTimeList();
        String jsonStr = JSONUtil.toJsonStr(dayTimeList);
        merchantDayTimeTemplate.setTemplateData(jsonStr);
        boolean update = merchantDayTimeTemplate.updateById();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "修改模板失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@RequestBody @Valid RemoveDayTimeTemplateParam param, UserAware userAware) {
        boolean remove = merchantDayTimeTemplateService.lambdaUpdate()
                .eq(MerchantDayTimeTemplate::getMchId, userAware.getMchId())
                .eq(MerchantDayTimeTemplate::getId, param.getId())
                .remove();
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "移除模板失败");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantDayTimeTemplateVO> getById(@PathVariable("id") Long id, UserAware userAware) {
        MerchantDayTimeTemplate merchantDayTimeTemplate = merchantDayTimeTemplateService.getById(id);
        Assert.isTrue(merchantDayTimeTemplate.getMchId().equals(userAware.getMchId()));
        MerchantDayTimeTemplateVO vo = this.toVO(merchantDayTimeTemplate);
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MerchantDayTimeTemplateVO>> list(MerchantDayTimeTemplatePageQry pageQry, UserAware userAware) {
        MerchantDayTimeTemplatePageParam pageParam = new MerchantDayTimeTemplatePageParam(pageQry);
        IPage<MerchantDayTimeTemplateVO> pageData = merchantDayTimeTemplateService.paginateAndConvert(pageParam, this::toVO);
        return SingleResponse.of(pageData);
    }

    public MerchantDayTimeTemplateVO toVO(MerchantDayTimeTemplate dayTimeTemplate) {
        MerchantDayTimeTemplateVO vo = new MerchantDayTimeTemplateVO();
        BeanUtils.copyProperties(dayTimeTemplate, vo);
        vo.setTitle(dayTimeTemplate.getTitle());
        String templateData = dayTimeTemplate.getTemplateData();
        List<DayTimeTemplateVO> dayTimeTemplateVOList = JSONUtil.toList(JSONUtil.parseArray(templateData), DayTimeTemplateVO.class);
        vo.setDayTimeList(dayTimeTemplateVOList);
        return vo;
    }

}
