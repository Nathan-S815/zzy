package com.zzy.api.service.hotmap.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.dao.hotmap.PandaFlowMapper;
import com.zzy.db.entity.hotmap.MonitorInfo;
import com.zzy.db.dao.hotmap.MonitorInfoMapper;
import com.zzy.api.service.hotmap.IMonitorInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 监控信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-27
 */
@Service
public class MonitorInfoServiceImpl extends ServiceImpl<MonitorInfoMapper, MonitorInfo> implements IMonitorInfoService {
    @Autowired
    private MonitorInfoMapper monitorInfoMapper;

    @Autowired
    private PandaFlowMapper pandaFlowMapper;


    @Override
    public PageInfo<MonitorInfo> listMonitorInfo(Integer pageNumber, Integer pageSize,Integer type) {
        PageHelper.startPage(pageNumber,pageSize);
        List<MonitorInfo> monitorInfos = monitorInfoMapper.selectList(new QueryWrapper<MonitorInfo>().eq("scenic_type", type));
        return new PageInfo<>(monitorInfos);
    }

    @Override
    public List<Map<String, Object>> getInAndOutPeople(String dayTime) {
        return pandaFlowMapper.getInAndOutPeople(dayTime);
    }
}
