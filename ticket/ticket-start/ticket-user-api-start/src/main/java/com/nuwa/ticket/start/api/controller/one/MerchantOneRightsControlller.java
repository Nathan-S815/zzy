package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.ticket.database.one.entity.OneRightsConf;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.mapper.join.MerchantScenicspotRightsJoinMapper;
import com.nuwa.infrastructure.ticket.database.one.mapper.join.query.MerchantScenicsportRightsJoinPageJoinQuery;
import com.nuwa.infrastructure.ticket.database.one.mapper.vo.MerchantScenicsportRightsPageVO;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantScenicspotRightsService;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantUsableIdentityService;
import com.nuwa.infrastructure.ticket.database.one.service.OneRightsConfService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequestMapping("one")
@Api(tags = {"C端一码通景区权益接口"})
public class MerchantOneRightsControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneMerchantUsableIdentityService oneMerchantUsableIdentityService;

    @Autowired
    private OneRightsConfService oneRightsConfService;

    @Autowired
    private OneMerchantScenicspotRightsService oneMerchantScenicspotRightsService;

    @Autowired
    private MerchantScenicspotRightsJoinMapper merchantScenicspotRightsJoinMapper;

    @ApiOperation(value = "景区权益列表")
    @RequestMapping(value = "scenics/rightslist", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<Page<MerchantScenicsportRightsPageVO>> list(@RequestParam(value = "mchId", required = true) Long mchId,
                                                                      @RequestParam(value = "scenicSportId", required = true) Long scenicSportId) {
        MerchantScenicsportRightsJoinPageJoinQuery queryPage = new MerchantScenicsportRightsJoinPageJoinQuery();
        queryPage.setMerchantId(mchId);
        queryPage.setScenicspotId(scenicSportId);
        Page<MerchantScenicsportRightsPageVO> pageData = merchantScenicspotRightsJoinMapper.paginateByQuery(queryPage);
        pageData.getRecords().forEach(x -> {
            if (x.getIdentityCodeList().equalsIgnoreCase("-1")) {
                x.setIdentityCodeList("全部");
            } else if (StrUtil.isNotBlank(x.getIdentityCodeList())) {
                ArrayList<String> identityCodeList = CollectionUtil.toList(x.getIdentityCodeList().split(","));
                List<OneUsableIdentity> identities = oneUsableIdentityService
                        .lambdaQuery()
                        .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
                String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
                x.setIdentityCodeList(identityNames);
            }
        });
        return SingleResponse.of(pageData);
    }

    @ApiOperation(value = "景区权益详情")
    @RequestMapping(value = "scenics/rightsDetai/{id}", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<MerchantScenicsportRightsPageVO> detail(@PathVariable("id") Long id) {
        OneRightsConf oneRightsConf = oneRightsConfService.getById(id);
        MerchantScenicsportRightsPageVO vo = new MerchantScenicsportRightsPageVO();
        BeanUtil.copyProperties(oneRightsConf, vo);
        if (oneRightsConf.getIdentityCodeList().equalsIgnoreCase("-1")) {
            vo.setIdentityCodeList("全部");
        } else if (StrUtil.isNotBlank(oneRightsConf.getIdentityCodeList())) {
            ArrayList<String> identityCodeList = CollectionUtil.toList(oneRightsConf.getIdentityCodeList().split(","));
            List<OneUsableIdentity> identities = oneUsableIdentityService
                    .lambdaQuery()
                    .in(OneUsableIdentity::getIdentityCode, identityCodeList).list();
            String identityNames = identities.stream().map(OneUsableIdentity::getName).collect(Collectors.joining(","));
            vo.setIdentityCodeList(identityNames);
        }
        return SingleResponse.of(vo);
    }
}
