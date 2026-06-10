package com.nuwa.infrastructure.attract.database.user.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;
import com.nuwa.infrastructure.attract.database.user.mapper.AttractUserMapper;
import com.nuwa.infrastructure.attract.database.user.service.AttractUserService;
import com.nuwa.framework.database.supper.SuperServiceImpl;
import com.nuwa.infrastructure.vo.screen.MchDetailsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务实现类
 *
 * @author nanhuang @南皇
 * @since 2022-09-06
 */
@Slf4j
@Service
public class AttractUserServiceImpl extends SuperServiceImpl<AttractUserMapper, AttractUser>
    implements AttractUserService {

    @Autowired
    private AttractUserMapper attractUserMapper;

    /**
     * 通过用户名获取用户信息
     *
     * @param userName 用户名
     * @return 用户信息
     */
    @Override
    public AttractUser getByUserName(String userName) {
        return this
            .lambdaQuery()
            .eq(AttractUser::getUsername, userName)
            .one();

    }


    /**
     * 大屏商家详情数据
     * @param pageSize
     * @param pageNum
     * @param mchName
     * @param accountType
     * @return
     */
    @Override
    public IPage<MchDetailsVO> getMchDetailList(Long pageSize, Long pageNum, String mchName, String accountType) {
        Page<MchDetailsVO> page = new Page<>();
        if (pageSize != null && pageNum != null) {
            page.setSize(pageSize);
            page.setCurrent(pageNum);
        }

        IPage<MchDetailsVO> record = attractUserMapper.getMchDetailList(page, mchName, accountType);
        return record;
    }
}
