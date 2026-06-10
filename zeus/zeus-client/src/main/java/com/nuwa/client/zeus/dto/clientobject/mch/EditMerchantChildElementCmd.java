package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.client.zeus.dto.clientobject.mch.co.ElementCO;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.List;

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
@ApiModel(value = "子账号编辑菜单权限-命令")
public class EditMerchantChildElementCmd extends NuwaCommand {

    @ApiModelProperty(value = "子账号Id", required = true)
    @NotNull(message = "子账号Id不能为空")
    private Long userId;

    private List<ElementCO> elements;

    @ApiModelProperty(value = "二级应用appId", required = true)
    private Long id;

    @ApiModelProperty(value = "支付宝微信公众号appId", required = true)
    private Long appId;

}
