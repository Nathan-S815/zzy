package com.nuwa.infrastructure.attract.database.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.framework.database.supper.SuperService;
import com.nuwa.infrastructure.vo.screen.MchDetailsVO;

/**
 * 服务类
 *
 * @author nanhuang @南皇
 * @since 2022-09-06
 */
public interface AttractUserService extends SuperService<AttractUser> {

    /**
     * 通过用户名获取用户信息
     * @param userName 用户名
     * @return 用户信息
     */
    AttractUser getByUserName(String userName);

    /**
     * 大屏商家详情数据
     * @param pageSize
     * @param pageNum
     * @param mchName
     * @param accountType
     * @return
     */
    IPage<MchDetailsVO> getMchDetailList(Long pageSize, Long pageNum, String mchName, String accountType);
}
