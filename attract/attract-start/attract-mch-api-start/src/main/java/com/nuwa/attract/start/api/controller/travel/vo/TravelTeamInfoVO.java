package com.nuwa.attract.start.api.controller.travel.vo;

import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.infrastructure.attract.database.invoice.entity.Invoice;
import com.nuwa.infrastructure.attract.database.travel.entity.TravelTeamTripInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 团队详情vo
 *
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.vo:TravelTeamInfoVO.java,v1.0.0 2022-09-15 15:34:28 nanHuang Exp $
 */
@Data
public class TravelTeamInfoVO {
    @ApiModelProperty("团队id")
    private Long                     teamId;
    @ApiModelProperty("团队名")
    private String                   teamName;
    @ApiModelProperty("领队人姓名")
    private String                   leaderName;
    @ApiModelProperty("领队人电话")
    private String                   leaderPhone;
    @ApiModelProperty("领队人身份证")
    private String                   leaderIdcard;
    @ApiModelProperty("团队性质 1-境内游客团 2-境外游客团")
    private Integer                  teamNature;
    @ApiModelProperty("团队类型 1-老年团 2-亲子团 3-购物团 4-纯玩团 5-学生团 6-研学团 7-疗休养团")
    private Integer                  teamType;
    @ApiModelProperty("行程开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date                     beginDate;
    @ApiModelProperty("行程结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date                     endDate;
    @ApiModelProperty("旅行社id")
    private Long                     userId;
    @ApiModelProperty("团队状态 0-未提交 1-景区酒店审核中 2-景区酒店审核失败 3-待文旅局审核 4-文旅局审核成功 5-文旅局审核失败")
    private Integer                  teamStatus;
    @ApiModelProperty("团队人数 （总）")
    private Integer                  teamPerson;
    @ApiModelProperty("奖励人数")
    private Integer                  rewardPerson;
    @ApiModelProperty("商家审核信息")
    private String                   mchReviewReason;
    @ApiModelProperty("官方审核信息")
    private String                   officialReviewReason;
    @ApiModelProperty("申请时间")
    private Date                     applyTime;
    @ApiModelProperty("酒店景区商家审核时间")
    private Date                     mchReviewTime;
    @ApiModelProperty("文旅局审核时间")
    private Date                     officialReviewTime;
    @ApiModelProperty("行程详情")
    private List<TravelTeamTripInfo> travelTeamTripInfoList;
    @ApiModelProperty("发票详情")
    private List<Invoice> invoiceList;
}
