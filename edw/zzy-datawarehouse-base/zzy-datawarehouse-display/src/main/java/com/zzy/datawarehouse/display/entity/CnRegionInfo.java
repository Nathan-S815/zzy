package com.zzy.datawarehouse.display.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 中国地区信息
 * 
 * @author wanghanhan
 * @email han950115@163.com
 * @date 2022-12-13 10:24:45
 */
@Data
@TableName("cn_region_info")
@ApiModel(value = "CnRegionInfo对象")

public class CnRegionInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	@ApiModelProperty("代码")
	@TableId(value = "CRI_CODE", type = IdType.AUTO)
	private String criCode;

	@ApiModelProperty("名称")
	@TableField("CRI_NAME")
	private String criName;

	@ApiModelProperty("简称")
	@TableField("CRI_SHORT_NAME")
	private String criShortName;

	@ApiModelProperty("上级代码")
	@TableField("CRI_SUPERIOR_CODE")
	private String criSuperiorCode;

	@ApiModelProperty("经度")
	@TableField("CRI_LNG")
	private String criLng;

	@ApiModelProperty("纬度")
	@TableField("CRI_LAT")
	private String criLat;

	@ApiModelProperty("排序")
	@TableField("CRI_SORT")
	private Integer criSort;

	@ApiModelProperty("创建时间")
	@TableField("CRI_GMT_CREATE")
	private Date criGmtCreate;

	@ApiModelProperty("修改时间")
	@TableField("CRI_GMT_MODIFIED")
	private Date criGmtModified;

	@ApiModelProperty("备注")
	@TableField("CRI_MEMO")
	private String criMemo;

	@ApiModelProperty("状态")
	@TableField("CRI_DATA_STATE")
	private Integer criDataState;

	@ApiModelProperty("租户ID")
	@TableField("CRI_TENANT_CODE")
	private String criTenantCode;

	@ApiModelProperty("级别1省,2市,3区县")
	@TableField("CRI_LEVEL")
	private String criLevel;

}
