package com.nuwa.client.ticket.dto.clientobject.one.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


/**
 * <pre>
 * 一码通广告配置 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-08-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通广告配置PageQry")
public class OneAdConfigPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("一码通端口id")
    private Long oneClientId;

}
