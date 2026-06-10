package com.nuwa.attract.start.api.controller.reward.entity;

import java.util.List;

import com.google.api.client.util.Lists;
import lombok.Data;

/**
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward.entity:OneDayRewardModel.java,v1.0.0 2022-09-21 13:13:20
 * nanHuang Exp $
 */
@Data
public class OneDayRewardConfig {
    /**
     * 游览人数大于等于
     */
    private Integer beginPerson;
    /**
     * 游览人数小于
     */
    private Integer endPerson;
    /**
     * 奖励
     */
    private Integer reward;

    public OneDayRewardConfig(Integer beginPerson, Integer endPerson, Integer reward) {
        this.beginPerson = beginPerson;
        this.endPerson = endPerson;
        this.reward = reward;
    }

    /**
     * 一日游计算规则
     *
     * @param person 可以赋予奖励的人数
     * @return 奖励金额
     */
    public static Integer oneDayCalculateRole(Integer person) {
        // 游览浦江购票x个景点
        final Integer oneDayTicket = 1;
        //一日游览奖励
        List<OneDayRewardConfig> oneDayRewardConfigs = Lists.newArrayList();
        oneDayRewardConfigs.add(new OneDayRewardConfig(0, 1000, 0));
        oneDayRewardConfigs.add(new OneDayRewardConfig(1000, 5000, 4));
        oneDayRewardConfigs.add(new OneDayRewardConfig(5000, 9999999, 5));
        for (OneDayRewardConfig oneDayRewardConfig : oneDayRewardConfigs) {
            if (person >= oneDayRewardConfig.getBeginPerson() && person < oneDayRewardConfig.getEndPerson()) {
                return person * oneDayRewardConfig.getReward();
            }
        }

        return 0;
    }
}