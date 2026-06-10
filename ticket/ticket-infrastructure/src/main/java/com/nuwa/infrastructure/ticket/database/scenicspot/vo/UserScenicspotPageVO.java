package com.nuwa.infrastructure.ticket.database.scenicspot.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 商户应用
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "用户景区 UserScenicspotPageVO 对象")
public class UserScenicspotPageVO extends Model<UserScenicspotPageVO> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id")
    private Long id;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("景区类型")
    private String typeName;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty("起售价格")
    private BigDecimal priceMin;

    @ApiModelProperty("景区等级（1,2,3,4,5）")
    private Integer grade;

    @ApiModelProperty("景区等级名称")
    private String gradeName;

    @ApiModelProperty("展示主图")
    @JsonSerialize(using = MaterialJson.class)
    private String mainPicture;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("距离中心点距离(km)")
    private BigDecimal distance;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("联系人电话")
    private String salesmanTelephone;

    @ApiModelProperty("poi类型  scenic|venue")
    private String poiType;

    @ApiModelProperty("景区标签列表")
    private List<ScenicspotLabelVO> labelList;

}
