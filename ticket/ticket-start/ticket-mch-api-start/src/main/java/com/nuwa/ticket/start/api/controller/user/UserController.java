package com.nuwa.ticket.start.api.controller.user;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.IdcardUtil;
import com.alibaba.cola.dto.SingleResponse;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.client.ticket.dto.clientobject.supplier.qry.SupplierConfPageQry;
import com.nuwa.client.ticket.dto.clientobject.user.qry.UserPageQry;
import com.nuwa.client.ticket.dto.vo.MemberVO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.param.UserPageParam;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUserCenter;
import com.nuwa.infrastructure.ticket.database.one.service.OneUserCenterService;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotSupplier;
import com.nuwa.infrastructure.ticket.database.supplier.param.MchSupplierConfPageParam;
import com.nuwa.ticket.start.api.controller.user.vo.MemberPageVO;
import com.nuwa.ticket.start.api.util.DesensitizedUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Objects;

/**
 * @author hy
 */
@Slf4j
@Controller
@RequestMapping("user")
@Api(tags = {"用户管理"})
public class UserController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private OneUserCenterService oneUserCenterService;

    @ApiOperation(value = "分页查询")
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public SingleResponse<IPage<MemberPageVO>> page(@Valid UserPageQry pageQry, UserAware userAware) {
        UserPageParam pageParam = new UserPageParam(pageQry);
        IPage<MemberPageVO> pageData = memberService.paginateAndConvert(pageParam, this::toVo);
        return SingleResponse.of(pageData);
    }

    public MemberPageVO toVo(Member member) {
        MemberPageVO vo = new MemberPageVO();
        BeanUtils.copyProperties(member, vo);
        MerchantAppBaseConf merchantAppBaseConf = merchantAppBaseConfService.getById(member.getSrcAppId());
        if (Objects.nonNull(merchantAppBaseConf)) {
            vo.setAppType(merchantAppBaseConf.getAppType());
            vo.setOutAppId(merchantAppBaseConf.getOutAppId());
        }
        OneUserCenter oneUserCenter = oneUserCenterService.lambdaQuery().eq(OneUserCenter::getUserPhone, member.getUserPhone()).one();
        if (Objects.nonNull(oneUserCenter)) {
            vo.setCenterUserId(oneUserCenter.getUserId());
            vo.setCenterUserPhone(oneUserCenter.getUserPhone());
            vo.setCenterRealName(DesensitizedUtils.chineseName(oneUserCenter.getUserRealName()));
            vo.setCenterIdCard(DesensitizedUtils.idCardNum(oneUserCenter.getUserIdCard()));
        }
        return vo;
    }
}
