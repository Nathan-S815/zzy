package com.nuwa.infrastructure.ticket.database.manager;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Date;

@Data
public class AlipayTicketModel {

    /**
     * 门票单价
     */
    @JsonProperty("price")
    private String price;

    /**
     * 门票状态
     INIT("INIT","初始化")
     TICKET_RUNNING("TICKET_RUNNING",出票中)
     TICKET_SUCCESS(TICKET_SUCCESS,出票成功)
     TICKET_FAILURE("TICKET_FAILURE",出票失败)
     TO_USE("TO_USE",待核销)
     USED("USED",已核销)
     CLOSED("CLOSED",已完结)
     REFUND_RUNNING("REFUND_RUNNING",待退票)
     REFUND_SUCCESS("REFUND_SUCCESS",已退票)
     REFUND_FAILURE("REFUND_FAILURE",退票失败)
     */
    @JsonProperty("status")
    private String status;

    /**
     * 票据数量，若一个实例表示一个门票，ticket_count = 1,
     若为聚合门票, ticket_count = n;
     */
    @JsonProperty("ticket_count")
    private Long ticketCount;

    /**
     * 门票名称
     */
    @JsonProperty("ticket_name")
    private String ticketName;

    /**
     * 单据号
     */
    @JsonProperty("ticket_no")
    private String ticketNo;

    /**
     * 门票类型
     NORMAL("NORMAL","普通"),
     GROUP("GROUP","套票"),
     PERIOD("PERIOD","时段票"),
     REGION("REGION","区域票")
     */
    @JsonProperty("ticket_type")
    private String ticketType;

    /**
     * 票凭证code
     */
    @JsonProperty("ticket_use_code")
    private String ticketUseCode;

    /**
     * 使用结束日期
     */
    @JsonProperty("use_end_date")
    private Date useEndDate;

    /**
     * 使用开始日期
     */
    @JsonProperty("use_start_date")
    private Date useStartDate;
}
