package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 商户分类表
 * 
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:46
 */
@Data
@TableName("merchant_type")
public class MerchantType implements Serializable {
	private static final long serialVersionUID = 1L;


	@TableId
	@ApiModelProperty("类型ID")
	private Long merchantTypeId;

	@ApiModelProperty("类型名称")
	private String merchantTypeName;

	@ApiModelProperty("创建人")
	private String createBy;

	@ApiModelProperty("创建时间")
	private Date createTime;

	@ApiModelProperty("当前状态")
	private String status;

}
