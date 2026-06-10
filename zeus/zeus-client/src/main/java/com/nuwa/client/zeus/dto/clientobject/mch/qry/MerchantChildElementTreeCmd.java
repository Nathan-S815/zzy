package com.nuwa.client.zeus.dto.clientobject.mch.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * CreateMerchantCmd 创建商户
 *
 * @author hy
 * @date 2021/6/2 16:17
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "查询子账号菜单权限-命令")
public class MerchantChildElementTreeCmd extends NuwaCommand {

    private Long userId;

    @ApiModelProperty(value = "二级应用appId", required = true)
    private Long id;

    @ApiModelProperty(value = "支付宝微信公众号appId", required = true)
    private Long appId;

}
