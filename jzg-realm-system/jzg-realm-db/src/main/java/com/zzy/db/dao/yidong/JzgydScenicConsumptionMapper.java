package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydScenicConsumption;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县及景区客流年龄数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydScenicConsumptionMapper extends BaseMapper<JzgydScenicConsumption> {

    int batchInsert(List<JzgydScenicConsumption> list);
}
