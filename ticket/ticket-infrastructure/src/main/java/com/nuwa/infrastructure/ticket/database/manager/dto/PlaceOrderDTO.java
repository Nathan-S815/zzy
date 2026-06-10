package com.nuwa.infrastructure.ticket.database.manager.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.client.ticket.dto.clientobject.order.TouristDTO;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.ticket.database.product.entity.MerchantSupplierConfig;
import com.nuwa.infrastructure.ticket.database.product.entity.ProductPriceEveryday;
import com.nuwa.infrastructure.ticket.database.product.entity.ScenicspotProduct;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 创建订单
 *
 * @author hy
 */
@Data
public class PlaceOrderDTO {
    private UserAware userAware;

    @ApiModelProperty("产品id")
    @NotNull(message = "产品id不能为空")
    private ScenicspotProduct product;

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

    @ApiModelProperty("推广人用户id")
    private String promoterUserId;

    @ApiModelProperty("游客列表")
    private List<TouristDTO> touristList;

    @ApiModelProperty("clientIp")
    private String clientIp;

    @ApiModelProperty("价格日历")
    private ProductPriceEveryday priceEveryday;

    @ApiModelProperty("供应商配置")
    private MerchantSupplierConfig supplierConfig;

    @ApiModelProperty("扩展字段")
    private Map<String, Object> extData;

    @ApiModelProperty("场次id")
    private Long sessionId;

    @ApiModelProperty("srcAppId")
    private Long srcAppId;

    @ApiModelProperty("clientSrc")
    private String clientSrc;

    @ApiModelProperty("srcAppName")
    private String srcAppName;
}
