package com.nuwa.infrastructure.ticket.database.order.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 用户订单列表返回VO
 *
 * @author hy
 */
@Data
public class UserTicketOrderListVO {
    private Integer id;

    private Long userId;

    private Integer status;

    private Integer quantity;

    private String mobile;

    private String idCard;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
