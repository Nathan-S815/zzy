package com.zzy.db.dao.base;

import com.zzy.db.entity.base.InfoActivityPropagate;
import com.zzy.db.entity.base.InfoPromotionVideo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 政务宣传 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface InfoPromotionVideoMapper extends BaseMapper<InfoPromotionVideo> {
    List<InfoPromotionVideo> selectPageListInfoPromotionVideo(Map<String, Object> para);
}
