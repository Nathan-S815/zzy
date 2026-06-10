package com.nuwa.infrastructure.ticket.service.mall.dto;

import lombok.Data;

/**
 *
 * @author hy
 * @date 2021/5/2 17:11
 * @since 1.0.0
 */
@Data
public class MallCreateOrderDTO {

    /**
     * 所属商户id
     */
    private Long mchId;

    /**
     * 所属商户appId
     */
    private Long mchAppId;

    private Long memberId;

    private Long productId;

    private Long specificationsId;

    private Integer productNum;

    private String buyerId;

    private String appId;

    private Integer receivingMethod;

    private String storeAddress;

    private String longitude;

    private String latitude;

    private String consigneeName;

    private String consigneeTel;

    private String consigneeAddr;

    private String appType;

}
