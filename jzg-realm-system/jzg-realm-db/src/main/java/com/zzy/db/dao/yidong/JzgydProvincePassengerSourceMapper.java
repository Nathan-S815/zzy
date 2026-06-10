package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydProvincePassengerSource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县国内各省来源地客流数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydProvincePassengerSourceMapper extends BaseMapper<JzgydProvincePassengerSource> {

    int batchInsert(List<JzgydProvincePassengerSource> list);
}
