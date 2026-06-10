package com.nuwa.attract.start.api.controller.reward.entity;

import java.util.List;

import com.google.api.client.util.Lists;
import com.sun.org.apache.bcel.internal.generic.RETURN;
import io.swagger.models.auth.In;
import lombok.Data;

/**
 * 过夜游配置
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward.entity:NightRewardConfig.java,v1.0.0 2022-09-21 13:18:36
 * nanHuang Exp $
 */
@Data
public class NightRewardConfig {
    /**
     * 游览人数大于等于
     */
    private Integer beginPerson;
    /**
     * 游览人数小于
     */
    private Integer endPerson;
    /**
     * 县内奖励 /夜
     */
    private Integer localReward;
    /**
     * 县外奖励 /夜
     */
    private Integer nonLocalReward;
    /**
     * 第二晚开始额外奖励 /夜
     */
    private Integer additionalReward;

    public NightRewardConfig(Integer beginPerson, Integer endPerson, Integer localReward, Integer nonLocalReward,
                             Integer additionalReward) {
        this.beginPerson = beginPerson;
        this.endPerson = endPerson;
        this.localReward = localReward;
        this.nonLocalReward = nonLocalReward;
        this.additionalReward = additionalReward;
    }

    private static List<NightRewardConfig> getConfig() {
        List<NightRewardConfig> nightRewardConfigs = Lists.newArrayList();
        nightRewardConfigs.add(new NightRewardConfig(0, 1000, 0, 0, 0));
        nightRewardConfigs.add(new NightRewardConfig(1000, 5000, 15, 14, 4));
        nightRewardConfigs.add(new NightRewardConfig(5000, 99999999, 21, 20, 4));
        return nightRewardConfigs;
    }

    /**
     * 过夜游计算规则
     *
     * @param nightRewardModels 过夜游明细
     * @param nativeTravel      是否是县内旅行社
     * @return 奖励金额
     */
    public static Integer nightRewardCalculateRole(List<NightRewardModel> nightRewardModels, Boolean nativeTravel) {
        List<NightRewardConfig> nightRewardConfigs = NightRewardConfig.getConfig();
        int totalPerson = nightRewardModels.stream().mapToInt(NightRewardModel::getPerson).sum();
        //两夜及以上人数
        int twoNightPerson = nightRewardModels.stream().filter(x -> x.getNight() >= 2).mapToInt(
            NightRewardModel::getPerson).sum();

        for (NightRewardConfig nightRewardConfig : nightRewardConfigs) {
            if (totalPerson >= nightRewardConfig.getBeginPerson() && totalPerson < nightRewardConfig.getEndPerson()) {
                //县内 + 县外 + 多夜奖励
                if (nativeTravel) {
                    return totalPerson * nightRewardConfig.getLocalReward()
                        + twoNightPerson * nightRewardConfig.getAdditionalReward();
                } else {
                    return totalPerson * nightRewardConfig.getNonLocalReward()
                        + twoNightPerson * nightRewardConfig.getAdditionalReward();
                }
            }
        }

        return 0;
    }

}
