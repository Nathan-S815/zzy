package com.nuwa.ticket.start.api.controller.articel.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.nuwa.infrastructure.ticket.json.serializer.MaterialJson;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 商户资讯分页查询VO
 *
 * @author hy
 */
@Data
public class ArticleInfoPageVO {
    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty("一级分类Id")
    private Long categoryOne;

    @ApiModelProperty("二级分类Id")
    private Long categorySecond;

    @ApiModelProperty("一级分类名称")
    private String categoryOneName;

    @ApiModelProperty("二级分类名称")
    private String categorySecondName;

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("浏览次数")
    private Long views;

    @ApiModelProperty("上下架 0:下架 1:上架")
    private Integer status;

    @ApiModelProperty("封面图")
    @JsonSerialize(using = MaterialJson.class)
    private String cover;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("更新时间")
    private Date updateTime;
}
