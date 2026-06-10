package com.nuwa.ticket.start.api.controller.notice;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.notice.qry.NoticeInfoPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.notice.entity.NoticeInfo;
import com.nuwa.infrastructure.ticket.database.notice.param.NoticeInfoPageParam;
import com.nuwa.infrastructure.ticket.database.notice.service.NoticeInfoService;
import com.nuwa.ticket.start.api.controller.notice.param.GetNoticeDetailParam;
import com.nuwa.ticket.start.api.controller.notice.param.GetNoticeTopParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("notice")
@Api(tags = {"公告相关"})
public class NoticeController {

    @Autowired
    private NoticeInfoService noticeInfoService;

    @ApiOperation(value = "获取置顶公告")
    @RequestMapping(value = "/top", method = RequestMethod.GET)
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

    @ApiOperation(value = "公告详情")
    @RequestMapping(value = "/getById", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<?> getById(GetNoticeDetailParam param) {
        NoticeInfo noticeInfo = noticeInfoService.getById(param.getId());
        Assert.isTrue(param.getMchId().equals(noticeInfo.getMchId()));
        return SingleResponse.of(noticeInfo);
    }

    @ApiOperation(value = "公告分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<NoticeInfo>> page(NoticeInfoPageQry pageQry, UserAware userAware) {
        pageQry.setStatus(1);
        NoticeInfoPageParam pageParam = new NoticeInfoPageParam(pageQry);
        IPage<NoticeInfo> pageData = noticeInfoService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }

}
