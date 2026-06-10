package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.InfoActivityPropagate;
import com.zzy.db.entity.base.InfoPromotionVideo;

import java.util.Map;

/**
 * <p>
 * 政务宣传 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IInfoPromotionVideoService extends IService<InfoPromotionVideo> {
    PageInfo<InfoPromotionVideo> selectPageListInfoPromotionVideo(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
