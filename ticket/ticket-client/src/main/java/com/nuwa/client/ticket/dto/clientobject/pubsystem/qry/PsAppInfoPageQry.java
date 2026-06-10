package com.nuwa.client.ticket.dto.clientobject.pubsystem.qry;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 公告表 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-03-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "支付宝服务市场app PageQry")
public class PsAppInfoPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("上下架 init:待实施  offline:下架 online:上架")
    private String appStatus;

    @ApiModelProperty("应用名称")
    private String appName;

    @ApiModelProperty("appId")
    private String appId;

    @ApiModelProperty("授权开始时间")
    private Date authBegin;

    @ApiModelProperty("授权结束时间")
    private Date authEnd;

    @ApiModelProperty("上架开始时间")
    private Date publishBegin;

    @ApiModelProperty("上架结束时间")
    private Date publishEnd;
}
