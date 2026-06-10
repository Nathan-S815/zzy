package com.nuwa.ticket.start.server.client.feign;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.MerchantAppConfigClientI;
import com.nuwa.client.ticket.api.appconfig.param.GetAppConfigByOutAppIdParam;
import com.nuwa.client.ticket.api.member.MemberClientI;
import com.nuwa.client.ticket.api.member.param.GetMemberByMemberIdParam;
import com.nuwa.client.ticket.dto.vo.MemberVO;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import com.nuwa.client.ticket.dto.vo.PaymentConfigVO;
import com.nuwa.infrastructure.ticket.database.mchconfig.MerchantAppPayConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.entity.MerchantAppBaseConf;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppBaseConfService;
import com.nuwa.infrastructure.ticket.database.mchconfig.service.MerchantAppPayConfService;
import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.member.entity.ThirdUser;
import com.nuwa.infrastructure.ticket.database.member.service.MemberService;
import com.nuwa.infrastructure.ticket.database.member.service.ThirdUserService;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * MemberClientI实现
 *
 * @author hy
 */
@Slf4j
@RestController
public class MemberClientImpl implements MemberClientI {

    @Autowired
    private MerchantAppBaseConfService merchantAppBaseConfService;

    @Autowired
    private MerchantAppPayConfService merchantAppPayConfService;

    @Autowired
    private MemberService mmemberService;

    @Autowired
    private ThirdUserService thirdUserService;

    @Override
    public SingleResponse<MemberVO> getByMemberId(GetMemberByMemberIdParam param) {
        Member member = mmemberService.getById(param.getMemberId());
        if (Objects.isNull(member)) {
            return SingleResponse.buildFailure("9874", "用户信息不存在");
        }
        MemberVO vo = new MemberVO();
        BeanUtils.copyProperties(member, vo);

        ThirdUser thirdUser = thirdUserService.lambdaQuery()
                .eq(ThirdUser::getUserId, member.getUserId())
                .eq(ThirdUser::getOutAppId, param.getOutAppId())
                .one();
        if (Objects.isNull(thirdUser)) {
            return SingleResponse.buildFailure("9874", "用户信息不存在");
        }
        vo.setChannelCode(thirdUser.getChannelCode());
        vo.setOpenId(thirdUser.getOpenId());
        return SingleResponse.of(vo);
    }
}
