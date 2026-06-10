package com.zzy.api.service.hotmap.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.zzy.db.entity.hotmap.GetAbjzgMinutePeopleHotData;
import com.zzy.db.dao.hotmap.GetAbjzgMinutePeopleHotDataMapper;
import com.zzy.api.service.hotmap.IGetAbjzgMinutePeopleHotDataService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 反馈阿坝九寨沟县等区域当前人流热力图 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-06-11
 */
@Service
public class GetAbjzgMinutePeopleHotDataServiceImpl extends ServiceImpl<GetAbjzgMinutePeopleHotDataMapper, GetAbjzgMinutePeopleHotData> implements IGetAbjzgMinutePeopleHotDataService {

    @Autowired
    GetAbjzgMinutePeopleHotDataMapper getAbjzgMinutePeopleHotDataMapper;
    @Override
    public List<Map<String, Object>> getLatest() {
        String time = DateUtil.format(DateUtil.offsetHour(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
        return getAbjzgMinutePeopleHotDataMapper.getLatest(time);
    }
}
