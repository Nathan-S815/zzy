package com.nuwa.infrastructure.ticket.database.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一码通服务商开放API
 *
 * @author huyonghack@163.com
 * @since 2022-10-28
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ServiceOpenApiConfig对象")
public class ServiceOpenApiConfig extends Model<ServiceOpenApiConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("应用id")
    @TableId(value = "app_id", type = IdType.AUTO)
    private Long appId;

    @ApiModelProperty("服务商私钥")
    private String servicePrivateKey;

    @ApiModelProperty("服务商公钥")
    private String servicePublicKey;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("支持景区")
    private String scenicspotList;

    @ApiModelProperty("客户端类型 service_open_client|service_one_merchant_client")
    private String clientType;


    public static final String APP_ID = "app_id";

    public static final String SERVICE_PRIVATE_KEY = "service_private_key";

    public static final String SERVICE_PUBLIC_KEY = "service_public_key";

    public static final String NAME = "name";

    public static final String MCH_ID = "mch_id";

    public static final String SCENICSPOT_LIST = "scenicspot_list";

    public static final String CLIENT_TYPE = "client_type";

}
