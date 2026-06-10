package com.nuwa.discovery.start.api.controller.dto;

import com.nuwa.infrastructure.discovery.database.member.entity.MemberTagBind;
import lombok.Data;

import java.util.List;

@Data
public class MemberTabBindDTO {

    private List<MemberTagBind> memberTagBindList;
}
