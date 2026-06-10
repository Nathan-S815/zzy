package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseHotelReport;
import com.zzy.db.entity.base.InfoActivityPropagate;

import java.util.Map;

/**
 * <p>
 * 活动宣传 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IInfoActivityPropagateService extends IService<InfoActivityPropagate> {
    PageInfo<InfoActivityPropagate> selectPageListInfoActivityPropagate(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
