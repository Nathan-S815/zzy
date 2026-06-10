package com.nuwa.infrastructure.ticket.database.product.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 分销商产品分页VO
 *
 * @author hy
 */
@Data
public class MerchantProductDistributePageVO {
    @ApiModelProperty("产品id")
    private Long id;

    @ApiModelProperty("所属景点id")
    private Long scenicspotId;

    @ApiModelProperty("产品名称")
    private String name;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("供应商id")
    private Long supplierMerchantId;

    @ApiModelProperty("供应商名称")
    private String supplierMerchantName;

    @ApiModelProperty("销售订单数")
    private Integer sellOrder;

    @ApiModelProperty("状态 0:未上架 1:已上架")
    private Integer publishStatus;

    @ApiModelProperty("供应商状态 0:未上架 1:已上架")
    private Integer supplierPublishStatus;

    @ApiModelProperty("审核状态 待审核:0  审核通过:1 审核拒绝:2")
    private Integer auditStatus;

    @ApiModelProperty("景点POI名称")
    private String scenicspotName;

    @ApiModelProperty("上架时间")
    private Date publishTime;

    @ApiModelProperty("申请时间")
    private Date createTime;

    @ApiModelProperty("产品分销id")
    private Long productDistributeId;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMerchantId;

    @ApiModelProperty("分销商-商户名称")
    private String distributeName;

    @ApiModelProperty("分销商-商户联系人姓名")
    private String distributeLinkName;

    @ApiModelProperty("分销商-商户联系人手机号")
    private String distributeLinkMobile;

    @ApiModelProperty("审核备注信息")
    private String rejectReason;

    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty("区名称")
    private String area;
}
