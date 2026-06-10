package com.zzy.api.service.base.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.base.IBaseShoppingReportService;
import com.zzy.db.dao.base.BaseShoppingReportMapper;
import com.zzy.db.dao.base.BaseShoppingReportMapper;
import com.zzy.db.entity.base.BaseShoppingReport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 * 购物场所收入表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-13
 */
@Service
public class BaseShoppingReportServiceImpl extends ServiceImpl<BaseShoppingReportMapper, BaseShoppingReport> implements IBaseShoppingReportService {

    @Autowired
    private BaseShoppingReportMapper baseShoppingReportMapper;

    @Override
    public PageInfo<Map<String, Object>> selectPageListBaseShoppingReport(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        try{
            return new PageInfo<Map<String, Object>>(baseShoppingReportMapper.selectPageListBaseShoppingReport(keyword));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public PageInfo<Map<String, Object>> getBaseShoppingReportByShoppingId(Integer pagNumber, Integer pagSize, Integer id,String startTime,String endTime) {
        PageHelper.startPage(pagNumber, pagSize);
        try {
            return new PageInfo<Map<String, Object>>(baseShoppingReportMapper.getByShoppingId(id,startTime,endTime));
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }
}
