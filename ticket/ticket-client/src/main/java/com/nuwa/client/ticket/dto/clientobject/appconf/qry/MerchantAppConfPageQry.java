package com.nuwa.client.ticket.dto.clientobject.appconf.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 商户客户端配置PageQry
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-12-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户客户端配置PageQry")
public class MerchantAppConfPageQry extends NuwaPageQry {

    @ApiModelProperty(value = "商户id", hidden = true)
    private Long mchId;

    @ApiModelProperty("客户端名称")
    private String appName;

    @ApiModelProperty("小程序AppId")
    private String outAppId;

    @ApiModelProperty("应用类型 douyin")
    private String appType;

    @ApiModelProperty("所属省份id")
    private Long provinceId;

    @ApiModelProperty("所属地市id")
    private Long cityId;
}
