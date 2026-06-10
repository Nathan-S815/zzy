package com.nuwa.attract.start.api.controller.screen.param;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: WangXh
 * @DateTime: 2022/10/28
 * @Description: TODO
 */
@Data
@ApiModel(value = "首页数据")
public class ScreenVo {

    private static final long  serialVersionUID = 1L;

    @ApiModelProperty(value = "旅行社入驻统计")
    private Integer travelNum;

    @ApiModelProperty(value = "酒店入驻统计")
    private Integer hotelNum;

    @ApiModelProperty(value = "景区入驻统计")
    private Integer sceneryNum;

    @ApiModelProperty(value = "本地游客数量")
    private Integer localVisitor;

    @ApiModelProperty(value = "外地游客数量")
    private Integer otherPlaceVisitor;

    @ApiModelProperty(value = "旅行游客社接待排行")
    private List<Map<String,Object>> travelAgencyNum;

    @ApiModelProperty(value = "酒店游客接待排行")
    private  List<Map<String,Object>> hotelAgencyNum;

    @ApiModelProperty(value = "景区游客接待排行")
    private  List<Map<String,Object>> sceneryAgencyNum;

    @ApiModelProperty(value = "旅行社团队接待排行")
    private List<Map<String,Object>> travelTeamAgencyNum;

    @ApiModelProperty(value = "酒店团队接待排行")
    private  List<Map<String,Object>> hotelTeamAgencyNum;

    @ApiModelProperty(value = "景区团队接待排行")
    private  List<Map<String,Object>> sceneryTeamAgencyNum;

    @ApiModelProperty(value = "省内游客排行")
    private List<Map<String,Object>> localVisitorList;

    @ApiModelProperty(value = "省外游客排行")
    private List<Map<String,Object>> otherPlaceVisitorList;

    @ApiModelProperty(value = "今年月游客数量")
    private List<Map<String,Object>>everyMonthVisitorList;

    @ApiModelProperty(value = "去年月游客数量")
    private List<Map<String,Object>>lastYearMonthVisitorList;

    @ApiModelProperty(value = "奖励申报件数")
    private Integer rewards;

    @ApiModelProperty(value = "奖励申报通过件数")
    private Integer rewardPass;

    @ApiModelProperty(value = "奖励申报总金额")
    private Double bonus;

    @ApiModelProperty(value = "参数码:0:游客,1:团队")
    private String travleCode;
}
