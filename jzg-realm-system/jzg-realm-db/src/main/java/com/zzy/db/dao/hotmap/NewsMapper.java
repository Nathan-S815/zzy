package com.zzy.db.dao.hotmap;

import com.zzy.db.entity.carpark.GetEnterCar;
import com.zzy.db.entity.hotmap.News;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-07
 */
public interface NewsMapper extends BaseMapper<News> {

    int batchInsert(List<News> getNews);
}
