package com.nuwa.app.attract.extpt;

import com.alibaba.cola.dto.SingleResponse;
import com.alibaba.cola.extension.ExtensionPointI;

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
    SingleResponse<?> placeOrder(Long orderId);
}
