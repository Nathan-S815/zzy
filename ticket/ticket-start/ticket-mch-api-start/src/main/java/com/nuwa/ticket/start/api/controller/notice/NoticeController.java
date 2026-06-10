package com.nuwa.ticket.start.api.controller.notice;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.notice.qry.NoticeInfoPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.notice.entity.NoticeInfo;
import com.nuwa.infrastructure.ticket.database.notice.param.NoticeInfoPageParam;
import com.nuwa.infrastructure.ticket.database.notice.service.NoticeInfoService;
import com.nuwa.ticket.start.api.controller.notice.param.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("notice")
@Api(tags = {"公告管理相关"})
public class NoticeController {

    @Autowired
    private NoticeInfoService noticeInfoService;

    @ApiOperation(value = "新增公告")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> save(@RequestBody SaveNoticeParam param, UserAware userAware) {
        NoticeInfo noticeInfo = new NoticeInfo();
        noticeInfo.setContent(param.getContent());
        noticeInfo.setCreateTime(new Date());
        noticeInfo.setMchId(userAware.getMchId());
        noticeInfo.setTitle(param.getTitle());
        noticeInfo.setContent(param.getContent());
        boolean insert = noticeInfo.insert();
        if (insert) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "新增公告失败");
    }

    @ApiOperation(value = "修改公告")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody ModifyNoticeParam param, UserAware userAware) {
        boolean update = noticeInfoService.lambdaUpdate()
                .set(NoticeInfo::getTitle, param.getTitle())
                .set(NoticeInfo::getContent, param.getContent())
                .set(NoticeInfo::getUpdateTime, new Date())
                .eq(NoticeInfo::getId, param.getId())
                .eq(NoticeInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "修改公告失败");
    }

    @ApiOperation(value = "移除公告")
    @RequestMapping(value = "/remove/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@PathVariable("id") Long id, UserAware userAware) {
        QueryWrapper<NoticeInfo> query = Wrappers.<NoticeInfo>query();
        query.eq(NoticeInfo.MCH_ID, userAware.getMchId());
        query.in(NoticeInfo.ID, id);
        boolean remove = noticeInfoService.remove(query);
        if (remove) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "移除公告失败");
    }

    @ApiOperation(value = "公告置顶")
    @RequestMapping(value = "/top", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> top(@RequestBody NoticeTopParam param, UserAware userAware) {
        boolean update = noticeInfoService.lambdaUpdate()
                .set(NoticeInfo::getRecommendStatus, 1)
                .eq(NoticeInfo::getId, param.getId())
                .eq(NoticeInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            noticeInfoService.lambdaUpdate()
                    .set(NoticeInfo::getRecommendStatus, 0)
                    .set(NoticeInfo::getUpdateTime, new Date())
                    .ne(NoticeInfo::getId, param.getId())
                    .eq(NoticeInfo::getMchId, userAware.getMchId())
                    .update();
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "公告置顶失败");
    }

    @ApiOperation(value = "公告取消置顶")
    @RequestMapping(value = "/topCanceled", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> topCanceled(@RequestBody NoticeTopParam param, UserAware userAware) {
        boolean update = noticeInfoService.lambdaUpdate()
                .set(NoticeInfo::getRecommendStatus, 0)
                .eq(NoticeInfo::getId, param.getId())
                .eq(NoticeInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "公告取消置顶失败");
    }

    @ApiOperation(value = "公告上架")
    @RequestMapping(value = "/on", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> on(@RequestBody NoticeOnParam param, UserAware userAware) {
        boolean update = noticeInfoService.lambdaUpdate()
                .set(NoticeInfo::getStatus, 1)
                .set(NoticeInfo::getUpdateTime, new Date())
                .eq(NoticeInfo::getId, param.getId())
                .eq(NoticeInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "公告上架失败");
    }

    @ApiOperation(value = "公告下架")
    @RequestMapping(value = "/off", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> off(@RequestBody NoticeOffParam param, UserAware userAware) {
        boolean update = noticeInfoService.lambdaUpdate()
                .set(NoticeInfo::getStatus, 0)
                .set(NoticeInfo::getUpdateTime, new Date())
                .eq(NoticeInfo::getId, param.getId())
                .eq(NoticeInfo::getRecommendStatus, 0)
                .eq(NoticeInfo::getMchId, userAware.getMchId())
                .update();
        if (update) {
            return SingleResponse.buildSuccess();
        }
        return SingleResponse.buildFailure("8987", "公告上架失败");
    }

    @ApiOperation(value = "公告详情")
    @RequestMapping(value = "/getById/{id}", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> getById(@PathVariable("id") Long id) {
        NoticeInfo noticeInfo = noticeInfoService.getById(id);
        //  Assert.isTrue(userAware.getMchId().equals(noticeInfo.getMchId()));
        return SingleResponse.of(noticeInfo);
    }

    @ApiOperation(value = "公告分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<NoticeInfo>> page(NoticeInfoPageQry pageQry, UserAware userAware) {
        NoticeInfoPageParam pageParam = new NoticeInfoPageParam(pageQry);
        IPage<NoticeInfo> pageData = noticeInfoService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "获取置顶公告")
    @RequestMapping(value = "/getTopOne", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<NoticeInfo> topList(GetNoticeTopParam param) {
        List<NoticeInfo> noticeInfoList = noticeInfoService.lambdaQuery()
                .eq(NoticeInfo::getRecommendStatus, 1)
                .eq(NoticeInfo::getMchId, param.getMchId())
                .last("limit 1")
                .list();
        if (!noticeInfoList.isEmpty()) {
            return SingleResponse.of(noticeInfoList.get(0));
        }
        return SingleResponse.of(null);
    }

    @ApiOperation(value = "公告分页(未登录)")
    @RequestMapping(value = "/notlogin/Page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<NoticeInfo>> notLoginPage(NoticeInfoPageQry pageQry) {
        NoticeInfoPageParam pageParam = new NoticeInfoPageParam(pageQry);
        IPage<NoticeInfo> pageData = noticeInfoService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

}
