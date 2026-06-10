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
 * 一码通商户端可用身份认
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneMerchantUsableIdentity对象")
public class OneMerchantUsableIdentity extends Model<OneMerchantUsableIdentity> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("唯一编码")
    private String identityCode;

    @ApiModelProperty("商户id")
    private Long mchId;

    @ApiModelProperty("状态  on|off")
    private String status;

    @ApiModelProperty("排序字段 从低到高")
    private Integer sortNum;


    public static final String ID = "id";

    public static final String IDENTITY_CODE = "identity_code";

    public static final String MCH_ID = "mch_id";

    public static final String STATUS = "status";

    public static final String SORT_NUM = "sort_num";

}
