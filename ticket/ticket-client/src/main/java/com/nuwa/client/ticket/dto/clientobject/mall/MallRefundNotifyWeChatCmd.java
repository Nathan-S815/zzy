package com.nuwa.client.ticket.dto.clientobject.mall;


import com.nuwa.client.ticket.dto.clientobject.mall.co.MallRefundNotifyWeChatCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "退款回调命令")
public class MallRefundNotifyWeChatCmd extends NuwaCommand {

    private static final long serialVersionUID = 1L;

    private MallRefundNotifyWeChatCO mallRefundNotifyWeChatCO;

}
