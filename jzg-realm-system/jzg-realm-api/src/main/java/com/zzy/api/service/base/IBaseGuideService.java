package com.zzy.api.service.base;


import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.zzy.db.entity.base.BaseGuide;
import com.zzy.db.entity.base.BaseScenic;

import java.util.Map;

/**
 * <p>
 * 导游基础信息表 服务类
 * </p>
 *
 * @author zzy
 * @since 2020-04-14
 */
public interface IBaseGuideService extends IService<BaseGuide> {
    PageInfo<BaseGuide> selectPageList(Integer pagNumber, Integer pagSize, Map<String, Object> para);
}
