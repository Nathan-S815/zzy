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
 * 景区分类表
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotType对象")
public class ScenicspotType extends Model<ScenicspotType> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "type_id")
    private Long typeId;

    @TableId(value = "scenic_spot_id")
    private Long scenicSpotId;

    public static final String TYPE_ID = "type_id";

    public static final String SCENIC_SPOT_ID = "scenic_spot_id";

}
