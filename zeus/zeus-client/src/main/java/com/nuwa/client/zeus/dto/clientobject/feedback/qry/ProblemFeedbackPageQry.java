package com.nuwa.client.zeus.dto.clientobject.feedback.qry;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;


/**
 * <pre>
 * 商户问题反馈信息 PageQry参数对象
 * </pre>
 *
 * @author huyonghack@163.com
 * @date 2021-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "商户问题反馈信息PageQry")
public class ProblemFeedbackPageQry extends NuwaPageQry {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "日起开始时间 yyyy-MM-dd HH:mm:ss")
    private Date createTimeStart;

    @ApiModelProperty(value = "日起结束时间 yyyy-MM-dd HH:mm:ss")
    private Date createTimeEnd;

    @ApiModelProperty("商户信息")
    private String mchName;

    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("注册手机号")
    private String registPhone;

    @ApiModelProperty("联系人")
    private String contactPeople;

    @ApiModelProperty("联系电话")
    private String contactPhone;


}
