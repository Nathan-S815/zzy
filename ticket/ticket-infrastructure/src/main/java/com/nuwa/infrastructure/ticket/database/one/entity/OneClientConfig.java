package com.nuwa.infrastructure.ticket.database.one.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 一码通端口配置
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneClientConfig对象")
public class OneClientConfig extends Model<OneClientConfig> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("AppId")
    private String outAppId;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("应用类型 weixin_mp(公众号)")
    private String appType;

    @ApiModelProperty("App 密钥")
    private String appSecret;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("业务类型 identity_code(身份码) verification_code(核销码)")
    private String bizList;

    @ApiModelProperty("背景图片id")
    @JsonSerialize(using = MaterialJson.class)
    private String backgroundPictureId;

    @ApiModelProperty("背景颜色")
    private String backgroundColor;

    @ApiModelProperty("备注信息")
    private String remark;

    @ApiModelProperty("状态 on|off")
    private String status;


    public static final String ID = "id";

    public static final String OUT_APP_ID = "out_app_id";

    public static final String NAME = "name";

    public static final String APP_TYPE = "app_type";

    public static final String APP_SECRET = "<REDACTED>";

    public static final String MCH_ID = "mch_id";

    public static final String BIZ_LIST = "biz_list";

    public static final String BACKGROUND_PICTURE_ID = "background_picture_id";

    public static final String BACKGROUND_COLOR = "background_color";

    public static final String REMARK = "remark";

    public static final String STATUS = "status";

}
