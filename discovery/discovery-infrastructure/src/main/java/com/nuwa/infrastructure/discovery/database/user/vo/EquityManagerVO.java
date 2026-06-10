package com.nuwa.infrastructure.discovery.database.user.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/16
 * @Description: 权益管理
 */
@Data
public class EquityManagerVO  {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("任务编号")
    private Integer taskId;

    @ApiModelProperty("任务来源")
    private String taskName;

    @ApiModelProperty("申领时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date submitTime;

    @ApiModelProperty("任务接取时达人标签")
    private String memberTag;

    @ApiModelProperty("任务接取时达人性别")
    private Integer sex;

    @ApiModelProperty("任务接取时达人等级")
    private String levelName;

    @ApiModelProperty("任务接取时达人所在地区")
    private String region;

    @ApiModelProperty("权益标题")
    private String prizeTitle;

    @ApiModelProperty("扩展字段")
    @JsonIgnore
    private String extData;

    @ApiModelProperty("探店地址")
    private String address;

    @ApiModelProperty("真实姓名")
    private String linkName;

    @ApiModelProperty("证件号码")
    private String linkIdCard;

    @ApiModelProperty("联系方式")
    private String linkMobile;

    @ApiModelProperty("探店日期")
    private String visitDate;

    @ApiModelProperty("同行人员")
    private List<List> togetherPeople;
}
