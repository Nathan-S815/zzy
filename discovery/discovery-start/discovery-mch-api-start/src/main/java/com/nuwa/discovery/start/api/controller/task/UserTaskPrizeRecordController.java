package com.nuwa.discovery.start.api.controller.task;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.app.discovery.command.query.MchTaskPrizePageJoinQry;
import com.nuwa.discovery.start.api.controller.task.param.AuditPassPrizeTaskParam;
import com.nuwa.discovery.start.api.controller.task.param.AuditRejectPrizeTaskParam;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.discovery.database.task.entity.TaskPlatform;
import com.nuwa.infrastructure.discovery.database.user.entity.Industry;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrize;
import com.nuwa.infrastructure.discovery.database.user.entity.MemberTaskPrizeRecord;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskApplyJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.UserTaskPrizeRecordJoinMapper;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.mapper.query.TaskPrizeRecordJoinPageQuery;
import com.nuwa.infrastructure.discovery.database.user.service.IndustryService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeRecordService;
import com.nuwa.infrastructure.discovery.database.user.service.MemberTaskPrizeService;
import com.nuwa.infrastructure.discovery.database.user.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.Charsets;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("user/task/record")
@Api(tags = {"任务权益记录相关"})
public class UserTaskPrizeRecordController {

    @Autowired
    private UserTaskApplyJoinMapper userTaskApplyJoinMapper;

    @Autowired
    private UserTaskPrizeRecordJoinMapper userTaskPrizeRecordJoinMapper;

    @Autowired
    private MemberTaskPrizeService memberTaskPrizeService;

    @Autowired
    private MemberTaskPrizeRecordService memberTaskPrizeRecordService;

    @Autowired
    private IndustryService industryService;

    @ApiOperation(value = "权益记录分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<UserTaskPrizeRecordPageVO>> prizePage(MchTaskPrizePageJoinQry query, UserAware userAware) {
        TaskPrizeRecordJoinPageQuery queryPage = new TaskPrizeRecordJoinPageQuery();
        BeanUtils.copyProperties(query, queryPage);
        Page<UserTaskPrizeRecordPageVO> pageData = userTaskPrizeRecordJoinMapper.paginateByQuery(queryPage);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "权益认领记录详情")
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<UserTaskPrizeDetailVO> prizeDetail(@PathVariable("id") Long id, UserAware userAware) {
        UserTaskPrizeDetailVO vo = new UserTaskPrizeDetailVO();
        TaskPrizeRecordJoinPageQuery queryPage = new TaskPrizeRecordJoinPageQuery();
        queryPage.setPrizeRecordId(id);
        Page<UserTaskPrizeRecordPageVO> pageData = userTaskPrizeRecordJoinMapper.paginateByQuery(queryPage);
        UserTaskPrizeRecordPageVO userTaskPrizePageVO = pageData.getRecords().get(0);
        BeanUtils.copyProperties(userTaskPrizePageVO, vo);
        MemberTaskPrizeRecord memberTaskPrizeRecord = memberTaskPrizeRecordService.getById(id);
        vo.setPictures(memberTaskPrizeRecord.getPictures());
        vo.setExtData(memberTaskPrizeRecord.getExtData());
        vo.setRemarkPictures(memberTaskPrizeRecord.getRemarkPictures());
        vo.setRemarkText(memberTaskPrizeRecord.getRemarkText());
        return SingleResponse.of(vo);
    }

    @ApiOperation(value = "权益审核拒绝")
    @RequestMapping(value = "audit/reject", method = RequestMethod.POST)
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
        memberTaskPrizeRecordService.lambdaUpdate()
                .set(MemberTaskPrizeRecord::getRemarkPictures, form.getPictures())
                .set(MemberTaskPrizeRecord::getRemarkText, form.getContent())
                .set(MemberTaskPrizeRecord::getStatus, 4)
                .set(MemberTaskPrizeRecord::getAuditTime, new Date())
                .eq(MemberTaskPrizeRecord::getId, form.getRecordId())
                .update();
        return SingleResponse.of("权益审核成功");
    }

    @ApiOperation(value = "权益审核通过")
    @RequestMapping(value = "audit/pass", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> prizeAuditPass(@RequestBody @Valid AuditPassPrizeTaskParam form, UserAware userAware) {
        MemberTaskPrize memberTaskPrize = memberTaskPrizeService.getById(form.getTaskPrizeId());
        if (!memberTaskPrize.getStatus().equals(2)) {
            return SingleResponse.buildFailure("9036", "审核操作失败,只有已认领状态的权益才能审核操作.");
        }
        memberTaskPrize.setLastUpdateById(userAware.getUserId() + "");
        memberTaskPrize.setLastUpdateByName(userAware.getUserName());
        memberTaskPrize.setLastUpdateTime(new Date());
        memberTaskPrize.setStatus(3);
        boolean ret = memberTaskPrize.updateById();
        if (!ret) {
            return SingleResponse.buildFailure("9036", "权益审核失败");
        }
        memberTaskPrizeRecordService.lambdaUpdate()
                .set(MemberTaskPrizeRecord::getStatus, 3)
                .set(MemberTaskPrizeRecord::getAuditTime, new Date())
                .eq(MemberTaskPrizeRecord::getId, form.getRecordId())
                .update();
        return SingleResponse.of("权益审核成功");
    }


    @ApiOperation(value = "权益管理(探店免票)")
    @RequestMapping(value = "/EquityManagerlist", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<EquityManagerVO>> listVideoList(Long pageSize, Long pageNum, String prizeTitle, String beginDate, String endDate) {
        IPage<EquityManagerVO> page = memberTaskPrizeRecordService.equityManagerList(pageSize, pageNum, prizeTitle, beginDate, endDate);
        for (EquityManagerVO equityManagerVO : page.getRecords()) {
            JSONObject jsonObject = JSONObject.parseObject(equityManagerVO.getExtData());
            equityManagerVO.setVisitDate((String)(jsonObject.get("visitDate")));
            equityManagerVO.setLinkName((String)(jsonObject.get("linkName")));
            equityManagerVO.setLinkIdCard((String)(jsonObject.get("linkIdCard")));
            equityManagerVO.setLinkMobile((String)(jsonObject.get("linkMobile")));
            Boolean togetherPeople =(Boolean)jsonObject.get("togetherPeople");
            if (togetherPeople) {
                equityManagerVO.setTogetherPeople((List) jsonObject.get("ticketList"));
            }
        }
        return SingleResponse.of(page);
    }

    @ApiOperation(value = "获取任务类型列表")
    @RequestMapping(value = "/listIndustry", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<Industry>> listTaskPrizeList() {
        List<Industry> list = industryService.lambdaQuery().list();
        return SingleResponse.of(list);
    }
}