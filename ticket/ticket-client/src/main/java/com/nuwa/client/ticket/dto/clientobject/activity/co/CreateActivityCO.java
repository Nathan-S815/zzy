package com.nuwa.client.ticket.dto.clientobject.activity.co;

import com.nuwa.framework.cola.starter.dto.NuwaCO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * PaidOrderCO 订单支付完成
 *
 * @author hy
 * @date 2021/4/16 13:54
 * @since 1.0.0
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "新增活动")
public class CreateActivityCO extends NuwaCO {

    @ApiModelProperty(value = "活动名称")
    private String activityTitle;

    @ApiModelProperty(value = "活动开始时间")
    private Date holdTimeStart;

    @ApiModelProperty(value = "活动结束时间")
    private Date holdTimeEnd;

    @ApiModelProperty(value = "图片地址,多个逗号隔开")
    private String imageList;

    @ApiModelProperty(value = "活动票数")
    private Integer ticketsNum;

    @ApiModelProperty(value = "活动分类(10教育,11演出,12文艺,13公益,14户外旅游,15讲座,16展览,17医疗)")
    private Integer activityType;

    @ApiModelProperty(value = "活动地址")
    private String address;

    @ApiModelProperty(value = "举办方")
    private String organizer;

    @ApiModelProperty(value = "活动详情")
    private String detailContentEditor;

    @ApiModelProperty(value = "温馨提示")
    private String tipContentEditor;

}
