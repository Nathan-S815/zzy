package com.nuwa.ticket.start.api.controller.scenicspot.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.database.scenicspot.vo.ScenicspotLabelVO;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ScenicspotDetailVO {
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("景区名称")
    private String name;

    @ApiModelProperty("介绍")
    private String memo;

    @ApiModelProperty("联系电话")
    private String tel;

    @ApiModelProperty("温馨提示")
    private String tips;

    @ApiModelProperty("类型名称")
    private String typeName;

    @ApiModelProperty("详细地址")
    private String address;

    @ApiModelProperty("经度")
    private Double longitude;

    @ApiModelProperty("纬度")
    private Double latitude;

    @ApiModelProperty("优惠政策")
    private String preferential;

    @ApiModelProperty("联系人姓名")
    private String salesman;

    @ApiModelProperty("联系人电话")
    private String salesmanTelephone;

    @ApiModelProperty("起售价格")
    private BigDecimal priceMin;

    @ApiModelProperty("景区等级（1,2,3,4,5）")
    private Integer grade;

    @ApiModelProperty("景区等级名称")
    private String gradeName;

    @ApiModelProperty("开放时间")
    private String openTime;

    @ApiModelProperty("用时建议")
    private String useTimeProposal;

    @ApiModelProperty("游玩季节")
    private String bestPlaySeason;

    @ApiModelProperty("poi类型  scenic|venue")
    private String poiType;

    @ApiModelProperty("适合人群")
    private List<String> suitedPeopleNames;

    @ApiModelProperty("交通信息")
    private String trafficInfo;

    @ApiModelProperty("图片列表")
    @JsonSerialize(using = MaterialJson.class)
    private String pictureList;

    @ApiModelProperty("展示主图")
    @JsonSerialize(using = MaterialJson.class)
    private String mainPicture;

    @ApiModelProperty("景区标签列表")
    private List<ScenicspotLabelVO> labelList;
}
