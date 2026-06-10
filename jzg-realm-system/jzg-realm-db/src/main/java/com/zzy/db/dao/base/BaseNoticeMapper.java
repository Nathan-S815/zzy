package com.zzy.db.dao.base;

import com.zzy.db.entity.base.BaseNotice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
public interface BaseNoticeMapper extends BaseMapper<BaseNotice> {

    List<BaseNotice> selectPageListBaseNotice(@Param("keyword")String keyword);
}
