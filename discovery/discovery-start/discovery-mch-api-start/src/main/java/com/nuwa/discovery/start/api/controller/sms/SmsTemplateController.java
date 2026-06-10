package com.nuwa.discovery.start.api.controller.sms;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.discovery.dto.clientobject.sms.qry.SmsTemplatePageQry;
import com.nuwa.client.discovery.dto.clientobject.user.qry.MemberPageQry;
import com.nuwa.discovery.start.api.controller.sms.param.ModifySmsTemplateParam;
import com.nuwa.discovery.start.api.controller.sms.param.SaveSmsTemplateParam;
import com.nuwa.discovery.start.api.controller.sms.vo.SmsTemplateVO;
import com.nuwa.discovery.start.api.controller.task.param.ModifyTaskWeightParam;
import com.nuwa.discovery.start.api.dingtalk.DingtalkService;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.sms.entity.SmsTemplate;
import com.nuwa.infrastructure.discovery.database.sms.param.SmsTemplatePageParam;
import com.nuwa.infrastructure.discovery.database.sms.service.SmsTemplateService;
import com.nuwa.infrastructure.discovery.database.task.entity.ScenicTask;
import com.nuwa.infrastructure.discovery.database.user.entity.Member;
import com.nuwa.infrastructure.discovery.database.user.param.MemberPageParam;
import com.nuwa.infrastructure.discovery.database.user.service.MemberService;
import com.nuwa.infrastructure.discovery.enums.AlertSmsEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("sms/template/")
@Api(tags = {"短信模板管理"})
public class SmsTemplateController {

    @Autowired
    private SmsTemplateService smsTemplateService;

    @Autowired
    private DingtalkService dingtalkService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody @Valid SaveSmsTemplateParam form, UserAware userAware) {
        SmsTemplate smsTemplate = new SmsTemplate();
        smsTemplate.setCreateTime(new Date());
        smsTemplate.setMchId(userAware.getMchId());
        BeanUtils.copyProperties(form, smsTemplate);
        boolean insert = smsTemplate.insert();
        if (!insert) {
            log.error("save sms template failed.");
            return SingleResponse.buildFailure("904", "新增短信模板失败");
        }
        log.info("save sms template [id:{}] success.", smsTemplate.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/{id}/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@PathVariable("id") Long id, @RequestBody @Valid ModifySmsTemplateParam form, UserAware userAware) {
        SmsTemplate smsTemplate = smsTemplateService.getById(id);
        smsTemplate.setCreateTime(new Date());
        smsTemplate.setMchId(userAware.getMchId());
        BeanUtils.copyProperties(form, smsTemplate);
        smsTemplate.setLastUpdateTime(new Date());
        smsTemplate.setLastUpdateById(userAware.getUserId() + "");
        smsTemplate.setLastUpdateByName(userAware.getUserName());
        boolean update = smsTemplate.updateById();
        if (!update) {
            log.error("update sms template failed.");
            return SingleResponse.buildFailure("904", "修改短信模板失败");
        }
        log.info("update sms template [id:{}] success.", smsTemplate.getId());
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@PathVariable("id") Long id, UserAware userAware) {
        boolean remove = smsTemplateService.removeById(id);
        if (!remove) {
            log.error("remove sms template failed.");
            return SingleResponse.buildFailure("904", "修改短信模板失败");
        }
        log.info("remove sms template [id:{}] success.", id);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<SmsTemplate> detail(@PathVariable("id") Long id, UserAware userAware) {
        SmsTemplate smsTemplate = smsTemplateService.getById(id);
        return SingleResponse.of(smsTemplate);
    }

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<SmsTemplateVO>> page(SmsTemplatePageQry qry, UserAware userAware) {
        SmsTemplatePageParam smsTemplatePageParam = new SmsTemplatePageParam(qry);
        IPage<SmsTemplateVO> iPage = smsTemplateService.paginateAndConvert(smsTemplatePageParam,this::toVO);
        return SingleResponse.of(iPage);
    }

    @ApiOperation(value = "消息业务类型列表")
    @RequestMapping(value = "/bizCode/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<BizCodeVO>> listBizCode(SmsTemplatePageQry qry, UserAware userAware) {
        List<BizCodeVO> bizCodeVOList = Arrays.stream(AlertSmsEnum.values()).map(x -> {
            return new BizCodeVO(x.getMessage(), x.getBizCode(), x.getParamName());
        }).collect(Collectors.toList());
        return SingleResponse.of(bizCodeVOList);
    }

    private SmsTemplateVO toVO(SmsTemplate smsTemplate) {
        SmsTemplateVO vo = new SmsTemplateVO();
        BeanUtils.copyProperties(smsTemplate,vo);
        AlertSmsEnum bizCodeEnum = AlertSmsEnum.getByBizCode(smsTemplate.getBizCode());
        if (Objects.nonNull(bizCodeEnum)) {
            vo.setBizName(bizCodeEnum.getMessage());
        }
        return vo;
    }

    @Data
    @AllArgsConstructor
    public static class BizCodeVO {
        private String name;
        private String bizCode;
        private String paramName;
    }
}
