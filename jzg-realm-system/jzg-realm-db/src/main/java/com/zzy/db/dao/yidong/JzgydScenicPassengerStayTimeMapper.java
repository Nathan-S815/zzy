package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydScenicPassengerStayTime;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县景区驻留时长客流数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydScenicPassengerStayTimeMapper extends BaseMapper<JzgydScenicPassengerStayTime> {

    int batchInsert(List<JzgydScenicPassengerStayTime> list);
}
