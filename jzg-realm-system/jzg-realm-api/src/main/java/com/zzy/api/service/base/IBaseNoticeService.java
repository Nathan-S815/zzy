package com.zzy.api.service.base;

import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseNotice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zzy
 * @since 2020-07-17
 */
public interface IBaseNoticeService extends IService<BaseNotice> {

    public PageInfo<BaseNotice> selectPageListBaseNotice(int pagNumber,int pageSize,String keyword);

}
