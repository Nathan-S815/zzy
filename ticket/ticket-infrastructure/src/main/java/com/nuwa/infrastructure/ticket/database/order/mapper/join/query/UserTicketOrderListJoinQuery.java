package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinListQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TouristInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "获取用户订单列表参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserTicketOrderListJoinQuery extends BaseJoinListQuery<UserTicketOrderListJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("游玩人手机号")
    @JoinColumn(tableClass = TouristInfo.class, column = TouristInfo.MOBILE)
    private String mobile;

    @ApiModelProperty("游玩人身份证号")
    @JoinColumn(tableClass = TouristInfo.class, column = TouristInfo.ID_CARD)
    private String idCard;

    @ApiModelProperty("用户id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.USER_ID)
    private Long userId;

    @ApiModelProperty("商户id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.MCH_ID)
    private Long mchId;

    @ApiModelProperty("产品id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.PRODUCT_ID)
    private Long productId;

    @ApiModelProperty("景区id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.SCENICSPOT_ID)
    private Long scenicspotId;

    @ApiModelProperty("订单状态In")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.STATUS)
    private List<Integer> statusIn;

    @ApiModelProperty("游玩日期")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDate;

    @Override
    public void where(JoinQueryBuilder<UserTicketOrderListJoinQuery> wrapper) {
        wrapper.eq(Objects.nonNull(userId), UserTicketOrderListJoinQuery::getUserId, userId);
        wrapper.eq(Objects.nonNull(mchId), UserTicketOrderListJoinQuery::getMchId, mchId);
        wrapper.eq(Objects.nonNull(productId), UserTicketOrderListJoinQuery::getProductId, productId);
        wrapper.eq(Objects.nonNull(scenicspotId), UserTicketOrderListJoinQuery::getScenicspotId, getScenicspotId());
        wrapper.eq(StrUtil.isNotBlank(mobile), UserTicketOrderListJoinQuery::getMobile, mobile);
        wrapper.eq(StrUtil.isNotBlank(idCard), UserTicketOrderListJoinQuery::getIdCard, idCard);
        wrapper.in(Objects.nonNull(statusIn) && statusIn.size() > 0, UserTicketOrderListJoinQuery::getStatusIn, statusIn);
        wrapper.ge(Objects.nonNull(visitDate), UserTicketOrderListJoinQuery::getVisitDate, visitDate);
    }
}
