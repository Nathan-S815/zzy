package com.nuwa.app.discovery.command.query;

import com.nuwa.framework.cola.starter.dto.NuwaPageQry;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "景点分页PageQry")
public class MchScenicspotPageJoinQry extends NuwaPageQry {

    @ApiModelProperty("状态[-1草稿;0:待审核;1:审核通过;2审核不通过]")
    private Integer status;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("景区类型")
    private Long typeId;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("区id")
    private String areaId;

    @ApiModelProperty("景区等级（1,2,3,4,5）")
    private Integer grade;

    @ApiModelProperty(value = "版本标志[1正式 0副本]", hidden = true)
    private Integer versionFlag;
}
