package com.nuwa.infrastructure.attract.database.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 中国地区信息
 *
 * @author nanhuang @南皇
 * @since 2022-09-13
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "CnRegionInfo对象")
public class CnRegionInfo extends Model<CnRegionInfo> {
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


    public static final String CRI_CODE = "CRI_CODE";

    public static final String CRI_NAME = "CRI_NAME";

    public static final String CRI_SHORT_NAME = "CRI_SHORT_NAME";

    public static final String CRI_SUPERIOR_CODE = "CRI_SUPERIOR_CODE";

    public static final String CRI_LNG = "CRI_LNG";

    public static final String CRI_LAT = "CRI_LAT";

    public static final String CRI_SORT = "CRI_SORT";

    public static final String CRI_GMT_CREATE = "CRI_GMT_CREATE";

    public static final String CRI_GMT_MODIFIED = "CRI_GMT_MODIFIED";

    public static final String CRI_MEMO = "CRI_MEMO";

    public static final String CRI_DATA_STATE = "CRI_DATA_STATE";

    public static final String CRI_TENANT_CODE = "CRI_TENANT_CODE";

    public static final String CRI_LEVEL = "CRI_LEVEL";

}
