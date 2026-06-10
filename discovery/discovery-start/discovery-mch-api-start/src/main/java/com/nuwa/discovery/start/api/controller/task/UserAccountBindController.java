package com.nuwa.discovery.start.api.controller.task;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.discovery.command.query.UserBindAccountPageJoinQry;
import com.nuwa.discovery.start.api.controller.task.param.AccountAuditParam;
import com.nuwa.discovery.start.api.controller.task.param.ModifyAccountParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberAccountBind;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserAccountBindJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.AccountBindJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.service.MemberAccountBindService;
import com.nuwa.infrastructure.discovery.database.user.vo.UserAccountBindPageVO;
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

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("bind/account/")
@Api(tags = {"任务账号认证相关"})
public class UserAccountBindController {

    @Autowired
    private UserAccountBindJoinMapper userAccountBindJoinMapper;

    @Autowired
    private MemberAccountBindService memberAccountBindService;

    @ApiOperation(value = "账户认证记录")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserAccountBindPageVO>> page(UserBindAccountPageJoinQry query, UserAware userAware) {
        AccountBindJoinPageQuery queryPage = new AccountBindJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        Page<UserAccountBindPageVO> pageData = userAccountBindJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "用户认证账户列表")
    @RequestMapping(value = "/list/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MemberAccountBind>> accountList(@PathVariable("id") Long id, UserAware userAware) {
        List<MemberAccountBind> list = memberAccountBindService.lambdaQuery().eq(MemberAccountBind::getUserId,id).list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "修改操作")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> update(@RequestBody @Valid ModifyAccountParam form, UserAware userAware) {
        boolean ret = memberAccountBindService.lambdaUpdate()
                .set(MemberAccountBind::getNick, form.getNick())
                .set(MemberAccountBind::getUId, form.getUId())
                .set(MemberAccountBind::getAccountId, form.getAccountId())
                .set(MemberAccountBind::getFansCount, form.getFansCount())
                .set(MemberAccountBind::getSex, form.getSex())
                .set(MemberAccountBind::getRegion, form.getRegion())
                .set(MemberAccountBind::getInviteCode, form.getInviteCode())
                .eq(MemberAccountBind::getId, form.getId())
                .update();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "修改失败");
        }
        return SingleResponse.of("修改成功");
    }

    @ApiOperation(value = "审核操作")
    @RequestMapping(value = "/audit", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> auditProcess(@RequestBody @Valid AccountAuditParam form, UserAware userAware) {
        MemberAccountBind memberAccountBind = memberAccountBindService.getById(form.getId());
        if (form.getStatus().equals(2)) {
            memberAccountBind.setStatus(2);
        } else if (form.getStatus().equals(1)) {
            memberAccountBind.setStatus(1);
        }
        memberAccountBind.setUpdateTime(new Date());
        boolean ret = memberAccountBind.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "审核失败");
        }
        return SingleResponse.of("审核成功");
    }
}
