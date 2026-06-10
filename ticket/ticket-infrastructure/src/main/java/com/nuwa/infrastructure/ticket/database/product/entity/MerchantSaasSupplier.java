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
 * 商户saas平台供应商信息
 *
 * @author huyonghack@163.com
 * @since 2022-02-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantSaasSupplier对象")
public class MerchantSaasSupplier extends Model<MerchantSaasSupplier> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("供应商-商户id")
    private Long supplierMerchantId;

    @ApiModelProperty("分销商-商户id")
    private Long distributeMerchantId;

    @ApiModelProperty("供应商-商户名称")
    private String supplierName;

    @ApiModelProperty("分销商-商户名称")
    private String distributeName;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String SUPPLIER_MERCHANT_ID = "supplier_merchant_id";

    public static final String DISTRIBUTE_MERCHANT_ID = "distribute_merchant_id";

    public static final String SUPPLIER_NAME = "supplier_name";

    public static final String DISTRIBUTE_NAME = "distribute_name";

    public static final String CREATE_TIME = "create_time";

}
