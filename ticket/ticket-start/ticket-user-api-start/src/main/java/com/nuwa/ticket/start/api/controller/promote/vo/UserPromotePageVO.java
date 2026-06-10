package com.nuwa.ticket.start.api.controller.promote.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author hy
 */
@Data
public class UserPromotePageVO {

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("订单id")
    private Long orderId;

    @ApiModelProperty("总金额")
    private BigDecimal amount;

    @ApiModelProperty("数量")
    private Integer quantity;

    @ApiModelProperty("游玩日期")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("产品名称")
    private String productName;
}
