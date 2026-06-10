package com.nuwa.ticket.start.api.pubsystem.vo;

import lombok.Data;

import java.util.List;

@Data
public class PsAppVersionInfoVO {
    private List<DevelopVO> developItems;
    private List<AuditVO> auditItems;
    private List<PublishVO> publishItems;
}
