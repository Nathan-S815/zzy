package com.nuwa.ticket.start.api.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "ProductBaseInfoDTO对象")
public class ProductBaseInfoDTO {

    @ApiModelProperty("所属景点id")
    private Long scenicspotId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("产品简称")
    private String shortName;

    @ApiModelProperty("省")
    private String provinceId;

    @ApiModelProperty("省")
    private String province;

    @ApiModelProperty("市")
    private String cityId;

    @ApiModelProperty("市")
    private String city;

    @ApiModelProperty("区")
    private String areaId;

    @ApiModelProperty("区")
    private String area;

    @ApiModelProperty("票种")
    private Integer ticketKind;

    @ApiModelProperty("费用说明")
    private String feeInfo;

    @ApiModelProperty("服务电话")
    private String servicePhone;

    @ApiModelProperty("产品图片列表，多个逗号隔开")
    private String pictureItems;

    @ApiModelProperty("产品主图")
    private String mainPicture;

    @ApiModelProperty("价格模式 （0:普通,1:日历）")
    private Integer priceMode;

    @ApiModelProperty("供应商产品编码")
    private String supplierProductCode;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    private Integer status;

    @ApiModelProperty("供应商id")
    private String supplierId;

    @ApiModelProperty("成人数量")
    private Integer adultNumber;

    @ApiModelProperty("儿童数量")
    private Integer childNumber;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("销售总金额")
    private BigDecimal sellMoney;

    @ApiModelProperty("销售数量")
    private Integer sellNumber;

    @ApiModelProperty("上架时间")
    private Date publishTime;

    @ApiModelProperty("创建时间")
    private Date createTime;
}
