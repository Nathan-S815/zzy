package com.nuwa.infrastructure.ticket.database.scenicspot.entity;

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
 * 商户关联景区POI表
 *
 * @author huyonghack@163.com
 * @since 2021-10-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantScenicspotPoi对象")
public class MerchantScenicspotPoi extends Model<MerchantScenicspotPoi> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("景区id")
    private Long scenicSpotId;

    @ApiModelProperty("商户")
    private Long merchantId;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("状态 1:启用 0:禁用")
    private Integer status;


    public static final String SCENIC_SPOT_ID = "scenic_spot_id";

    public static final String MERCHANT_ID = "merchant_id";

    public static final String WEIGHT = "weight";

    public static final String STATUS = "status";

}
