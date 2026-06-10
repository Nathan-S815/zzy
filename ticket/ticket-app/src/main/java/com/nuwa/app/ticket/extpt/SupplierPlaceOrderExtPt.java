package com.nuwa.app.ticket.extpt;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionPointI;
import com.nuwa.framework.base.UserAware;

/**
 * 供应商下单扩展点
 *
 * @author hy
 */
public interface SupplierPlaceOrderExtPt extends ExtensionPointI {
    /**
     * 供应商下单
     *
     * @param orderId 订单号
     * @return SingleResponse
     */
    public SingleResponse<?> placeOrder(Long orderId);
}
