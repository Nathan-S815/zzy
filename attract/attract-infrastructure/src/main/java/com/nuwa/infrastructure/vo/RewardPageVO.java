package com.nuwa.infrastructure.vo;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.infrastructure.vo:RewardPageVO.java,v1.0.0 2022-09-21 14:47:52 nanHuang Exp $
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "奖励分页查询对象")
public class RewardPageVO extends Model<RewardPageVO> {
    @ApiModelProperty("奖励id")
    @TableId(value = "reward_id", type = IdType.AUTO)
    private Long rewardId;

    @ApiModelProperty("旅行社id")
    private Long userId;

    @ApiModelProperty("旅行社名称")
    private String mchName;

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
    private Date createTime;

    public static RewardPageVO toVO(Reward reward) {
        RewardPageVO vo = new RewardPageVO();

        BeanUtils.copyProperties(reward, vo);

        return vo;
    }
}
