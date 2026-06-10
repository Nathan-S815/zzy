package com.nuwa.client.ticket.dto.clientobject.one.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 一码通商户端权益配置 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通商户端权益配置PageQry")
public class OneRightsConfPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "创建时间IM开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "创建时间IM结束")
    private Date createTimeEnd;

}
