package com.nuwa.ticket.start.api.controller.conf;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.SearchTip;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.SearchTipService;
import com.nuwa.ticket.start.api.controller.conf.param.GetTipParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("search/config")
@Api(tags = {"搜索配置"})
public class SearchConfController {

    @Autowired
    private SearchConfService searchConfService;

    @Autowired
    private SearchTipService searchTipService;


    @ApiOperation(value = "获取搜索关键字列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<SearchConf>> list(GetTipParam param) {
        List<SearchConf> list = searchConfService.lambdaQuery()
                .eq(SearchConf::getMchId, param.getMchId())
                .orderByAsc(SearchConf::getOrderNum)
                .list();
        return SingleResponse.of(list);
    }

    @ApiOperation(value = "获取搜索提示")
    @RequestMapping(value = "/getTip", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<SearchTip> getTip(GetTipParam param) {
        SearchTip searchTip = searchTipService.lambdaQuery().eq(SearchTip::getMchId, param.getMchId()).one();
        if (Objects.isNull(searchTip)) {
            searchTip = new SearchTip();
        }
        return SingleResponse.of(searchTip);
    }

}
