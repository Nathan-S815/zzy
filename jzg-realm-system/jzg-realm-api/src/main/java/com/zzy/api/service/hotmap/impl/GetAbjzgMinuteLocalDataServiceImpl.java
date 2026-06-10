package com.zzy.api.service.hotmap.impl;

import com.zzy.db.entity.hotmap.GetAbjzgMinuteLocalData;
import com.zzy.db.dao.hotmap.GetAbjzgMinuteLocalDataMapper;
import com.zzy.api.service.hotmap.IGetAbjzgMinuteLocalDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 反馈阿坝九寨沟县等区域当前人流总数、游客人数和常驻人数 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Service
public class GetAbjzgMinuteLocalDataServiceImpl extends ServiceImpl<GetAbjzgMinuteLocalDataMapper, GetAbjzgMinuteLocalData> implements IGetAbjzgMinuteLocalDataService {

}
