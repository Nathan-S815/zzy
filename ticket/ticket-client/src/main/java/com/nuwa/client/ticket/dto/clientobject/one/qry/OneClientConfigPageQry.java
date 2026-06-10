package com.nuwa.client.ticket.dto.clientobject.one.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


/**
 * <pre>
 * 一码通端口配置 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通端口配置PageQry")
public class OneClientConfigPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("appId")
    private String outAppId;

    @ApiModelProperty("应用类型 weixin_mp(公众号)")
    private String appType;

    @ApiModelProperty("名称")
    private String name;
}
