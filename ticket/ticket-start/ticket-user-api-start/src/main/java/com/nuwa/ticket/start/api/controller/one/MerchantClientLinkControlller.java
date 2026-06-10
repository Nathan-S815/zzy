package com.nuwa.ticket.start.api.controller.one;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneClientLinkPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneClientLink;
import com.nuwa.infrastructure.ticket.database.one.param.OneClientLinkPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.OneClientLinkService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("one/tool/link")
@Api(tags = {"一码通功能链接配置"})
public class MerchantClientLinkControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneClientLinkService oneClientLinkService;

    @ApiOperation(value = "功能区列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<OneClientLink>> page(OneClientLinkPageQry pageQry, UserAware userAware) {
        pageQry.setMchId(userAware.getMchId());
        pageQry.setLimit(100);
        OneClientLinkPageParam pageParam = new OneClientLinkPageParam(pageQry);
        IPage<OneClientLink> pageData = oneClientLinkService.paginateByParam(pageParam);
        return SingleResponse.of(pageData.getRecords());
    }
}
