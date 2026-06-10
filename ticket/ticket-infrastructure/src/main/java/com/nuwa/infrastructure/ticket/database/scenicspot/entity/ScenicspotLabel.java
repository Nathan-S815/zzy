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
 * 景区标签表
 *
 * @author huyonghack@163.com
 * @since 2021-10-20
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "ScenicspotLabel对象")
public class ScenicspotLabel extends Model<ScenicspotLabel> {
    private static final long serialVersionUID = 1L;

    private Long labelId;

    private Long scenicSpotId;


    public static final String LABEL_ID = "label_id";

    public static final String SCENIC_SPOT_ID = "scenic_spot_id";

}
