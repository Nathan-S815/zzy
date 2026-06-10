package com.nuwa.infrastructure.ticket.database.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商家产品分销表
 *
 * @author huyonghack@163.com
 * @since 2022-02-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantProductDistribute对象")
public class MerchantProductDistribute extends Model<MerchantProductDistribute> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("产品id")
    private Long productId;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("供应商名称")
    private String supplierMerchantName;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMerchantId;

    @ApiModelProperty("审核状态 待审核:0  审核通过:1 审核拒绝:2 ")
    private Integer auditStatus;

    @ApiModelProperty("拒绝原因")
    private String rejectReason;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("上架状态   上架:1 下架:0")
    private Integer publishStatus;

    @ApiModelProperty("上架时间")
    private Date publishTime;

    @ApiModelProperty("创建时间")
    private Date createTime;

    private String createByName;

    private String createById;

    private String lastUpdateByName;

    private String lastUpdateById;

    private Date lastUpdateTime;


    public static final String ID = "id";

    public static final String PRODUCT_ID = "product_id";

    public static final String SUPPLIER_MERCHANT_ID = "supplier_merchant_id";

    public static final String SUPPLIER_MERCHANT_NAME = "supplier_merchant_name";

    public static final String DISTRIBUTE_MERCHANT_ID = "distribute_merchant_id";

    public static final String AUDIT_STATUS = "audit_status";

    public static final String REJECT_REASON = "reject_reason";

    public static final String WEIGHT = "weight";

    public static final String PUBLISH_STATUS = "publish_status";

    public static final String PUBLISH_TIME = "publish_time";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String LAST_UPDATE_BY_NAME = "last_update_by_name";

    public static final String LAST_UPDATE_BY_ID = "last_update_by_id";

    public static final String LAST_UPDATE_TIME = "last_update_time";

}
