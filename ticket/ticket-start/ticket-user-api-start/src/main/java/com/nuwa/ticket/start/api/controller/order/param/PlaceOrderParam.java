package com.nuwa.ticket.start.api.controller.order.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.ticket.start.api.controller.dto.TouristDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 下单参数
 *
 * @author hy
 */
@Data
public class PlaceOrderParam {

    @ApiModelProperty("产品id")
    @NotNull(message = "产品id不能为空")
    private Long productId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("购买数量")
    private Integer quantity;

    @ApiModelProperty("游玩日期[yyyy-MM-dd]")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date visitDate;

    @ApiModelProperty("联系人姓名")
    private String linkName;

    @ApiModelProperty("联系人手机号")
    private String linkMobile;

    @ApiModelProperty("联系人身份证号码")
    private String linkIdCard;

    @ApiModelProperty("游客列表")
    private List<TouristDTO> touristList;

    @ApiModelProperty("扩展字段")
    private Map<String, Object> extData;

    @ApiModelProperty("推广人用户id")
    private String promoterUserId;
}
