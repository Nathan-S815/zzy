package com.nuwa.client.zeus.dto.clientobject.mch;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

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
@ApiModel(value = "启用商户子账号-命令")
public class EnableMerchantChildCmd extends NuwaCommand {

    @ApiModelProperty(value = "Id", required = true)
    @NotNull(message = "Id不能为空")
    private Integer id;

}
