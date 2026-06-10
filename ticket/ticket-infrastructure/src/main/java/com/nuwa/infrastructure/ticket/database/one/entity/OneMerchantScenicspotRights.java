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
 * 一码通商户景区权益配置
 *
 * @author huyonghack@163.com
 * @since 2022-10-25
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OneMerchantScenicspotRights对象")
public class OneMerchantScenicspotRights extends Model<OneMerchantScenicspotRights> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("权益id(-1代表通用权益)")
    private Long rightsId;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("商户id")
    private Long merchantId;


    public static final String ID = "id";

    public static final String RIGHTS_ID = "rights_id";

    public static final String SCENICSPOT_ID = "scenicspot_id";

    public static final String MERCHANT_ID = "merchant_id";

}
