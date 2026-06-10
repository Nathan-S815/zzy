package com.nuwa.ticket.start.api.controller.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author hy
 */
@Data
public class ScenicspotBaseInfoDTO {
    @ApiModelProperty(value = "景区名称", required = true)
    @NotBlank(message = "景区名称不能为空")
    private String name;

    @ApiModelProperty("logo")
    @JsonSerialize(using = MaterialJson.class)
    private String logo;

    @ApiModelProperty(value = "poiType[scenic(景区) venue(文博)]")
    private String poiType;

    @ApiModelProperty(value = "景点类型ids[多个,隔开]", required = true)
    @NotBlank(message = "typeIds不能为空")
    private String typeIds;

    @ApiModelProperty(value = "景点标签ids[多个,隔开]", required = true)
    @NotBlank(message = "labelIds不能为空")
    private String labelIds;

    @ApiModelProperty("网站")
    private String site;

    @ApiModelProperty("介绍")
    private String memo;

    @ApiModelProperty("温馨提示")
    private String tips;

    @ApiModelProperty("省名称")
    private String province;

    @ApiModelProperty(value = "省id", required = true)
    @NotBlank(message = "provinceId不能为空")
    private String provinceId;

    @ApiModelProperty("市名称")
    private String city;

    @ApiModelProperty(value = "市id", required = true)
    @NotBlank(message = "市id不能为空")
    private String cityId;

    @ApiModelProperty("区名称")
    private String area;

    @ApiModelProperty(value = "区id", required = true)
    @NotBlank(message = "区id不能为空")
    private String areaId;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty(value = "经度", required = true)
    @NotNull(message = "经度不能为空")
    private Double longitude;

    @ApiModelProperty(value = "纬度", required = true)
    @NotNull(message = "纬度不能为空")
    private Double latitude;

    @ApiModelProperty("优惠政策")
    private String preferential;

    @ApiModelProperty(value = "联系人姓名", required = true)
    @NotBlank(message = "联系人姓名不能为空")
    private String salesman;

    @ApiModelProperty(value = "联系人电话", required = true)
    @NotBlank(message = "联系人电话不能为空")
    private String salesmanTelephone;

    @ApiModelProperty(value = "景区等级（0,3,4,5）", required = true)
    @NotNull(message = "景区等级不能为空")
    private Integer grade;

    @ApiModelProperty(value = "开放时间", required = true)
    @NotBlank(message = "开放时间不能为空")
    private String openTime;

    @ApiModelProperty("用时建议")
    private String useTimeProposal;

    @ApiModelProperty("游玩季节")
    private String bestPlaySeason;

    @ApiModelProperty("交通信息")
    private String trafficInfo;

    @ApiModelProperty(value = "适合人群", required = true)
    @NotBlank(message = "适合人群不能为空")
    private String suitedPeople;

    @ApiModelProperty(value = "展示主图", required = true)
    @NotBlank(message = "展示主图不能为空")
    @JsonSerialize(using = MaterialJson.class)
    private String mainPicture;
}
