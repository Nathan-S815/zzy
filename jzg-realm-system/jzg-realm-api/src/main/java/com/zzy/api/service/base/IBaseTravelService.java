package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseShopping;
import com.zzy.db.entity.base.BaseTravel;

import java.util.Map;

/**
 * <p>
 * 旅行社 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-13
 */
public interface IBaseTravelService extends IService<BaseTravel> {
    PageInfo<BaseTravel> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
