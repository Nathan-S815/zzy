package com.nuwa.infrastructure.attract.database.travel.entity;

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
 * 团队-客户关联表
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "TeamCustomerRef对象")
public class TeamCustomerRef extends Model<TeamCustomerRef> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("团关联id")
    @TableId(value = "team_customer_ref_id", type = IdType.AUTO)
    private Long teamCustomerRefId;

    @ApiModelProperty("团队id")
    private Long teamId;

    @ApiModelProperty("客户id 旅行人")
    private Long customerId;

    @ApiModelProperty("0-正常 1-删除")
    private Integer status;


    public static final String TEAM_CUSTOMER_REF_ID = "team_customer_ref_id";

    public static final String TEAM_ID = "team_id";

    public static final String CUSTOMER_ID = "customer_id";

}
