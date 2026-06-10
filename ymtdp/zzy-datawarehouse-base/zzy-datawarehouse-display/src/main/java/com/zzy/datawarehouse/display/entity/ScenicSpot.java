package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 景区信息表
 * 
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
@Data
@TableName("scenic_spot")
public class ScenicSpot implements Serializable {
	private static final long serialVersionUID = 1L;


	@TableId
	@ApiModelProperty("景区id")
	private Long scenicspotId;

	@ApiModelProperty("游客id")
	private Long visitorId;

	@ApiModelProperty("景区名称")
	private String scenicspotName;

	@ApiModelProperty("省份编码")
	private Long provinceId;

	@ApiModelProperty("当前状态")
	private String status;

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

	@ApiModelProperty("景区类型")
	private Long scenicSpotType;

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
