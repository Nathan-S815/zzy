package com.nuwa.app.ticket.command.query;

import com.baomidou.mybatisplus.annotation.TableId;
import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import com.nuwa.framework.database.tk.join.annotation.JoinColumn;
import com.nuwa.infrastructure.ticket.database.scenicspot.entity.Scenicspot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "用户景区分页查询")
public class UserScenicspotPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("ids")
    private List<Long> ids;

    @ApiModelProperty("商戶id")
    @NotNull(message = "商戶id不能为空")
    private String mchId;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("景区类型")
    private Long typeId;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("排序方式 0:热度排序 1:按星级倒序 2:按距离排序  3:价格倒序 4:价格正序")
    private Integer sortType;

    @ApiModelProperty("查询半径距离(km)")
    private Integer radiusKm;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    private String poiType;
}
