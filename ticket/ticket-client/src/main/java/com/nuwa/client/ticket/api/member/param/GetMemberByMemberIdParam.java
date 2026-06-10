package com.nuwa.client.ticket.api.member.param;

import lombok.Data;

/**
 * 获取用户信息
 *
 * @author hy
 */
@Data
public class GetMemberByMemberIdParam {
    private String outAppId;
    private Long memberId;
    private Long mchId;
}
