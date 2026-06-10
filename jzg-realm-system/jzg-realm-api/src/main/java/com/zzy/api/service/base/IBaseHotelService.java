package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseGuide;
import com.zzy.db.entity.base.BaseHotel;

import java.util.Map;

/**
 * <p>
 * 旅游饭店 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseHotelService extends IService<BaseHotel> {
    PageInfo<BaseHotel> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
