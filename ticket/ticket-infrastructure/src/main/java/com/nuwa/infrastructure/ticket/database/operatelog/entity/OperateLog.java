package com.nuwa.infrastructure.ticket.database.operatelog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 操作日志表
 *
 * @author huyonghack@163.com
 * @since 2021-12-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "OperateLog对象")
public class OperateLog extends Model<OperateLog> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("日志业务标识 ticket_order product")
    private String bizKey;

    @ApiModelProperty("业务订单id")
    private Long bizId;

    @ApiModelProperty("动作")
    private String action;

    @ApiModelProperty("修改的详细信息，可以为json")
    private String detail;

    @ApiModelProperty("操作人id")
    private Long operatorUserId;

    @ApiModelProperty("操作人姓名")
    private String operatorUserName;

    @ApiModelProperty("类别")
    private String category;

    @ApiModelProperty("创建时间")
    private Date createTime;


    public static final String ID = "id";

    public static final String BIZ_KEY = "biz_key";

    public static final String BIZ_ID = "biz_id";

    public static final String ACTION = "action";

    public static final String DETAIL = "detail";

    public static final String OPERATOR_USER_ID = "operator_user_id";

    public static final String OPERATOR_USER_NAME = "operator_user_name";

    public static final String CATEGORY = "category";

    public static final String CREATE_TIME = "create_time";

}
