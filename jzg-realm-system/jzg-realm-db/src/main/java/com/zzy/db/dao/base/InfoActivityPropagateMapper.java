package com.zzy.db.dao.base;

import com.zzy.db.entity.base.InfoActivityPropagate;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.base.InfoPromotionVideo;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 活动宣传 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface InfoActivityPropagateMapper extends BaseMapper<InfoActivityPropagate> {
    List<InfoActivityPropagate> selectPageListInfoActivityPropagate(Map<String, Object> para);
}
