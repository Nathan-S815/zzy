package com.nuwa.client.ticket.dto.clientobject.one.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 一码通商户端可用身份认 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通商户端可用身份认PageQry")
public class OneMerchantUsableIdentityPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    private Long mchId;

}
