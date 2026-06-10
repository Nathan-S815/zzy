package com.nuwa.discovery.start.api.controller.task.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.nuwa.discovery.start.api.controller.dto.TaskPrizeDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "SaveTaskParam 新增/修改任务参数")
public class SaveTaskParam {

    @ApiModelProperty(value = "任务id，修改时不能为空")
    private Long id;

    @ApiModelProperty(value = "任务名称", required = true)
    private String name;

    @ApiModelProperty(value = "封面图片", required = true)
    private String picture;

    @ApiModelProperty("详情图")
    private String pictures;

    @ApiModelProperty("发单方式 1:达人投稿")
    private Integer billMode;

    @ApiModelProperty("营销目的 1:电商卖货")
    private Integer target;

    @ApiModelProperty("任务平台编码 douyin|xiaohongshu")
    private String platformCode;

    @ApiModelProperty("传播方式 1:短视频")
    private Integer broadcastMode;

    @ApiModelProperty(value = "任务介绍", required = true)
    private String introduceText;

    @ApiModelProperty(value = "联系人", required = true)
    private String linkman;

    @ApiModelProperty(value = "联系人电话", required = true)
    private String linkmanTelephone;

    @ApiModelProperty("最小佣金比例")
    private Integer chargeMin;

    @ApiModelProperty("最大佣金比例")
    private Integer chargeMax;

    @ApiModelProperty("最小粉丝数")
    private Long limitFansMin;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty(value = "达人等级要求", required = true)
    private Integer limitLevel;

    @ApiModelProperty(value = "开始时间" , required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date beginDate;

    @ApiModelProperty(value = "结束时间" , required = true)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @ApiModelProperty("有效期模式 1:时间段 2：长久")
    private Integer validityMode;

    @ApiModelProperty("任务权益列表")
    private List<TaskPrizeDTO> taskPrizes;

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

    @ApiModelProperty("限制区域内容")
    private String limitAreaContent;

    @ApiModelProperty("其他要求")
    private List<String> otherRemarkList;

    @ApiModelProperty("特别提醒")
    private List<String> specialTipList;

    @ApiModelProperty("经度")
    private String lon;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty(value = "地址", required = true)
    private String address;

    @ApiModelProperty("来源 1:平台 2:商户")
    private Integer source;

    @ApiModelProperty(value = "任务类型", required = true)
    private List<String> industryCodeList;
}
