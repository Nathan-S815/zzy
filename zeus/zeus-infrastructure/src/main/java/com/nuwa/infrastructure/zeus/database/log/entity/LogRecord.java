package com.nuwa.infrastructure.zeus.database.log.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 操作日志表
 *
 * @author huyonghack@163.com
 * @since 2021-06-30
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "LogRecord对象")
public class LogRecord extends Model<LogRecord> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("租户标识")
    private String tenant;

    @ApiModelProperty("日志业务标识")
    private String bizKey;

    @ApiModelProperty("业务businessNo")
    private String bizNo;

    @ApiModelProperty("操作人")
    private String operator;

    @ApiModelProperty("动作")
    private String action;

    @ApiModelProperty("种类")
    private String category;

    @ApiModelProperty("修改的详细信息，可以为json")
    private String detail;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String TENANT = "tenant";

    public static final String BIZ_KEY = "biz_key";

    public static final String BIZ_NO = "biz_no";

    public static final String OPERATOR = "operator";

    public static final String ACTION = "action";

    public static final String CATEGORY = "category";

    public static final String DETAIL = "detail";

    public static final String CREATE_TIME = "create_time";

}
