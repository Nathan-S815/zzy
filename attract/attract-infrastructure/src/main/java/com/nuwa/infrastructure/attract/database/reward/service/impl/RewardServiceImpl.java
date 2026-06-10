package com.nuwa.infrastructure.attract.database.reward.service.impl;

import cn.hutool.core.lang.Assert;
import com.alibaba.cola.dto.SingleResponse;
import com.nuwa.framework.base.UserAware;
import com.nuwa.infrastructure.attract.database.common.entity.Material;
import com.nuwa.infrastructure.attract.database.common.mapper.MaterialMapper;
import com.nuwa.infrastructure.attract.database.reward.entity.Reward;
import com.nuwa.infrastructure.attract.database.reward.mapper.RewardMapper;
import com.nuwa.infrastructure.attract.database.reward.service.RewardService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.infrastructure.enums.MaterialFileTypeEnum;
import com.nuwa.infrastructure.enums.MaterialTargetEnum;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 *  服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-21
 */
@Slf4j
@Service
public class RewardServiceImpl extends SuperServiceImpl<RewardMapper, Reward> implements RewardService {

    @Autowired
    private RewardMapper rewardMapper;

    @Autowired
    private AttractUserService attractUserService;

    @Autowired
    private MaterialMapper materialMapper;

    @Override
    public SingleResponse selectFileList() {

        List<Material> rewardList = materialMapper.qryRewardList( MaterialTargetEnum.REWARD.getCode());
        return SingleResponse.of(rewardList);
    }
}
