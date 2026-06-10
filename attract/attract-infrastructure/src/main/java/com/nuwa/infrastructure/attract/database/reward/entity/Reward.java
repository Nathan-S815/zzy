package com.nuwa.infrastructure.attract.database.reward.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 
 *
 * @author nanhuang @南皇
 * @since 2022-09-21
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "Reward对象")
public class Reward extends Model<Reward> {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty("奖励id")
    @TableId(value = "reward_id", type = IdType.AUTO)
    private Long rewardId;

    @ApiModelProperty("旅行社id")
    private Long userId;

    @ApiModelProperty("1-县内旅行社 2-县外旅行社")
    private Integer travelType;

    @ApiModelProperty("总奖励金额")
    private Integer totalReward;

    @ApiModelProperty("一日游奖励")
    private Integer oneDayReward;

    @ApiModelProperty("过夜游奖励")
    private Integer nightReward;

    @ApiModelProperty("团队id ,分隔")
    private String teamId;

    @ApiModelProperty("0-未发放 1-已发放")
    private Integer reviewStatus;

    @ApiModelProperty("创建时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createTime;

    private String createByName;

    private Long createById;


    public static final String REWARD_ID = "reward_id";

    public static final String USER_ID = "user_id";

    public static final String TRAVEL_TYPE = "travel_type";

    public static final String TOTAL_REWARD = "total_reward";

    public static final String ONE_DAY_REWARD = "one_day_reward";

    public static final String NIGHT_REWARD = "night_reward";

    public static final String TEAM_ID = "team_id";

    public static final String REVIEW_STATUS = "review_status";

    public static final String CREATE_TIME = "create_time";

    public static final String CREATE_BY_NAME = "create_by_name";

    public static final String CREATE_BY_ID = "create_by_id";

}
