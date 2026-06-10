package com.nuwa.client.ticket.dto.clientobject.mall.co;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * MallRefundNotifyCO
 *
 * @author hy
 * @date 2020/11/21 13:09
 * @since 1.0.0
 */
@Data
public class MallRefundNotifyCO implements Serializable {

    /**
     * 商户号
     */
    private String mchId;

    /**
     * 商户订单号
     */
    private String orderNo;

    /**
     * 金额（分）
     */
    private String amount;

    /**
     * 平台订单号
     */
    private String platOrderNo;

    /**
     * 状态
     */
    private String status;

    /**
     * 签名
     */
    private String sign;

    private String mapData;


    public static MallRefundNotifyCO mapToObj(Map<String, String> mapData) {
        MallRefundNotifyCO parameter = new MallRefundNotifyCO();
        BeanUtil.fillBeanWithMap(mapData, parameter, true, true);
        return parameter;
    }
}
