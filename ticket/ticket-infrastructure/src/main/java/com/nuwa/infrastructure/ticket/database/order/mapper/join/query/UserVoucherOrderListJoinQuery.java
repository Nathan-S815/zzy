package com.nuwa.infrastructure.ticket.database.order.mapper.join.query;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinListQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TouristInfo;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProductVerificationConfig;
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
@ApiModel(value = "获取用户核销码订单列表参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserVoucherOrderListJoinQuery extends BaseJoinListQuery<UserVoucherOrderListJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("订单id")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.ID)
    private Long id;

    @ApiModelProperty("联系人手机号")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.LINK_MOBILE)
    private String linkMobile;

    @ApiModelProperty("入园凭证")
    @JoinColumn(tableClass = ScenicspotProductVerificationConfig.class, column = ScenicspotProductVerificationConfig.ENTRANCE_CERTIFICATE)
    private String entranceCertificate;

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

    @ApiModelProperty("游玩日期-开始")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateStart;

    @ApiModelProperty("游玩日期-结束")
    @JoinColumn(tableClass = TicketOrder.class, column = TicketOrder.VISIT_DATE)
    private Date visitDateEnd;

    @Override
    public void where(JoinQueryBuilder<UserVoucherOrderListJoinQuery> wrapper) {
        wrapper.orderByAsc(UserVoucherOrderListJoinQuery::getVisitDateEnd);
        wrapper.eq(Objects.nonNull(mchId), UserVoucherOrderListJoinQuery::getMchId, mchId);
        wrapper.eq(Objects.nonNull(id), UserVoucherOrderListJoinQuery::getId, id);
        wrapper.eq(Objects.nonNull(productId), UserVoucherOrderListJoinQuery::getProductId, productId);
        wrapper.eq(Objects.nonNull(scenicspotId), UserVoucherOrderListJoinQuery::getScenicspotId, getScenicspotId());
        wrapper.eq(StrUtil.isNotBlank(linkMobile), UserVoucherOrderListJoinQuery::getLinkMobile, linkMobile);
        wrapper.like(StrUtil.isNotBlank(entranceCertificate), UserVoucherOrderListJoinQuery::getEntranceCertificate, entranceCertificate);
        wrapper.in(Objects.nonNull(statusIn) && statusIn.size() > 0, UserVoucherOrderListJoinQuery::getStatusIn, statusIn);
        if (Objects.nonNull(visitDateStart)) {
            wrapper.ge(UserVoucherOrderListJoinQuery::getVisitDateStart, DateUtil.beginOfDay(visitDateStart));
        }
        if (Objects.nonNull(visitDateStart)) {
            wrapper.le(UserVoucherOrderListJoinQuery::getVisitDateEnd, DateUtil.endOfDay(visitDateEnd));
        }
    }
}
