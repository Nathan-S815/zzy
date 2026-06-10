package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 景区分类表
 * 
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Data
@TableName("scenic_spot_type")
public class ScenicSpotType implements Serializable {
	private static final long serialVersionUID = 1L;

	@TableId
	@ApiModelProperty("类型ID")
	private Long scenicTypeId;
	
	@ApiModelProperty("类型名称")
	private String scenicTypeName;

	@ApiModelProperty("创建人")
	private String createBy;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("当前状态")
	private String status;

}
