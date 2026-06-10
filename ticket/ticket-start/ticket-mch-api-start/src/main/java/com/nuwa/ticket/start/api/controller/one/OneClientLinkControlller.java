package com.nuwa.ticket.start.api.controller.one;

import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneClientLinkPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneClientLink;
import com.nuwa.infrastructure.ticket.database.one.param.OneClientLinkPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.OneClientLinkService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import com.nuwa.ticket.start.api.controller.one.param.AddToolLinkParam;
import com.nuwa.ticket.start.api.controller.one.param.ModifyOneToolLinkSortNumParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
@RequestMapping("one/tool/link")
@Api(tags = {"一码通功能链接配置"})
public class OneClientLinkControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneClientLinkService oneClientLinkService;

    @ApiOperation(value = "新增")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody @Valid AddToolLinkParam param, UserAware userAware) {
       /* Integer count = oneClientLinkService.lambdaQuery().eq(OneClientLink::getToolName, param.getToolName()).count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "功能名称重复");
        }*/
        OneClientLink oneClientLink = new OneClientLink();
        BeanUtils.copyProperties(param, oneClientLink);
        oneClientLink.setMchId(userAware.getMchId());
        oneClientLink.setSortNum(0);
        oneClientLink.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改")
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modify(@RequestBody @Valid AddToolLinkParam param, UserAware userAware) {
       /* Integer count = oneClientLinkService.lambdaQuery()
                .eq(OneClientLink::getToolName, param.getToolName())
                .ne(OneClientLink::getId, param.getId())
                .count();
        if (count > 0) {
            return SingleResponse.buildFailure("9824", "功能名称重复");
        }*/
        OneClientLink oneClientLink = oneClientLinkService.getById(param.getId());
        BeanUtils.copyProperties(param, oneClientLink);
        oneClientLink.setSortNum(null);
        oneClientLink.updateById();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改排序")
    @RequestMapping(value = "/modifySortNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyOrderNum(@RequestBody @Valid ModifyOneToolLinkSortNumParam param, UserAware userAware) {
        oneClientLinkService.lambdaUpdate()
                .set(OneClientLink::getSortNum, param.getSortNum())
                .eq(OneClientLink::getId, param.getId())
                .update();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "删除")
    @RequestMapping(value = "/{id}/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@PathVariable("id") Long id, UserAware userAware) {
        oneClientLinkService.removeById(id);
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "详情")
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<OneClientLink> detail(@PathVariable("id") Long id, UserAware userAware) {
        OneClientLink oneClientLink = oneClientLinkService.getById(id);
        return SingleResponse.of(oneClientLink);
    }

    @ApiOperation(value = "分页")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<OneClientLink>> page(OneClientLinkPageQry pageQry, UserAware userAware) {
        pageQry.setMchId(userAware.getMchId());
        OneClientLinkPageParam pageParam = new OneClientLinkPageParam(pageQry);
        IPage<OneClientLink> pageData = oneClientLinkService.paginateByParam(pageParam);
        return SingleResponse.of(pageData);
    }
}
