package com.nuwa.ticket.start.api.controller.order.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单流程VO
 *
 * @author hy
 */
@Data
@AllArgsConstructor
public class OrderFlowItemVO {

    @ApiModelProperty("流程标题")
    private String title;

    @ApiModelProperty("发生时间")
    private Date happenDate;

    @ApiModelProperty("流程记录")
    private String record;
}
