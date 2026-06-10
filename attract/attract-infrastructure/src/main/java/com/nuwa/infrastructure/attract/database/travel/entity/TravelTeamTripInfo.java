package com.nuwa.infrastructure.attract.database.travel.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.checkerframework.checker.units.qual.A;

/**
 * 旅行团行程统计详情
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.attract.database.travel.entity:TravelTeamTripInfo.java,v1.0.0 2022-09-15 15:26:51
 * nanHuang Exp $
 */
@Data
public class TravelTeamTripInfo {

    @ApiModelProperty("商户名")
    private String  mchName;
    @ApiModelProperty(value = "账号类型 0-文旅局 1-景区 2-酒店 3-旅行社")
    private Integer accountType;
    @ApiModelProperty("行程ID")
    private Integer  teamUserRefId;
    @ApiModelProperty("日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date    travelDate;
    @ApiModelProperty("当前景区/酒店总人数")
    private Integer teamPerson;
    @ApiModelProperty("当前景区/酒店实到人数")
    private Integer attendance;
    @ApiModelProperty("当前酒店房间数")
    private Integer hotelRooms;
    @ApiModelProperty("当前景区/酒店id")
    private Long userId;
    @ApiModelProperty("审核状态")
    private Integer teamStatus;
    @ApiModelProperty("商家审核时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date mchReviewTime;
    @ApiModelProperty("商家审核信息")
    private String mchReviewReason;
    @ApiModelProperty(value = "行程详情: 1-景区 2-酒店 3-自由行")
    private Integer type;
    @ApiModelProperty("文旅局审核信息")
    private String officialReviewReason;
}
