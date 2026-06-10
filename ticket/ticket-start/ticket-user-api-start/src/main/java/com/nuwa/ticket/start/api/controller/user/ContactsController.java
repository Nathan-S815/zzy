package com.nuwa.ticket.start.api.controller.user;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.notice.qry.NoticeInfoPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.member.entity.ContactsInfo;
import com.nuwa.infrastructure.ticket.database.member.service.ContactsInfoService;
import com.nuwa.infrastructure.ticket.database.notice.entity.NoticeInfo;
import com.nuwa.infrastructure.ticket.database.notice.param.NoticeInfoPageParam;
import com.nuwa.infrastructure.ticket.database.notice.service.NoticeInfoService;
import com.nuwa.ticket.start.api.controller.notice.param.GetNoticeDetailParam;
import com.nuwa.ticket.start.api.controller.notice.param.GetNoticeTopParam;
import com.nuwa.ticket.start.api.controller.user.param.AddContactParam;
import com.nuwa.ticket.start.api.controller.user.param.ModifyContactParam;
import com.nuwa.ticket.start.api.controller.user.param.RemoveContactParam;
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
@RequestMapping("contacts")
@Api(tags = {"常用联系人管理"})
public class ContactsController {

    @Autowired
    private ContactsInfoService contactsInfoService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody @Valid AddContactParam param, UserAware userAware) {
        ContactsInfo contactsInfo = new ContactsInfo();
        BeanUtils.copyProperties(param, contactsInfo);
        contactsInfo.setCreateTime(new Date());
        contactsInfo.setUserId(userAware.getUserId());
        boolean insert = contactsInfo.insert();
        if (insert) {
            if (Objects.nonNull(param.getDefaultFlag()) && param.getDefaultFlag().equals(1)) {
                boolean update = contactsInfoService.lambdaUpdate()
                        .set(ContactsInfo::getDefaultFlag, 0)
                        .eq(ContactsInfo::getUserId, userAware.getUserId())
                        .ne(ContactsInfo::getId, contactsInfo.getId())
                        .update();
            }
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "新增联系人失败");
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid ModifyContactParam param, UserAware userAware) {
        ContactsInfo contactsInfo = contactsInfoService.getById(param.getId());
        BeanUtils.copyProperties(param, contactsInfo);
        contactsInfo.setCreateTime(new Date());
        contactsInfo.setUserId(userAware.getUserId());
        boolean update = contactsInfo.updateById();
        if (update) {
            if (Objects.nonNull(param.getDefaultFlag()) && param.getDefaultFlag().equals(1)) {
                boolean updateDefault = contactsInfoService.lambdaUpdate()
                        .set(ContactsInfo::getDefaultFlag, 0)
                        .eq(ContactsInfo::getUserId, userAware.getUserId())
                        .ne(ContactsInfo::getId, contactsInfo.getId())
                        .update();
            }
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "修改联系人失败");
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@RequestBody @Valid RemoveContactParam param, UserAware userAware) {
        boolean remove = contactsInfoService.lambdaUpdate()
                .eq(ContactsInfo::getUserId, userAware.getUserId())
                .eq(ContactsInfo::getId, param.getId())
                .remove();
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("9875", "移除联系人失败");
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<ContactsInfo> getById(@PathVariable("id") Long id, UserAware userAware) {
        ContactsInfo contactsInfo = contactsInfoService.getById(id);
        Assert.isTrue(contactsInfo.getUserId().equals(userAware.getUserId()));
        return SingleResponse.of(contactsInfo);
    }

    @ApiOperation(value = "列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<ContactsInfo>> list(UserAware userAware) {
        List<ContactsInfo> list = contactsInfoService.lambdaQuery()
                .eq(ContactsInfo::getUserId, userAware.getUserId())
                .orderByDesc(ContactsInfo::getDefaultFlag)
                .list();
        return SingleResponse.of(list);
    }

}
