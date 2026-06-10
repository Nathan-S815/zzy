package com.nuwa.discovery.start.api.controller.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author hy
 */
@Data
public class ScenicTaskPageVO {
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("任务平台编码")
    private String platformCode;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("联系人电话")
    private String linkmanTelephone;

    @ApiModelProperty("报名人数")
    private Long applyTotal;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty("开始时间")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("任务状态  1:未开始 2:进行中 3:已结束 4:暂停")
    private Integer status;

    @ApiModelProperty("镜头要求")
    private String cameraRemark;

    @ApiModelProperty("口播要求")
    private String wordRemark;

    @ApiModelProperty("标题要求")
    private String titleRemark;

    @ApiModelProperty("官方话题 0：不添加 1:添加 ")
    private Integer topic;

    @ApiModelProperty("权重")
    private Integer weight;

    @ApiModelProperty("官方话题内容")
    private String topicContent;

    @ApiModelProperty("首页推荐 0：普通 1：推荐")
    private Integer indexRecommend;

    @ApiModelProperty("任务权益标签 [权益A][权益B]")
    private String prizeTypeTag;

    @ApiModelProperty("任务权益标签列表")
    private List<String> prizeTypeList;
}
