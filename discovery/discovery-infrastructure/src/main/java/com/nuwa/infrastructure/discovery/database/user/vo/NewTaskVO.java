package com.nuwa.infrastructure.discovery.database.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.discovery.util.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @Author: WangXh
 * @DateTime: 2022/11/21
 * @Description: TODO
 */
@Data
public class NewTaskVO {

    @ApiModelProperty("Id")
    private Long id;

    @ApiModelProperty("任务名称")
    private String name;

    @ApiModelProperty("任务主图")
    @JsonSerialize(using = MaterialJson.class)
    private String picture;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("介绍")
    private String introduceText;

    @ApiModelProperty("最大报名人数")
    private Long limitApplyMax;

    @ApiModelProperty("报名人数")
    private Long applyTotal;

    @ApiModelProperty("开始时间")
    private Date beginDate;

    @ApiModelProperty("结束时间")
    private Date endDate;

    @ApiModelProperty("联系方式")
    private String linkmanTelephone;

    @ApiModelProperty("类型编码")
    private String industryCode;

    @ApiModelProperty("类型名称")
    private List<String> industryName;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("已报名用户")
    private List<Map<String, Object>>  userInfoList;
}
