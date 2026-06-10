package com.nuwa.infrastructure.ticket.database.one.mapper.join.query;

import cn.hutool.core.date.DateUtil;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.framework.database.tk.join.query.BaseJoinPagingQuery;
import com.nuwa.framework.database.tk.join.wrappper.JoinQueryBuilder;
import com.nuwa.infrastructure.ticket.database.one.entity.OneMerchantScenicspotRights;
import com.nuwa.infrastructure.ticket.database.order.entity.SupplierPaymentOrder;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.Objects;


/**
 * @author hy
 */
@ApiModel(value = "商户景区权益分页查询参数")
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class MerchantScenicsportRightsJoinPageJoinQuery extends BaseJoinPagingQuery<MerchantScenicsportRightsJoinPageJoinQuery> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @JoinColumn(tableClass = OneMerchantScenicspotRights.class, column = OneMerchantScenicspotRights.ID)
    private Long id;

    @ApiModelProperty("商户id")
    @JoinColumn(tableClass = OneMerchantScenicspotRights.class, column = OneMerchantScenicspotRights.MERCHANT_ID)
    private Long merchantId;

    @ApiModelProperty("景区id")
    @JoinColumn(tableClass = OneMerchantScenicspotRights.class, column = OneMerchantScenicspotRights.SCENICSPOT_ID)
    private Long scenicspotId;

    @Override
    public void where(JoinQueryBuilder<MerchantScenicsportRightsJoinPageJoinQuery> wrapper) {
        wrapper.orderByDesc(MerchantScenicsportRightsJoinPageJoinQuery::getId);
        wrapper.eq(Objects.nonNull(merchantId), MerchantScenicsportRightsJoinPageJoinQuery::getMerchantId, merchantId);
        wrapper.eq(Objects.nonNull(scenicspotId), MerchantScenicsportRightsJoinPageJoinQuery::getScenicspotId, scenicspotId);
    }
}
