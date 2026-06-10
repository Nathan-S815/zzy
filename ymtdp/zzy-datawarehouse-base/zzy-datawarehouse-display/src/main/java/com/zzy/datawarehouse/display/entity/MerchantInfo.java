package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商户信息表
 *
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
@Data
@ApiModel(value = "商户信息")
@TableName("merchant_info")
public class MerchantInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "mch_id", type = IdType.AUTO)
    @ApiModelProperty("商户ID")
    private Long mchId;

    @ApiModelProperty("商户名称")
    private String mchName;

    @ApiModelProperty("当前状态")
    private String status;

    @ApiModelProperty("是否开通一码通")
    private String isOneClient;

    @ApiModelProperty("省份编码")
    private Long provinceId;

    @ApiModelProperty("省份名称")
    private String provinceName;

    @ApiModelProperty("城市编码")
    private Long cityId;

    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty("地区编码")
    private Long areaId;

    @ApiModelProperty("地区名称")
    private String areaName;

    @ApiModelProperty("详细地址")
    private String adress;

    @ApiModelProperty("联系人姓名")
    private String contactName;

    @ApiModelProperty("联系人电话")
    private String contactPhone;

    @ApiModelProperty("商户类型")
    private Long mchType;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("创建时间")
    private Date createTime;

    @ApiModelProperty("创建人")
    private Long createBy;

    @ApiModelProperty("更新人")
    private Long updateBy;

    @ApiModelProperty("更新时间")
    private Date updateTime;

}
