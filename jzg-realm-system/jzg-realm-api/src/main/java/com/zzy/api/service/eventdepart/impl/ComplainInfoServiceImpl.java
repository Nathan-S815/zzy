package com.zzy.api.service.eventdepart.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.eventdepart.ComplainInfo;
import com.zzy.db.dao.eventdepart.ComplainInfoMapper;
import com.zzy.api.service.eventdepart.IComplainInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 小程序投诉表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-15
 */
@Service
public class ComplainInfoServiceImpl extends ServiceImpl<ComplainInfoMapper, ComplainInfo> implements IComplainInfoService {

    @Autowired
    private ComplainInfoMapper complainInfoMapper;
    @Override
    public PageInfo<Map<String, Object>> listComplainInfo(Integer pageNo, Integer pageSize,Integer state,String creatTime,String startTime,String endTime) {
        PageHelper.startPage(pageNo,pageSize);
        List<Map<String, Object>> maps = complainInfoMapper.listComplainInfo(state,creatTime,startTime,endTime);
        return new PageInfo<>(maps);
    }

    @Override
    public int updateComplainInfoState(Integer id, Integer state) {
        return complainInfoMapper.updateComplainInfoState(id,state);
    }

    @Override
    public int insertReplenish(Integer id, String replenish) {
        return complainInfoMapper.updateReplenish(id,replenish);
    }
}
