package com.zzy.api.service.hotmap.impl;

import com.zzy.db.entity.hotmap.GetAbjzgHourLocalData;
import com.zzy.db.dao.hotmap.GetAbjzgHourLocalDataMapper;
import com.zzy.api.service.hotmap.IGetAbjzgHourLocalDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 按小时指定查询时间，反馈阿坝九寨沟县等区域小时客流数据 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Service
public class GetAbjzgHourLocalDataServiceImpl extends ServiceImpl<GetAbjzgHourLocalDataMapper, GetAbjzgHourLocalData> implements IGetAbjzgHourLocalDataService {

}
