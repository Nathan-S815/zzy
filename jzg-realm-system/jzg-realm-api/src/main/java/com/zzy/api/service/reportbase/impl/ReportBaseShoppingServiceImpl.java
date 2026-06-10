package com.zzy.api.service.reportbase.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.api.service.reportbase.IReportBaseShoppingService;
import com.zzy.db.dao.reportbase.ReportBaseShoppingMapper;
import com.zzy.db.dao.reportbase.ReportBaseShoppingMapper;
import com.zzy.db.entity.reportbase.ReportBaseShopping;
import com.zzy.db.entity.reportbase.ReportBaseShopping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 购物上报基础信息表 服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-05-09
 */
@Service
public class ReportBaseShoppingServiceImpl extends ServiceImpl<ReportBaseShoppingMapper, ReportBaseShopping> implements IReportBaseShoppingService {

    @Autowired
    private ReportBaseShoppingMapper reportBaseShoppingMapper;
    @Override
    public PageInfo<ReportBaseShopping> getReportBaseShoppingList(Integer pagNumber, Integer pagSize, String keyword) {
        PageHelper.startPage(pagNumber, pagSize);
        return new PageInfo<ReportBaseShopping>(reportBaseShoppingMapper.selectPageListBaseShopping(keyword));
    }
}
