package com.nuwa.client.ticket.dto.clientobject.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.framework.cola.starter.dto.NuwaCommand;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * PlaceOrderCmd 用户下单命令
 *
 * @author hy
 * @date 2022/01/04 14:55
 * @since 1.0.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户下单")
@ToString
public class PlaceOrderCmd extends NuwaCommand {
    @ApiModelProperty("产品id")
    @NotNull(message = "产品id不能为空")
    private Long productId;

    @ApiModelProperty("场次id")
    private Long sessionId;

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

    @ApiModelProperty("localAppId")
    //@NotNull(message = "localAppId")
    private Long localAppId;
}
