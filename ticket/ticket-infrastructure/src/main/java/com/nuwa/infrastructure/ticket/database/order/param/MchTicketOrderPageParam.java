package com.nuwa.infrastructure.ticket.database.order.param;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nuwa.client.ticket.dto.clientobject.order.qry.MchTicketOrderPageQry;
import com.nuwa.framework.base.UserAware;
import com.nuwa.framework.database.PageQry;
import com.nuwa.infrastructure.ticket.database.order.entity.TicketOrder;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * <pre>
 * 订单表 分页参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户订单表分页参数")
public class MchTicketOrderPageParam extends PageQry<TicketOrder> {
    private static final long serialVersionUID = 1L;

    private MchTicketOrderPageQry qry;

    public MchTicketOrderPageParam(MchTicketOrderPageQry qry) {
        super(qry);
        this.qry = qry;
    }

    @Override
    public LambdaQueryWrapper<TicketOrder> toQueryWrapper() {
        UserAware userAware = qry.getUserAware();
        LambdaQueryWrapper<TicketOrder> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.orderByDesc(TicketOrder::getId);
        queryWrapper.select(TicketOrder.class,
                t -> !t.getColumn().endsWith("_editor") || !ignoresColumnData().contains(t.getColumn())
        );
        queryWrapper.eq(Objects.nonNull(qry.getDistributeMerchantId()), TicketOrder::getMchId, qry.getDistributeMerchantId());
        queryWrapper.eq(Objects.nonNull(qry.getSupplierMerchantId()), TicketOrder::getSupplierMerchantId, qry.getSupplierMerchantId());
        queryWrapper.eq(Objects.nonNull(qry.getStatus()), TicketOrder::getStatus, qry.getStatus());
        queryWrapper.eq(Objects.nonNull(qry.getOrderId()), TicketOrder::getId, qry.getOrderId());
        queryWrapper.eq(Objects.nonNull(qry.getOrderNo()), TicketOrder::getOrderNo, qry.getOrderNo());
        queryWrapper.eq(Objects.nonNull(qry.getSupplierId()), TicketOrder::getSupplierId, qry.getSupplierId());
        queryWrapper.like(StrUtil.isNotBlank(qry.getProductName()), TicketOrder::getProductName, qry.getProductName());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getSupplierProductCode()), TicketOrder::getSupplierProductCode, qry.getSupplierProductCode());
        queryWrapper.eq(Objects.nonNull(qry.getProductId()), TicketOrder::getProductId, qry.getProductId());
        if (Objects.nonNull(qry.getVisitDateStart())) {
            queryWrapper.ge(TicketOrder::getVisitDate, DateUtil.beginOfDay(qry.getVisitDateStart()));
        }
        if (Objects.nonNull(qry.getVisitDateEnd())) {
            queryWrapper.le(TicketOrder::getVisitDate, DateUtil.endOfDay(qry.getVisitDateEnd()));
        }

        if (Objects.nonNull(qry.getCreateDateStart())) {
            queryWrapper.ge(TicketOrder::getCreateTime, DateUtil.beginOfDay(qry.getCreateDateStart()));
        }
        if (Objects.nonNull(qry.getCreateDateEnd())) {
            queryWrapper.le(TicketOrder::getCreateTime, DateUtil.endOfDay(qry.getCreateDateEnd()));
        }
        if (!userAware.getMchId().equals(-1L)) {
            queryWrapper.eq(Objects.nonNull(qry.getDistributeMerchantId()), TicketOrder::getMchId, qry.getDistributeMerchantId());
            queryWrapper.eq(Objects.nonNull(qry.getSupplierMerchantId()), TicketOrder::getSupplierMerchantId, qry.getSupplierMerchantId());
        }
        queryWrapper.like(StrUtil.isNotBlank(qry.getLinkName()), TicketOrder::getLinkName, qry.getLinkName());
        queryWrapper.like(StrUtil.isNotBlank(qry.getLinkMobile()), TicketOrder::getLinkMobile, qry.getLinkMobile());
        queryWrapper.eq(StrUtil.isNotBlank(qry.getUid()), TicketOrder::getPromoterCode, qry.getUid());
        if (Objects.nonNull(qry.getLimit()) && qry.isExportFlag()) {
            queryWrapper.last("limit " + qry.getLimit());
        }
        return queryWrapper;
    }

    private Set<String> ignoresColumnData() {
        Set<String> ignoresColumnSet = new HashSet<>();
        ignoresColumnSet.add(TicketOrder.DELETE_FLAG);
        ignoresColumnSet.add(TicketOrder.REAL_AMOUNT);
        ignoresColumnSet.add(TicketOrder.REFUNDED_AMOUNT);
        ignoresColumnSet.add(TicketOrder.MCH_ID);
        ignoresColumnSet.add(TicketOrder.CLIENT_IP);
        ignoresColumnSet.add(TicketOrder.EXTRA);
        ignoresColumnSet.add(TicketOrder.CHANNEL_SRC);
        ignoresColumnSet.add(TicketOrder.SNAPSHOOT_VERSION);
        ignoresColumnSet.add(TicketOrder.DOUYIN_SETTLE_STATUS);
        ignoresColumnSet.add(TicketOrder.LAST_UPDATE_BY_NAME);
        ignoresColumnSet.add(TicketOrder.LAST_UPDATE_TIME);
        ignoresColumnSet.add(TicketOrder.LAST_UPDATE_BY_ID);
        ignoresColumnSet.add(TicketOrder.CREATE_BY_ID);
        ignoresColumnSet.add(TicketOrder.CREATE_BY_NAME);
        return ignoresColumnSet;
    }
}
