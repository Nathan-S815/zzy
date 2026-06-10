package com.nuwa.infrastructure.discovery.database.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
public class MyTaskPageVO {
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty("报名人数")
    private Long applyTotal;

    @ApiModelProperty("最小佣金比例")
    private Integer chargeMin;

    @ApiModelProperty("最大佣金比例")
    private Integer chargeMax;

    @ApiModelProperty("最小粉丝数")
    private Long limitFansMin;

    @ApiModelProperty("任务图片")
    @JsonSerialize(using = MaterialJson.class)
    private String picture;

    @ApiModelProperty("开始时间")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("任务状态 1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

    @ApiModelProperty("首页推荐 0：普通 1：推荐")
    private Integer indexRecommend;

    @ApiModelProperty("任务权益标签 [权益A][权益B]")
    private String prizeTypeTag;

    @ApiModelProperty("任务权益标签列表")
    private List<String> prizeTypeList;

    @ApiModelProperty("已领取任务权益标签列表")
    private List<String> receivedPrizeTypeList;

    @ApiModelProperty("已领取任务权益标签列表")
    private List<String> unclaimedPrizeTypeList;

}
