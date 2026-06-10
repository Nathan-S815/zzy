package com.nuwa.discovery.start.api.controller.task;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.discovery.command.query.MchTaskApplyPageJoinQry;
import com.nuwa.app.discovery.command.query.MchTaskPrizePageJoinQry;
import com.nuwa.client.discovery.dto.domainevent.UserPrizeAuditEvent;
import com.nuwa.client.discovery.dto.domainevent.UserTaskApplyEvent;
import com.nuwa.discovery.start.api.controller.task.param.*;
import com.nuwa.discovery.start.api.excel.UserTaskApplyExcelVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.common.event.DomainEventPublisher;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskApplyJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskPrizeJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.UserTaskJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeService;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskApplyPageVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizeDetailVO;
import com.nuwa.infrastructure.discovery.database.user.vo.UserTaskPrizePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("user/task")
@Api(tags = {"任务报名相关"})
public class UserTaskController {

    @Autowired
    private UserTaskApplyJoinMapper userTaskApplyJoinMapper;

    @Autowired
    private UserTaskPrizeJoinMapper userTaskPrizeJoinMapper;

    @Autowired
    private MemberTaskPrizeService memberTaskPrizeService;

    @Autowired
    private DomainEventPublisher domainEventPublisher;

    @ApiOperation(value = "任务报名记录")
    @RequestMapping(value = "/{taskId}/applyPage", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserTaskApplyPageVO>> page(@PathVariable("taskId") Long taskId, MchTaskApplyPageJoinQry query, UserAware userAware) {
        UserTaskJoinPageQuery queryPage = new UserTaskJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setTaskId(taskId);
        Page<UserTaskApplyPageVO> pageData = userTaskApplyJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "任务报名记录导出")
    @RequestMapping(value = "/{taskId}/applyPage/export", method = RequestMethod.GET)
    public void exportApplyData(@PathVariable("taskId") Long taskId, MchTaskApplyPageJoinQry query, UserAware userAware, HttpServletResponse response) throws IOException {
        UserTaskJoinPageQuery queryPage = new UserTaskJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setTaskId(taskId);
        queryPage.setLimit(Integer.MAX_VALUE);
        Page<UserTaskApplyPageVO> pageData = userTaskApplyJoinMapper.paginateByQuery(queryPage);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("任务报名记录", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        List<UserTaskApplyExcelVO> excelVOList = pageData.getRecords().stream().map(x -> {
            UserTaskApplyExcelVO vo = new UserTaskApplyExcelVO();
            BeanUtils.copyProperties(x, vo);
            return vo;
        }).collect(Collectors.toList());
        EasyExcel.write(response.getOutputStream(), UserTaskApplyExcelVO.class).sheet("任务报名记录").doWrite(excelVOList);
    }

    @ApiOperation(value = "权益认领记录分页查询")
    @RequestMapping(value = "/prize/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserTaskPrizePageVO>> prizePage(MchTaskPrizePageJoinQry query, UserAware userAware) {
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setCreateById(userAware.getMchUserId() + "");
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setShowWaitStatus(false);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "权益认领记录导出")
    @RequestMapping(value = "/prize/export", method = RequestMethod.GET)
    public void exportPrizeData(MchTaskPrizePageJoinQry query, UserAware userAware, HttpServletResponse response) throws IOException {
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setCreateById(userAware.getMchUserId() + "");
        BeanUtils.copyProperties(query, queryPage);
        queryPage.setShowWaitStatus(false);
        queryPage.setLimit(Integer.MAX_VALUE);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding(Charsets.UTF_8.name());
        String fileName = URLEncoder.encode("权益认领记录", Charsets.UTF_8.name());
        response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
        EasyExcel.write(response.getOutputStream(), UserTaskPrizePageVO.class).sheet("权益认领记录").doWrite(pageData.getRecords());
    }

    @ApiOperation(value = "权益认领记录详情")
    @RequestMapping(value = "/prize/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserTaskPrizeDetailVO> prizeDetail(@PathVariable("id") Long id, UserAware userAware) {
        UserTaskPrizeDetailVO vo = new UserTaskPrizeDetailVO();
        TaskPrizeJoinPageQuery queryPage = new TaskPrizeJoinPageQuery();
        queryPage.setCreateById(userAware.getMchUserId() + "");
        queryPage.setUserPrizeId(id);
        queryPage.setShowWaitStatus(false);
        Page<UserTaskPrizePageVO> pageData = userTaskPrizeJoinMapper.paginateByQuery(queryPage);
        UserTaskPrizePageVO userTaskPrizePageVO = pageData.getRecords().get(0);
        BeanUtils.copyProperties(userTaskPrizePageVO, vo);
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(id);
        vo.setPictures(memberTaskPrize.getPictures());
        vo.setExtData(memberTaskPrize.getExtData());
        vo.setRemarkPictures(memberTaskPrize.getRemarkPictures());
        vo.setRemarkText(memberTaskPrize.getRemarkText());
        vo.setIntroduceText(memberTaskPrize.getIntroduceText());
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "权益审核拒绝")
    @RequestMapping(value = "prize/audit/reject", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> prizeAuditReject(@RequestBody @Valid AuditRejectPrizeTaskParam form, UserAware userAware) {
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(form.getTaskPrizeId());

        if (!memberTaskPrize.getStatus().equals(2)) {
            return SingleResponse.buildFailure("9036", "审核操作失败,只有已认领状态的权益才能审核操作.");
        }
        memberTaskPrize.setRemarkPictures(form.getPictures());
        memberTaskPrize.setRemarkText(form.getContent());
        memberTaskPrize.setLastUpdateById(userAware.getUserId() + "");
        memberTaskPrize.setLastUpdateByName(userAware.getUserName());
        memberTaskPrize.setLastUpdateTime(new Date());
        memberTaskPrize.setStatus(4);
        boolean ret = memberTaskPrize.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "权益审核失败");
        }
        UserPrizeAuditEvent event = new UserPrizeAuditEvent(form.getTaskPrizeId());
        try {
            domainEventPublisher.publishEvent(event);
        } catch (Exception ex) {
            log.error("publishEvent [{}] error", event);
        }
        return SingleResponse.of("权益审核成功");
    }

    @ApiOperation(value = "权益审核通过")
    @RequestMapping(value = "prize/audit/pass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> prizeAuditPass(@RequestBody @Valid AuditPassPrizeTaskParam form, UserAware userAware) {
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(form.getTaskPrizeId());
        if (!memberTaskPrize.getStatus().equals(2)) {
            return SingleResponse.buildFailure("9036", "审核操作失败,只有已认领状态的权益才能审核操作.");
        }
        memberTaskPrize.setRemarkPictures(form.getPictures());
        memberTaskPrize.setRemarkText(form.getContent());
        memberTaskPrize.setLastUpdateById(userAware.getUserId() + "");
        memberTaskPrize.setLastUpdateByName(userAware.getUserName());
        memberTaskPrize.setLastUpdateTime(new Date());
        memberTaskPrize.setStatus(3);
        boolean ret = memberTaskPrize.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "权益审核失败");
        }

        UserPrizeAuditEvent event = new UserPrizeAuditEvent(form.getTaskPrizeId());
        try {
            domainEventPublisher.publishEvent(event);
        } catch (Exception ex) {
            log.error("publishEvent [{}] error", event);
        }

        return SingleResponse.of("权益审核成功");
    }
}
