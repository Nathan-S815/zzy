package com.nuwa.client.ticket.dto.clientobject.mall.co;

import cn.hutool.core.bean.BeanUtil;
import lombok.Data;

import java.util.Map;

/**
 * VideoOrderNotifyParam 异步回调
 *
 * @author hy
 * @date 2020/10/27 14:26
 * @since 1.0.0
 */

@Data
public class MallOrderNotifyCO {
    private String mchId;
    private String orderNo;
    private String amount;
    private String platOrderNo;
    private String status;
    private String bankSerialNo;
    private String sign;

    private String mapData;

    public static MallOrderNotifyCO mapToObj(Map<String, String> mapData) {
        MallOrderNotifyCO parameter = new MallOrderNotifyCO();
        BeanUtil.fillBeanWithMap(mapData, parameter, true, true);
        return parameter;
    }
}
