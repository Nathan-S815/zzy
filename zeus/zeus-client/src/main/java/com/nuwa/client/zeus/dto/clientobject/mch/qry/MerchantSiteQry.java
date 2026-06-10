package com.nuwa.client.zeus.dto.clientobject.mch.qry;

import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;


/**
 * <pre>
 * 商户信息 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-05-31
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户配置Qry")
public class MerchantSiteQry extends NuwaCommand {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商户Id", required = true)
    @NotNull(message = "商户Id不能为空")
    private Integer mchId;

    @ApiModelProperty(value = "配置类型 1:登录页 2:管理首页", required = true)
    @NotNull(message = "配置类型不能为空")
    private Integer type;

}
