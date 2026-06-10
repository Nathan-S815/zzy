package com.zzy.db.dao.yidong;

import com.zzy.db.entity.yidong.JzgydCountyScenicPassenger;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zzy.db.entity.yidong.JzgydScenicPassengerStayTime;

import java.util.List;

/**
 * <p>
 * 反馈阿坝九寨沟县、景区客流数据(日月) Mapper 接口
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
public interface JzgydCountyScenicPassengerMapper extends BaseMapper<JzgydCountyScenicPassenger> {

    int batchInsert(List<JzgydCountyScenicPassenger> list);
}
