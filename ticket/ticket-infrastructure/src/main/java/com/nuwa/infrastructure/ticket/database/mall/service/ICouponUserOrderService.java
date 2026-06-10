package com.nuwa.infrastructure.ticket.database.mall.service;

public interface ICouponUserOrderService {


    void useCoupon(String couponNo,String openId,String orderId);

    /***
     * 设置优惠券使用状态
     * @param couponNo
     * @param status couponUser status
     */
    void setCouponStatus(String couponNo, int status);
}
