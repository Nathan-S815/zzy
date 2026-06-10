package com.nuwa.infrastructure.ticket.third.b2b.model.req;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
public class B2bCreateOrderReqModel {

    /**
     * 合作商Id
     */
    private String partnerId;

    /**
     * 外部订单id
     */
    private String originOrderId;

    private String productId;

    private Integer orderQuantity;

    private String orderRemark;

    private String visitDate;

    private String beginPlayTime;

    private String endPlayTime;

    private ContactPersonInfo contactPerson;

    private List<VisitPersonInfo> visitPerson;
}
