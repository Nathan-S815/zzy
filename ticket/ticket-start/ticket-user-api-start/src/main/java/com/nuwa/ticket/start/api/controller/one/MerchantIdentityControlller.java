package com.nuwa.ticket.start.api.controller.one;

import cn.hutool.core.util.StrUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneMerchantUsableIdentityPageQry;
import com.nuwa.client.ticket.dto.clientobject.one.qry.OneUsableIdentityPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMember;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUsableIdentity;
import com.nuwa.infrastructure.ticket.database.one.param.OneMerchantUsableIdentityPageParam;
import com.nuwa.infrastructure.ticket.database.one.param.OneUsableIdentityPageParam;
import com.nuwa.infrastructure.ticket.database.one.service.OneMemberService;
import com.nuwa.infrastructure.ticket.database.one.service.OneMerchantUsableIdentityService;
import com.nuwa.infrastructure.ticket.database.one.service.OneUsableIdentityService;
import com.nuwa.ticket.start.api.controller.one.vo.MerchantOneUsableIdentityPageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Controller
@RequestMapping("one/identity")
@Api(tags = {"C端商户一码通身份认证接口"})
public class MerchantIdentityControlller {

    @Autowired
    private OneUsableIdentityService oneUsableIdentityService;

    @Autowired
    private OneMerchantUsableIdentityService oneMerchantUsableIdentityService;

    @Autowired
    private OneMemberService oneMemberService;

    @ApiOperation(value = "申请认证列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<List<MerchantOneUsableIdentityPageVO>> list(UserAware userAware) {
        Set<String> identityCodeSet = new HashSet<>();
        OneMember oneMember = oneMemberService.lambdaQuery().eq(OneMember::getUserId, userAware.getUserId()).one();
        if (StrUtil.isNotBlank(oneMember.getIdentityCode())) {
            identityCodeSet = Arrays.stream(oneMember.getIdentityCode().split(",")).collect(Collectors.toSet());
        }
        OneUsableIdentityPageQry pageQry = new OneUsableIdentityPageQry();
        pageQry.setLimit(100);
        OneUsableIdentityPageParam pageParam = new OneUsableIdentityPageParam(pageQry);
        IPage<MerchantOneUsableIdentityPageVO> pageData = oneUsableIdentityService.paginateAndConvert(pageParam, this::toVO);
        Set<String> finalIdentityCodeSet = identityCodeSet;
        pageData.getRecords().forEach(x -> {
            x.setAuthStatus(0);
            OneMerchantUsableIdentity merchantUsableIdentity = oneMerchantUsableIdentityService.lambdaQuery()
                    .eq(OneMerchantUsableIdentity::getMchId, userAware.getMchId())
                    .eq(OneMerchantUsableIdentity::getIdentityCode, x.getIdentityCode())
                    .eq(OneMerchantUsableIdentity::getStatus, "on")
                    .one();
            if (Objects.isNull(merchantUsableIdentity)) {
                x.setOpenStatus("off");
                x.setSortNum(999);
            } else {
                if (finalIdentityCodeSet.contains(x.getIdentityCode())) {
                    x.setAuthStatus(1);
                }
                x.setOpenStatus("on");
                x.setSortNum(merchantUsableIdentity.getSortNum());
            }
        });
        List<MerchantOneUsableIdentityPageVO> sortedRecords = pageData.getRecords().stream()
                .sorted(Comparator.comparing(MerchantOneUsableIdentityPageVO::getSortNum))
                .collect(Collectors.toList());
        return SingleResponse.of(sortedRecords);
    }

    public MerchantOneUsableIdentityPageVO merchantUsableIdentityToVO(OneMerchantUsableIdentity entity) {
        OneUsableIdentity oneUsableIdentity = oneUsableIdentityService.lambdaQuery().eq(OneUsableIdentity::getIdentityCode, entity.getIdentityCode()).one();
        return toVO(oneUsableIdentity);
    }

    public MerchantOneUsableIdentityPageVO toVO(OneUsableIdentity entity) {
        MerchantOneUsableIdentityPageVO vo = new MerchantOneUsableIdentityPageVO();
        BeanUtils.copyProperties(entity, vo);
        return vo;
    }
}
