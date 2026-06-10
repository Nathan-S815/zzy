package com.nuwa.client.ticket.dto.clientobject.complaint.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * PaidOrderCO 订单支付完成
 *
 * @author hy
 * @date 2021/4/16 13:54
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增投诉")
public class CreateComplaintCO extends NuwaCO {

    @ApiModelProperty(value = "投诉对象")
    private String target;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "投诉内容")
    private String complaintContent;

    @ApiModelProperty(value = "图片(多个逗号分隔)")
    private String pic;

    @ApiModelProperty(value = "联系人")
    private String contact;

    @ApiModelProperty(value = "投诉人电话")
    private String tel;

}
