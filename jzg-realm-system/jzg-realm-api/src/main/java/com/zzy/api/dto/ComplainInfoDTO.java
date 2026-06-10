package com.zzy.api.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ComplainInfoDTO {

    /**
     * 投诉主题
     */
    @ApiModelProperty(value = "投诉主题", required = false)
    private String complainName;

    /**
     * 投诉内容
     */
    @ApiModelProperty(value = "投诉内容", required = false)
    private String complainContent;

    /**
     * 投诉类型
     */
    @ApiModelProperty(value = "投诉类型", required = false)
    private String complainType;

    /**
     * 投诉单位
     */
    @ApiModelProperty(value = "投诉单位", required = false)
    private String complainUnit;

    /**
     * 投诉人
     */
    @ApiModelProperty(value = "投诉人", required = false)
    private String complainPeople;

    /**
     * 投诉人电话
     */
    @ApiModelProperty(value = "投诉人电话", required = false)
    private String phone;

    /**
     * 投诉人微信头像
     */
    @ApiModelProperty(value = "投诉人微信头像", required = false)
    private String peoplePic;
    /**
     * b2b投诉数据id
     */
    @ApiModelProperty(value = "b2b投诉数据id", required = false)
    private Integer complainId;

    /**
     * 相关图片
     */
    @ApiModelProperty(value = "投诉相关图片", required = false)
    private String picUrl;
}
