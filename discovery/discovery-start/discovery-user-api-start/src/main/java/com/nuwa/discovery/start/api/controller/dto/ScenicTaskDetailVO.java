package com.nuwa.discovery.start.api.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonInclude;
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
public class ScenicTaskDetailVO {
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("报名状态 true:已报名 false:未报名")
    private Boolean enabledApply;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("展示主图")
    @JsonSerialize(using = MaterialJson.class)
    private String picture;

    @ApiModelProperty("发单方式 1:达人投稿")
    private Integer billMode;

    @ApiModelProperty("营销目的 1:电商卖货")
    private Integer target;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("传播方式 1:短视频")
    private Integer broadcastMode;

    @ApiModelProperty("介绍")
    private String introduceText;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("联系人电话")
    private String linkmanTelephone;

    @ApiModelProperty("任务图片")
    @JsonSerialize(using = MaterialJson.class)
    private String pictures;

    @ApiModelProperty("最小佣金比例")
    private Integer chargeMin;

    @ApiModelProperty("最大佣金比例")
    private Integer chargeMax;

    @ApiModelProperty("最小粉丝数")
    private Long limitFansMin;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty("报名人数")
    private Long applyTotal;

    @ApiModelProperty("开始时间")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("有效期模式 1:时间段 2：长久")
    private Integer validityMode;

    @ApiModelProperty("权益列表")
    private List<TaskPrizeDTO> taskPrizeItems;

    @ApiModelProperty("镜头要求")
    private String cameraRemark;

    @ApiModelProperty("口播要求")
    private String wordRemark;

    @ApiModelProperty("标题要求")
    private String titleRemark;

    @ApiModelProperty("官方话题 0：不添加 1:添加 ")
    private Integer topic;

    @ApiModelProperty("官方话题内容")
    private String topicContent;

    @ApiModelProperty("限制性别 0:不限 1:男 2:女")
    private Integer limitSex;

    @ApiModelProperty("限制区域 0:不限 1:限制")
    private Integer limitArea;

    @ApiModelProperty("经度")
    private String lon;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("限制区域内容")
    private String limitAreaContent;

    @ApiModelProperty("其他要求")
    private List<String> otherRemarkList;

    @ApiModelProperty("特别提醒")
    private List<String> specialTipList;

    @ApiModelProperty("同景区下任务数量")
    private Integer taskCount;

    @ApiModelProperty("视频")
    @JsonSerialize(using = MaterialJson.class)
    private String video;

    @ApiModelProperty("等级限制")
    private Integer limitLevel;

    @ApiModelProperty("等级限制")
    private String levelName;

    @ApiModelProperty("首页推荐 0：普通 1：推荐")
    private Integer indexRecommend;

    @ApiModelProperty("任务类型")
    private List<String> industryName;
}
