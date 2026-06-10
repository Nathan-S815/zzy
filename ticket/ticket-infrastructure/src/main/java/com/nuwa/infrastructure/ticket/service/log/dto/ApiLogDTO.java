package com.nuwa.infrastructure.ticket.service.log.dto;

import com.nuwa.client.ticket.util.ChannelResult;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 供应商日志事件
 *
 * @author hy
 */

@Data
public class ApiLogDTO {
    @ApiModelProperty("请求url")
    private String url;

    @ApiModelProperty("请求报文")
    private String req;

    @ApiModelProperty("响应报文")
    private String resp;

    @ApiModelProperty("花费时间")
    private Double costTime;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("订单号")
    private Long orderNo;

    @ApiModelProperty("日志类型 1:下单 2:支付 3:核销通知 4:退款通知 5:出票通知 6:退款")
    private Integer type;

    @ApiModelProperty("业务订单id")
    private Long bizOrderId;

    @ApiModelProperty("请求流水号")
    private String requestNo;

    @ApiModelProperty("状态码")
    private Integer httpCode;

    @ApiModelProperty("结果描述")
    private String result;

    public ApiLogDTO() {
    }

    @SuppressWarnings("ALL")
    public ApiLogDTO(ChannelResult channelResult, Long orderId, Long bizOrderId, Integer type) {
        this.setOrderId(orderId);
        this.setBizOrderId(bizOrderId);
        this.setType(type);
        this.setReq(channelResult.getRequestStr());
        this.setResp(channelResult.getResponseStr());
        this.setHttpCode(channelResult.getStatus());
        this.setUrl(channelResult.getUrl());
        this.setCostTime(channelResult.getCostTime().doubleValue());
        this.setResult(channelResult.getStatus() + "|" + channelResult.isSuccessful() + "|" + channelResult.getCode() + "|" + channelResult.getMsg());

    }

    @SuppressWarnings("ALL")
    public static ApiLogDTO of(ChannelResult channelResult) {
        ApiLogDTO event = new ApiLogDTO();
        event.setReq(channelResult.getRequestStr());
        event.setResp(channelResult.getResponseStr());
        event.setHttpCode(channelResult.getStatus());
        event.setUrl(channelResult.getUrl());
        event.setCostTime(channelResult.getCostTime().doubleValue());
        event.setResult(channelResult.getStatus() + "|" + channelResult.isSuccessful() + "|" + channelResult.getCode() + "|" + channelResult.getMsg());
        return event;
    }

    @SuppressWarnings("ALL")
    public static ApiLogDTO of(ChannelResult channelResult, Long orderId, Long bizOrderId, Integer type) {
        ApiLogDTO event = new ApiLogDTO();
        event.setOrderId(orderId);
        event.setBizOrderId(bizOrderId);
        event.setType(type);
        event.setReq(channelResult.getRequestStr());
        event.setResp(channelResult.getResponseStr());
        event.setHttpCode(channelResult.getStatus());
        event.setUrl(channelResult.getUrl());
        event.setCostTime(channelResult.getCostTime().doubleValue());
        event.setResult(channelResult.getStatus() + "|" + channelResult.isSuccessful() + "|" + channelResult.getCode() + "|" + channelResult.getMsg());
        return event;
    }
}
