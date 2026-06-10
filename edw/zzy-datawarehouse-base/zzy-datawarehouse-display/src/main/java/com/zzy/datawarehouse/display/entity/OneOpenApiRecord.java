package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 一码通调用记录
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Data
@TableName("one_open_api_record")
public class OneOpenApiRecord implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("一码通平台商户id")
    private Long oneMchId;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("一码通平台客户端id")
    private Long oneCleintId;

    @ApiModelProperty("一码通平台客户端appid")
    private String oneCleintAppId;

    @ApiModelProperty("一码通平台客户端名称")
    private String oneCleintName;

    @ApiModelProperty("用户身份")
    private String identityCode;

    @ApiModelProperty("身份证号码")
    private String idNo;

    @ApiModelProperty("身份证姓名")
    private String idName;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("景博名称")
    private String scenicspotName;

    @ApiModelProperty("景区id")
    private Long scenicspotId;

    @ApiModelProperty("扫码设备客户端appId")
    private Long scanClientAppId;

    @ApiModelProperty("扫码设备客户端名称")
    private String scanClientAppName;

    @ApiModelProperty("扫码商户id")
    private Long scanMchId;

    @ApiModelProperty("权益信息")
    private String rightsInfo;

    @ApiModelProperty("选择的权益id")
    private Long rightsId;

    @ApiModelProperty("扫码客户端类型")
    private String scanClientType;

    @ApiModelProperty("记录时间")
    private Date createTime;

}
