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
 * 客户表
 *
 * @author nanhuang @南皇
 * @since 2022-09-15
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Customer对象")
public class Customer extends Model<Customer> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("客户id")
    @TableId(value = "customer_id", type = IdType.AUTO)
    private Long customerId;

    @ApiModelProperty("姓名")
    private String name;

    @ApiModelProperty("身份证号")
    private String idcard;

    @ApiModelProperty("是否可以领取奖励 0-否 1-是")
    private Integer reward;

    @ApiModelProperty("状态 0-正常 1-已删除")
    private Integer status;

    public static final String CUSTOMER_ID = "customer_id";

    public static final String NAME = "name";

    public static final String IDCARD = "idcard";

    public static final String REWARD = "reward";

}
