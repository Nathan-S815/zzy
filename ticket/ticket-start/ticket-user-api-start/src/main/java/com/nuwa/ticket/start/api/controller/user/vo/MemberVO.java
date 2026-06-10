package com.nuwa.ticket.start.api.controller.user.vo;

import com.nuwa.infrastructure.ticket.database.member.entity.Member;
import com.nuwa.infrastructure.ticket.database.one.entity.OneUserCenter;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class MemberVO extends Member {
    private OneUserCenter userCenter;
}
