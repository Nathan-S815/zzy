package com.nuwa.attract.start.api.controller.reward.entity;

import lombok.Data;

/**
 * 过夜游
 *
 * @author nanHuang @南皇
 * @version com.nuwa.attract.start.api.controller.reward.entity:NightRewardModel.java,v1.0.0 2022-09-21 13:14:23
 * nanHuang Exp $
 */
@Data
public class NightRewardModel {
    /**
     * 人数
     */
    private Integer person;



    /**
     * 入住X晚
     */
    private Integer night;
}
