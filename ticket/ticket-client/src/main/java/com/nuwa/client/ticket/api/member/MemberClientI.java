package com.nuwa.client.ticket.api.member;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.client.ticket.api.appconfig.MerchantAppConfigClientIFallBack;
import com.nuwa.client.ticket.api.appconfig.param.GetAppConfigByOutAppIdParam;
import com.nuwa.client.ticket.api.member.param.GetMemberByMemberIdParam;
import com.nuwa.client.ticket.dto.vo.MemberVO;
import com.nuwa.client.ticket.dto.vo.MerchantAppPayInfoClientVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 获取用户信息
 *
 * @author hy
 */
@FeignClient(
        value = "ticket-service-${spring.profiles.active}",
        fallback = MerchantAppConfigClientIFallBack.class
)
public interface MemberClientI {
    String API_PREFIX = "/member/";

    /**
     * 获取用户信息
     *
     * @param param GetMemberByMemberIdParam
     * @return SingleResponse
     */
    @PostMapping(API_PREFIX + "/getByMemberId")
    @ResponseBody
    public SingleResponse<MemberVO> getByMemberId(@RequestBody GetMemberByMemberIdParam param);

}
