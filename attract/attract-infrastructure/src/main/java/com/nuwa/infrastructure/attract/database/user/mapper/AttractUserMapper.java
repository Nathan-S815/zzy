package com.nuwa.infrastructure.attract.database.user.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nuwa.framework.database.supper.SuperMapper;
import com.nuwa.infrastructure.attract.database.user.entity.AttractUser;

import com.nuwa.infrastructure.vo.screen.MchDetailsVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * Mapper 接口
 *
 * @author nanhuang @南皇
 * @since 2022-09-13
 */
@Repository
public interface AttractUserMapper extends SuperMapper<AttractUser> {

    /**
     * 大屏商户数据详情
     *
     * @param page
     * @param mchName
     * @param accountType
     * @return
     */
    IPage<MchDetailsVO> getMchDetailList(IPage<MchDetailsVO> page, @Param("mchName") String mchName,
                                                                   @Param("accountType") String accountType);
}
