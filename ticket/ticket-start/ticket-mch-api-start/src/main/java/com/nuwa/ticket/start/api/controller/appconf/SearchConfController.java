package com.nuwa.ticket.start.api.controller.appconf;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.exception.Assert;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.appconf.qry.MerchantAppConfPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchTip;
import com.nuwa.infrastructure.ticket.database.mchconfig.param.MchAppConfPageParam;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchTipService;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsAlipayConfig;
import com.nuwa.infrastructure.ticket.database.pubsystem.entity.PsTemplateInfo;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsAlipayConfigService;
import com.nuwa.infrastructure.ticket.database.pubsystem.service.PsTemplateInfoService;
import com.nuwa.ticket.start.api.controller.appconf.param.*;
import com.nuwa.ticket.start.api.controller.appconf.vo.MchAppBaseConfigPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("merchant/search")
@Api(tags = {"商户搜索配置管理"})
public class SearchConfController {

    @Autowired
    private SearchConfService searchConfService;

    @Autowired
    private SearchTipService searchTipService;

    @ApiOperation(value = "配置搜索提示")
    @RequestMapping(value = "/addTip", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> addSearchTip(@RequestBody AddMchSearchTipParam param, UserAware userAware) {
        SearchTip searchTip = searchTipService.lambdaQuery().eq(SearchTip::getMchId, userAware.getMchId()).one();
        if (Objects.isNull(searchTip)) {
            searchTip = new SearchTip();
        }
        BeanUtils.copyProperties(param, searchTip);
        searchTip.setMchId(userAware.getMchId().intValue());
        searchTip.insertOrUpdate();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "获取搜索提示")
    @RequestMapping(value = "/getTip", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<SearchTip> getTip(UserAware userAware) {
        SearchTip searchTip = searchTipService.lambdaQuery().eq(SearchTip::getMchId, userAware.getMchId()).one();
        if (Objects.isNull(searchTip)) {
            searchTip = new SearchTip();
        }
        return SingleResponse.of(searchTip);
    }

    @ApiOperation(value = "新增关键字")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> add(@RequestBody AddMchSearchConfParam param, UserAware userAware) {
        SearchConf searchConf = new SearchConf();
        BeanUtils.copyProperties(param, searchConf);
        searchConf.setMchId(userAware.getMchId().intValue());
        searchConf.insert();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "修改关键字顺序")
    @RequestMapping(value = "/modifyOrderNum", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> modifyOrderNum(@RequestBody BatchModifySearchOrderNumConfParam param, UserAware userAware) {
        List<SearchConf> batchSearchConf = param.getOrderNumItems().stream().map(x -> {
            SearchConf searchConf = new SearchConf();
            searchConf.setId(x.getId().intValue());
            searchConf.setOrderNum(x.getOrderNum());
            return searchConf;
        }).collect(Collectors.toList());
        searchConfService.updateBatchById(batchSearchConf);

        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "删除关键字")
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    @ResponseBody
    public SingleResponse<?> remove(@RequestBody RemovedSearchOrderNumConfParam param, UserAware userAware) {
        searchConfService.lambdaUpdate()
                .eq(SearchConf::getId, param.getId())
                .eq(SearchConf::getMchId, userAware.getMchId())
                .remove();
        return SingleResponse.buildSuccess();
    }

    @ApiOperation(value = "搜索关键字列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<SearchConf>> list(UserAware userAware) {
        List<SearchConf> list = searchConfService.lambdaQuery()
                .eq(SearchConf::getMchId, userAware.getMchId())
                .orderByAsc(SearchConf::getOrderNum)
                .list();
        return SingleResponse.of(list);
    }

}
