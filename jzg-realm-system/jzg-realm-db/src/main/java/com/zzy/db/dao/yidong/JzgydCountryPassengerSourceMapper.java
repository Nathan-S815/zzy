package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydCountryPassengerSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 阿坝九寨沟县市国际来源地客流数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydCountryPassengerSourceMapper extends BaseMapper<JzgydCountryPassengerSource> {

    int batchInsert(List<JzgydCountryPassengerSource> list);
}
