package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseGuide;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.base.BaseScenic;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 导游基础信息表 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface BaseGuideMapper extends BaseMapper<BaseGuide> {

    List<BaseGuide> selectPageListBaseGuide(Map<String, Object> para);

}
