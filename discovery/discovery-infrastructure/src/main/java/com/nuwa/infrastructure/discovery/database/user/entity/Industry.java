package com.nuwa.infrastructure.discovery.database.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 
 *
 * @author huyonghack@163.com
 * @since 2022-11-17
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Industry对象")
public class Industry extends Model<Industry> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("ID")
    @TableId(value = "industry_id", type = IdType.AUTO)
    private Long industryId;

    @ApiModelProperty("行业编码")
    private String industryCode;

    @ApiModelProperty("行业名称")
    private String industryName;

    @ApiModelProperty("状态 '0':正常,'1':已删除")
    private String status;


    public static final String INDUSTRY_ID = "industry_id";

    public static final String INDUSTRY_CODE = "industry_code";

    public static final String INDUSTRY_NAME = "industry_name";

    public static final String STATUS = "status";

}
