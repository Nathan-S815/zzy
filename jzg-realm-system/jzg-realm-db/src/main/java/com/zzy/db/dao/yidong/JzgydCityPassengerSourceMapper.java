package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydCityPassengerSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县市内各地级市来源地客流数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydCityPassengerSourceMapper extends BaseMapper<JzgydCityPassengerSource> {

    int batchInsert(List<JzgydCityPassengerSource> list);
}
