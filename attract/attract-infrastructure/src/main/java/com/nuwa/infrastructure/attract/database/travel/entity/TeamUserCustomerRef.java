package com.nuwa.infrastructure.attract.database.travel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.annotation.TableId;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TeamUserCustomerRef对象")
public class TeamUserCustomerRef extends Model<TeamUserCustomerRef> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("团关联id")
    @TableId(value = "team_ref_id", type = IdType.AUTO)
    private Long teamRefId;

    @ApiModelProperty("团队id")
    private Long teamId;

    @ApiModelProperty("用户（景点酒店）id")
    private Long userId;

    @ApiModelProperty("客户id 旅行人")
    private Long customerId;

    @ApiModelProperty("日期")
    private Date travelDate;

    @ApiModelProperty("1-酒店 2-景区")
    private Integer type;

    @ApiModelProperty("0-正常 1-删除")
    private Integer Status;

    public static final String TEAM_REF_ID = "team_ref_id";

    public static final String TEAM_ID = "team_id";

    public static final String USER_ID = "user_id";

    public static final String CUSTOMER_ID = "customer_id";

    public static final String TRAVEL_DATE = "travel_date";

    public static final String TYPE = "type";

}
