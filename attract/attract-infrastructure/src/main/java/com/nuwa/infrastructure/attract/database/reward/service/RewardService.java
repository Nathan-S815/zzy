package com.nuwa.infrastructure.attract.database.reward.service;

import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.framework.database.supper.SuperService;

/**
 *  服务类
 *
 * @author nanhuang @南皇
 * @since 2022-09-21
 */
public interface RewardService extends SuperService<Reward> {

    SingleResponse<?> selectFileList();
}
