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
 * 商户供应商配置
 *
 * @author huyonghack@163.com
 * @since 2021-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantSupplierConfig对象")
public class MerchantSupplierConfig extends Model<MerchantSupplierConfig> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("供应商id")
    private Long supplierId;

    @ApiModelProperty("商户id")
    private Long merchantId;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("删除标志")
    private Integer deleteFlag;

    @ApiModelProperty("创建人id")
    private String createById;

    @ApiModelProperty("创建人姓名")
    private String createByName;

    @ApiModelProperty("渠道商户id")
    private String channelMerchantId;

    @ApiModelProperty("渠道秘钥")
    private String channelSecretKey;

    @ApiModelProperty("渠道加密秘钥")
    private String channelEncryptSecretKey;

    @ApiModelProperty("渠道加密向量")
    private String channelEncryptVector;

    @ApiModelProperty("1:正常,0：停用")
    private Integer status;

    @ApiModelProperty("分销商渠道过期时间")
    private Date expirationDate;

    @ApiModelProperty("接口地址")
    private String apiUrl;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String SUPPLIER_ID = "supplier_id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String WEIGHT = "weight";

    public static final String CREATE_TIME = "create_time";

    public static final String DELETE_FLAG = "delete_flag";

    public static final String CREATE_BY_ID = "create_by_id";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CHANNEL_MERCHANT_ID = "channel_merchant_id";

    public static final String CHANNEL_SECRET_KEY = "<REDACTED>";

    public static final String CHANNEL_ENCRYPT_SECRET_KEY = "<REDACTED>";

    public static final String CHANNEL_ENCRYPT_VECTOR = "channel_encrypt_vector";

    public static final String STATUS = "status";

    public static final String EXPIRATION_DATE = "expiration_date";

    public static final String API_URL = "api_url";

}
