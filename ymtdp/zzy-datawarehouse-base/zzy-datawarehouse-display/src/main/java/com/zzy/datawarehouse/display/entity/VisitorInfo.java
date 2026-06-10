package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 游客信息表
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Data
@TableName("visitor_info")
public class VisitorInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("游客id")
    private Long visitorId;

    @ApiModelProperty("游客姓名")
    private String visitorName;

    @ApiModelProperty("证件类型")
    private String cardType;

    @ApiModelProperty("证件号码")
    private String cardNo;

    @ApiModelProperty("游客手机号")
    private String visitorTel;

    @ApiModelProperty("游览时间")
    private Date visitTime;

    @ApiModelProperty ("景区id")
    private Long scenicSpotId;

    @ApiModelProperty("景区名称")
    private String scenicSpotName;

    @ApiModelProperty("商户Id")
    private Long mchId;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("当前状态")
    private String status;

}
