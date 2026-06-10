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
import java.util.Date;

/**
 * 商户应用
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "MerchantApp对象")
public class MerchantScenicspotPageVO extends Model<MerchantScenicspotPageVO> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("原始景区id")
    @TableId(value = "src_id")
    private Long srcId;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("景区类型")
    private String typeName;

    @ApiModelProperty("logo")
    private String logo;

    @ApiModelProperty("网站")
    private String site;

    @ApiModelProperty("介绍")
    private String memo;

    @ApiModelProperty("联系电话")
    private String tel;

    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty("省id")
    private String provinceId;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty("市id")
    private String cityId;

    @ApiModelProperty("区名称")
    private String area;

    @ApiModelProperty("区id")
    private String areaId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("优惠政策")
    private String preferential;

    @ApiModelProperty("状态[-1草稿;0:待审核;1:审核通过;2审核不通过]IMQ_eq")
    private Integer status;

    @ApiModelProperty("排序权重(越小越靠前)")
    private Integer weight;

    @ApiModelProperty("联系人姓名")
    private String salesman;

    @ApiModelProperty("联系人电话")
    private String salesmanTelephone;

    @ApiModelProperty("起售价格")
    private BigDecimal priceMin;

    @ApiModelProperty("景区等级（1,2,3,4,5）")
    private Integer grade;

    @ApiModelProperty("开放时间")
    private String openTime;

    @ApiModelProperty("用时建议")
    private String useTimeProposal;

    @ApiModelProperty("游玩季节")
    private String bestPlaySeason;

    @ApiModelProperty("交通信息")
    private String trafficInfo;

    @ApiModelProperty("拒绝原因")
    private String rejectReason;

    @ApiModelProperty("展示主图")
    @JsonSerialize(using = MaterialJson.class)
    private String mainPicture;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("poi类型  scenic|venue")
    private String poiType;

}
