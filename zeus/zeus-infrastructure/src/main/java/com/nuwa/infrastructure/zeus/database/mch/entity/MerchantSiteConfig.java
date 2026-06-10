package com.nuwa.infrastructure.zeus.database.mch.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.zeus.util.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantSiteConfig对象")
public class MerchantSiteConfig extends Model<MerchantSiteConfig> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("商户ID")
    private Long mchId;

    @ApiModelProperty("配置类型 1:登录页 2:管理首页")
    private Integer type;

    @ApiModelProperty("联系人姓名")
    private String contactName;

    @ApiModelProperty("联系方式")
    private String contactType;

    @ApiModelProperty("qq")
    private String qq;

    @ApiModelProperty("logo地址")
    @JsonSerialize(using = MaterialJson.class)
    private Long logo;

    @ApiModelProperty("背景图片地址")
    @JsonSerialize(using = MaterialJson.class)
    private String bgImg;

    @ApiModelProperty("域名")
    private String domain;

    @ApiModelProperty("备案号")
    private String websiteApproveNo;


    public static final String ID = "id";

    public static final String MCH_ID = "mch_id";

    public static final String TYPE = "type";

    public static final String CONTACT_NAME = "contact_name";

    public static final String CONTACT_TYPE = "contact_type";

    public static final String QQ = "qq";

    public static final String LOGO = "logo";

    public static final String BG_IMG = "bg_img";

    public static final String DOMAIN = "domain";

    public static final String WEBSITE_APPROVE_NO = "website_approve_no";

}
