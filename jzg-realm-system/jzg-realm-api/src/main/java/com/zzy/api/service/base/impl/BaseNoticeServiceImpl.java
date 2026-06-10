package com.zzy.api.service.base.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseNotice;
import com.zzy.db.dao.base.BaseNoticeMapper;
import com.zzy.api.service.base.IBaseNoticeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
@Service
public class BaseNoticeServiceImpl extends ServiceImpl<BaseNoticeMapper, BaseNotice> implements IBaseNoticeService {

    @Autowired
    BaseNoticeMapper baseNoticeMapper;

    @Override
    public PageInfo<BaseNotice> selectPageListBaseNotice(int pagNumber, int pageSize, String keyword) {
        PageHelper.startPage(pagNumber,pageSize);
        return new PageInfo<BaseNotice>(baseNoticeMapper.selectPageListBaseNotice(keyword));
    }
}
