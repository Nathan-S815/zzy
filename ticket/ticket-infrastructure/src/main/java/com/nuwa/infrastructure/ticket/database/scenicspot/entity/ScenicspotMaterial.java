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
 * 景区图文表
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotMaterial对象")
public class ScenicspotMaterial extends Model<ScenicspotMaterial> {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("景区id")
    private Long scenicSpotId;

    @ApiModelProperty("素材id")
    private Long materialId;

    @ApiModelProperty("标签名")
    private String label;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("类型,pic,video")
    private String type;

    @ApiModelProperty("权重")
    private Integer weight;


    public static final String ID = "id";

    public static final String SCENIC_SPOT_ID = "scenic_spot_id";

    public static final String MATERIAL_ID = "material_id";

    public static final String LABEL = "label";

    public static final String TITLE = "title";

    public static final String TYPE = "type";

    public static final String WEIGHT = "weight";

}
