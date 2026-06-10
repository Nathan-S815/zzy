package com.zzy.api.service.base.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IInfoPromotionVideoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zzy.db.dao.base.InfoPromotionVideoMapper;
import com.zzy.db.entity.base.InfoPromotionVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 政务宣传 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
@Service
public class InfoPromotionVideoServiceImpl extends ServiceImpl<InfoPromotionVideoMapper, InfoPromotionVideo> implements IInfoPromotionVideoService {
    @Autowired
    private InfoPromotionVideoMapper infoPromotionVideoMapper;

    @Override
    public PageInfo<InfoPromotionVideo> selectPageListInfoPromotionVideo(Integer pagNumber, Integer pagSize, Map<String, Object> para) {
        PageHelper.startPage(pagNumber,pagSize);
        return new PageInfo<>(infoPromotionVideoMapper.selectPageListInfoPromotionVideo(para));
    }
}
