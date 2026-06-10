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
 * 一码通调用记录 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2022-10-29
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "一码通调用记录PageQry")
public class OneOpenApiRecordPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "记录时间开始")
    private Date createTimeStart;

    @ApiModelProperty(value = "记录时间结束")
    private Date createTimeEnd;

    @ApiModelProperty("扫码商户id")
    private Long scanMchId;

    @ApiModelProperty("一码通平台商户id")
    private Long oneMchId;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("身份证姓名")
    private String idName;

    @ApiModelProperty("手机号")
    private String mobile;
}
