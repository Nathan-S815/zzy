package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseGuide;
import com.zzy.db.entity.base.BaseHotel;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 旅游饭店 Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface BaseHotelMapper extends BaseMapper<BaseHotel> {

    List<BaseHotel> selectPageListBaseHotel(Map<String, Object> para);
}
