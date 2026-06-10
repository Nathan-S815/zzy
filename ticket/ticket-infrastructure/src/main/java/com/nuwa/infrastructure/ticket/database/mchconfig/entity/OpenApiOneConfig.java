package com.nuwa.infrastructure.ticket.database.mchconfig.entity;

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
 * 一码通开放API
 *
 * @author huyonghack@163.com
 * @since 2022-09-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OpenApiOneConfig对象")
public class OpenApiOneConfig extends Model<OpenApiOneConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("app_key")
    private String appKey;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("App 密钥")
    private String appSecret;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("业务类型")
    private String bizList;


    public static final String ID = "id";

    public static final String APP_KEY = "app_key";

    public static final String NAME = "name";

    public static final String APP_SECRET = "<REDACTED>";

    public static final String MCH_ID = "mch_id";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String BIZ_LIST = "biz_list";

}
