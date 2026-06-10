package com.nuwa.client.ticket.api.member;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.MerchantAppConfigClientI;
import com.nuwa.client.ticket.api.appconfig.param.GetAppConfigByOutAppIdParam;
import com.nuwa.client.ticket.api.member.param.GetMemberByMemberIdParam;
import com.nuwa.client.ticket.dto.vo.MemberVO;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import org.springframework.stereotype.Component;

/**
 * MemberClientI
 *
 * @author hy
 * @date 2021/4/22 13:54
 * @since 1.0.0
 */
@Component
public class MemberClientIFallBack implements MemberClientI {

    @Override
    public SingleResponse<MemberVO> getByMemberId(GetMemberByMemberIdParam param) {
        return SingleResponse.buildFailure("9999", "FallBack");
    }
}
